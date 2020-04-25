package Interpreter.Syntax;

import Interpreter.Engine.Engine;
import Interpreter.ParseException;
import Interpreter.Parser;
import Interpreter.Tokenizer.TokenType;

import java.io.IOException;
import java.util.ArrayList;

//TODO Refactor List parsing
public class BNFCreateTB implements Expression{

    public void interpret(Parser p, Engine eng) throws ParseException, IOException {
        p.checkLongerThan(1);
        p.checkToken(TokenType.NAME);
        String tableName = p.getTokenAtIndex().getData();

        ArrayList<String> attributes = new ArrayList<>();
        if(p.getTokens().size() > 2) {
            p.incIndex(1, true);
            p.checkToken(TokenType.LPAREN);
            p.checkToken(TokenType.RPAREN,p.getTokens().size()-1);
            p.incIndex(1, true);
            while(!p.getTokenAtIndex().is(TokenType.RPAREN)) {
                p.checkToken(TokenType.NAME);
                attributes.add(p.getTokenAtIndex().getData());
                p.incIndex(1, true);
                if(p.getIndex() == p.getTokens().size()-1) {
                    p.checkToken(TokenType.RPAREN);
                } else {
                    p.checkToken(TokenType.COMMA);
                    p.incIndex(1, true);
                }
            }
        }
        p.checkCommandFinished();
        eng.createTB(tableName, attributes);
    }
}
