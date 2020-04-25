package Interpreter.Syntax;

import Interpreter.Engine.Engine;
import Interpreter.Parser;
import Interpreter.ParseException;
import Interpreter.Tokenizer.Token;
import Interpreter.Tokenizer.TokenType;
import java.io.IOException;
import java.util.ArrayList;

public class BNFInsert implements Expression {

    public void interpret(Parser p, Engine eng) throws ParseException, IOException {
        p.checkLongerThan(5);
        p.checkToken(TokenType.NAME);
        String tableName = p.getTokenAtIndex().getData();
        p.incIndex(1, true);
        p.checkToken(TokenType.VALUES);
        p.incIndex(1, true);
        p.checkToken(TokenType.LPAREN);
        p.incIndex(1, true);

        p.checkToken(TokenType.RPAREN,p.getTokens().size()-1);
        ArrayList<Token> values = new ArrayList<>();
        while(!p.getTokenAtIndex().is(TokenType.RPAREN)) {
            p.checkTokenIsValue();
            values.add(p.getTokenAtIndex());
            p.incIndex(1, true);
            if(p.getIndex() == p.getTokens().size()-1) {
                p.checkToken(TokenType.RPAREN);
            } else {
                p.checkToken(TokenType.COMMA);
                p.incIndex(1, true);
            }
        }
        p.checkCommandFinished();
        eng.insertInto(tableName, values);
    }
}
