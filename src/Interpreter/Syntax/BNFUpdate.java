package Interpreter.Syntax;

import Interpreter.Engine.Engine;
import Interpreter.Engine.Operation;
import Interpreter.Parser;
import Interpreter.ParseException;
import Interpreter.Tokenizer.Token;
import Interpreter.Tokenizer.TokenType;
import java.io.IOException;
import java.util.ArrayList;

//TODO refactor
public class BNFUpdate implements Expression {

    public void interpret(Parser p, Engine eng) throws ParseException, IOException {
        p.checkLongerThan(9);
        p.checkToken(TokenType.NAME);
        String tableName = p.getTokenAtIndex().getData();
        p.incIndex(1, true);
        p.checkToken(TokenType.SET);
        p.incIndex(1, true);
        ArrayList<String> attributes = new ArrayList<>();
        ArrayList<Token> values = new ArrayList<>();
        while(!p.getTokenAtIndex().is(TokenType.WHERE)) {
            p.checkToken(TokenType.NAME);
            attributes.add(p.getTokenAtIndex().getData());
            p.incIndex(1, true);
            p.checkToken(TokenType.ASSIGN);
            p.incIndex(1, true);
            p.checkTokenIsValue();
            values.add(p.getTokenAtIndex());
            p.incIndex(1, true);
            if (p.getTokenAtIndex().is(TokenType.COMMA)) {
                p.incIndex(1, true);
            } else {
                p.checkToken(TokenType.WHERE);
            }
        }
        Operation cond = p.parseCondition();
        eng.update(tableName, attributes, values, cond);
    }
}
