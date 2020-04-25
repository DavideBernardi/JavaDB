package Interpreter.Syntax;

import Interpreter.Engine.Engine;
import Interpreter.Parser;
import Interpreter.ParseException;
import Interpreter.Tokenizer.Token;
import Interpreter.Tokenizer.TokenType;

import java.io.IOException;
import java.util.List;

public class BNFJoin implements Expression {

    public void interpret(Parser p, Engine eng) throws ParseException, IOException {
        p.checkLengthEquals(8);
        p.checkToken(TokenType.NAME);
        String table1 = p.getTokenAtIndex().getData();
        p.incIndex(1, true);
        p.checkToken(TokenType.COND, "AND");
        p.incIndex(1, true);
        p.checkToken(TokenType.NAME);
        String table2 = p.getTokenAtIndex().getData();
        p.incIndex(1, true);
        p.checkToken(TokenType.ON);
        p.incIndex(1, true);
        p.checkToken(TokenType.NAME);
        String attribute1 = p.getTokenAtIndex().getData();
        p.incIndex(1, true);
        p.checkToken(TokenType.COND);
        p.incIndex(1, true);
        p.checkToken(TokenType.NAME);
        String attribute2 = p.getTokenAtIndex().getData();
        eng.join(table1, table2, attribute1, attribute2);
    }
}
