package Interpreter.Syntax;

import Interpreter.Engine.Engine;
import Interpreter.Parser;
import Interpreter.ParseException;
import Interpreter.Tokenizer.TokenType;
import java.io.IOException;
import java.util.Map;

//OK
import static Interpreter.Tokenizer.TokenType.*;

public class BNFCommand implements Expression {

    Map<TokenType, Expression> commandTypes;

    public BNFCommand() {
        commandTypes = Map.ofEntries(Map.entry(USE, new BNFUse()),
                Map.entry(CREATEDB, new BNFCreateDB()),
                Map.entry(CREATETB, new BNFCreateTB()),
                Map.entry(DROPDB, new BNFDropDB()),
                Map.entry(DROPTB, new BNFDropTB()),
                Map.entry(INSERT, new BNFInsert()),
                Map.entry(SELECT, new BNFSelect()),
                Map.entry(UPDATE, new BNFUpdate()),
                Map.entry(DELETE, new BNFDelete()),
                Map.entry(JOIN, new BNFJoin()),
                Map.entry(ALTER, new BNFAlter()));
    }

    public void interpret(Parser p, Engine eng) throws ParseException, IOException {
        Expression commandType = commandTypes.get(p.getTokenAtIndex().getType());
        if (commandType == null) throw new ParseException("Invalid command type: " + p.getTokenAtIndex());
        p.incIndex(1, true);
        commandType.interpret(p,eng);
    }
}
