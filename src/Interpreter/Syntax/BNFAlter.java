package Interpreter.Syntax;

import Interpreter.Engine.Engine;
import Interpreter.Parser;
import Interpreter.ParseException;
import Interpreter.Tokenizer.TokenType;
import java.io.IOException;
import java.util.List;

public class BNFAlter implements Expression {

    public void interpret(Parser p, Engine eng) throws ParseException, IOException {
        p.checkLengthEquals(4);
        p.checkToken(TokenType.NAME);
        String tableName = p.getTokenAtIndex().getData();
        p.incIndex(1, true);
        p.checkToken(TokenType.ALTERATIONTYPE);
        String alteration = p.getTokenAtIndex().getData();
        p.incIndex(1, true);
        p.checkToken(TokenType.NAME);
        String attribute = p.getTokenAtIndex().getData();
        eng.alter(tableName, alteration, attribute);
    }
}
