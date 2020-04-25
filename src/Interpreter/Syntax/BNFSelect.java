package Interpreter.Syntax;

import Interpreter.Engine.Engine;
import Interpreter.Engine.Operation;
import Interpreter.Parser;
import Interpreter.ParseException;
import Interpreter.Tokenizer.TokenType;
import java.io.IOException;
import java.util.ArrayList;

//TODO refactor
public class BNFSelect implements Expression {

    public void interpret(Parser p, Engine eng) throws ParseException, IOException {
        p.checkLongerThan(3);
        ArrayList<String> attributes;
        if (p.getTokenAtIndex().is(TokenType.STAR)) {
            attributes = null;
            p.incIndex(1, true);
            p.checkToken(TokenType.FROM);
        } else {
            attributes = new ArrayList<>();
            while(!p.getTokenAtIndex().is(TokenType.FROM)) {
                p.checkToken(TokenType.NAME);
                attributes.add(p.getTokenAtIndex().getData());
                p.incIndex(1, true );
                if (p.getTokenAtIndex().is(TokenType.COMMA)) {
                    p.incIndex(1, true);
                } else {
                    p.checkToken(TokenType.FROM);
                }
            }
        }
        p.incIndex(1, true);
        p.checkToken(TokenType.NAME);
        String tableName = p.getTokenAtIndex().getData();

        Operation cond = null;
        if (p.getIndex()<p.getTokens().size()-1) {
            p.incIndex(1, true);
            cond = p.parseCondition();
        }
        eng.select(tableName, attributes, cond);
    }
}
