package Interpreter.Engine;

import Interpreter.ParseException;
import Interpreter.Tokenizer.Token;
import Interpreter.Tokenizer.TokenType;
import java.io.*;
import java.util.ArrayList;

//TODO Heavy refactoring required, lots of things are being repeated across functions
public class Engine {

    File currDB;
    ArrayList<String> output;

    final String extension = ".tb";

    public Engine(){
        currDB = null;
        output = null;
    }

    public void useDB(String dbName) throws ParseException{
        currDB = new File(dbName);
        if (!currDB.exists()) throw new ParseException("Database '" + dbName + "' does not exist");
        output = defaultOutput();
    }

    public void createDB(String dbName) throws ParseException {
        File db = new File(dbName);
        if (db.exists()) throw new ParseException("Database '" + dbName + "' already exists");
        if(!db.mkdir()) throw new ParseException("Couldn't create Database");
        output = defaultOutput();
    }

    public void createTB(String tbName, ArrayList<String> attributes) throws ParseException, IOException {
        File tbFile = getFileOfTable(tbName, false);
        if(!tbFile.createNewFile()) throw new ParseException("Couldn't create table");
        Table tb = new Table(attributes);
        writeTableToFile(tb, tbFile);
        output = defaultOutput();
    }

    public void dropDB(String dbName) throws ParseException {
        File db = new File(dbName);
        if (!db.exists()) throw new ParseException("Database doesn't exist");
        File[] tables = db.listFiles();
        if (tables == null) throw new ParseException("CRITICAL: Wrong format of Database");
        for(File table : tables) {
            if(!table.delete()) throw new ParseException("CRITICAL: Wrong format of Tables in Database");
        }
        if(!db.delete()) throw new ParseException("Deleted Tables in Database but not Database itself");
        output = defaultOutput();
    }

    public void dropTB(String tbName) throws ParseException {
        File table = getFileOfTable(tbName, true);
        if(!table.delete()) throw new ParseException("Couldn't delete table");
        output = defaultOutput();
    }

    public void insertInto(String tbName, ArrayList<Token> values) throws ParseException, IOException {
        File tableFile = getFileOfTable(tbName, true);
        Table table = readTableFromFile(tableFile);
        table.insert(values);
        writeTableToFile(table, tableFile);
        output = defaultOutput();
    }

    public void select(String tbName, ArrayList<String> attributes, Operation cond) throws ParseException, IOException {
        File tableFile = getFileOfTable(tbName, true);
        Table table = readTableFromFile(tableFile);

        if(attributes == null) {
            attributes = table.attributes;
        } else {
            for (String attribute : attributes) {
                if (!table.attributes.contains(attribute)) throw new ParseException(attribute + " is not an attribute in the table.");
            }
        }

        ArrayList<String> output = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        for (String attribute : attributes ) {
            line.append(attribute).append("\t");
        }
        output.add(line.toString());

        for (Row row : table.rows.values()) {
            if(cond==null || cond.compare(row)) {
                line.setLength(0);
                for (String attribute : attributes) {
                    line.append(row.map.get(attribute)).append("\t");
                }
                output.add(line.toString());
            }
        }
        this.output = output;
    }

    public void deleteFrom(String tableName, Operation cond) throws ParseException, IOException {
        File tableFile = getFileOfTable(tableName, true);
        Table table = readTableFromFile(tableFile);

        for (Integer i : new ArrayList<>(table.rows.keySet())) {
            Row row = table.rows.get(i);
            if(cond.compare(row)) {
                table.rows.remove(i,row);
            }
        }
        writeTableToFile(table,tableFile);
        output = defaultOutput();
    }

    public void update(String tableName, ArrayList<String> attributes, ArrayList<Token> values, Operation cond) throws ParseException, IOException {
        File tableFile = getFileOfTable(tableName, true);
        Table table = readTableFromFile(tableFile);

        if(!table.attributes.containsAll(attributes)) throw new ParseException("Attribute(s) selected is(are) not in the table");

        for(Row row: table.rows.values()) {
            if (cond.compare(row)) {
                for(int i = 0; i<attributes.size();i++) {
                    row.map.replace(attributes.get(i),values.get(i));
                }
            }
        }

        writeTableToFile(table,tableFile);
        output = defaultOutput();
    }


    public void alter( String tableName, String alteration, String attribute) throws IOException, ParseException {
        File tableFile = getFileOfTable(tableName, true);
        Table table = readTableFromFile(tableFile);

        switch (alteration.toUpperCase()) {
            case "ADD":
                if (table.attributes.contains(attribute)) throw new ParseException("This table already has this attribute");
                table.attributes.add(attribute);
                for (Row row : table.rows.values()) {
                    row.map.put(attribute, new Token(TokenType.STRLIT, "''"));
                }
                break;
            case "DROP":
                if (!table.attributes.contains(attribute)) throw new ParseException("This table does not have this attribute");
                table.attributes.remove(attribute);
                for (Row row : table.rows.values()) {
                    row.map.remove(attribute);
                }
                break;
            default: throw new ParseException("CRITICAL: Unexpected alteration type");
        }

        writeTableToFile(table,tableFile);
        output = defaultOutput();
    }

    public void join( String tableName1, String tableName2, String attribute1, String attribute2) throws IOException, ParseException {
        File tableFile1 = getFileOfTable(tableName1, true);
        File tableFile2 = getFileOfTable(tableName2, true);
        Table table1 = readTableFromFile(tableFile1);
        Table table2 = readTableFromFile(tableFile2);
        if (!table1.attributes.contains(attribute1) || !table2.attributes.contains(attribute2)) throw new ParseException("Attributes are not contained in the tables");

        ArrayList<String> attributes = new ArrayList<>();
        for (String attribute : table1.attributes) {
            if(!attribute.equals("id")) attributes.add(tableName1 + "." + attribute);
        }
        for (String attribute : table2.attributes) {
            if(!attribute.equals("id")) attributes.add(tableName2 + "." + attribute);
        }
        Table joinTable = new Table(attributes);

        for (Row row1 : table1.rows.values()) {
            for (Row row2 : table2.rows.values()) {
                if (row1.map.get(attribute1).getData().equals(row2.map.get(attribute2).getData())) {
                    ArrayList<Token> values = new ArrayList<>();
                    for (String attribute : table1.attributes) {
                        if(!attribute.equals("id")) values.add(row1.map.get(attribute));
                    }
                    for (String attribute : table2.attributes) {
                        if(!attribute.equals("id")) values.add(row2.map.get(attribute));
                    }
                    joinTable.insert(values);
                }
            }
        }

        ArrayList<String> output = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        for (String attribute : joinTable.attributes ) {
            line.append(attribute).append("\t");
        }
        output.add(line.toString());

        for (Row row : joinTable.rows.values()) {
            System.out.println("Inside a row of joinTable");
            line.setLength(0);
            for (String attribute : joinTable.attributes) {
                line.append(row.map.get(attribute)).append("\t");
            }
            output.add(line.toString());
        }
        this.output = output;
    }

    public ArrayList<String> getOutput() {
        return output;
    }

    private Table readTableFromFile(File tableFile) throws IOException, ParseException {
        FileInputStream readFromFile = new FileInputStream(tableFile);
        ObjectInputStream readObjIn = new ObjectInputStream(readFromFile);
        try {
            Table tb = (Table)readObjIn.readObject();
            readObjIn.close();
            readFromFile.close();
            return tb;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ParseException("CRITICAL: Couldn't deserialize Table file.");
        }
    }

    private void writeTableToFile(Table table, File file) throws IOException {
        FileOutputStream writeToFile = new FileOutputStream(file);
        ObjectOutputStream writer = new ObjectOutputStream(writeToFile);
        writer.writeObject(table);
        writeToFile.close();
        writer.close();
    }

    private File getFileOfTable(String tableName, boolean checkExists) throws ParseException {
        if (currDB == null) throw new ParseException("No Database Selected");
        File tableFile = new File(currDB.getName() + File.separator + tableName + extension);
        if(checkExists && !tableFile.exists()) {
            throw new ParseException("Table '" + tableName + "' does not exist");
        }
        return tableFile;
    }

    private ArrayList<String> defaultOutput() {
        ArrayList<String> out = new ArrayList<>();
        out.add("OK");
        return out;
    }


}
