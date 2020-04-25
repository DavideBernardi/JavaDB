package Interpreter.Engine;

import Interpreter.ParseException;
import Interpreter.Tokenizer.Token;
import Interpreter.Tokenizer.TokenType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

//OK
public class Table implements Serializable {

    ArrayList<String> attributes;
    HashMap<Integer, Row> rows;
    Integer index;

    public Table(ArrayList<String> attributes) {
        this.attributes = new ArrayList<>();
        this.attributes.add("id");
        this.attributes.addAll(attributes);
        rows = new HashMap<>();
        index = 1;
    }

    public void insert(ArrayList<Token> values) throws ParseException {
        if (values.size()!=attributes.size()-1) throw new ParseException(attributes.size() + " values required, " + values.size() + " received.");
        ArrayList<Token> valuesIn = new ArrayList<>();
        valuesIn.add(new Token(TokenType.INT,index.toString()));
        valuesIn.addAll(values);
        rows.put(index, new Row(attributes,valuesIn));
        index++;
    }

}
