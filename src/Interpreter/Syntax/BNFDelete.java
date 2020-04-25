package Interpreter.Syntax;

import Interpreter.Engine.Engine;
import Interpreter.Engine.Operation;
import Interpreter.Parser;
import Interpreter.ParseException;
import Interpreter.Tokenizer.TokenType;
import java.io.IOException;

//OK
public class BNFDelete implements Expression {

    public void interpret(Parser p, Engine eng) throws ParseException, IOException {
        p.checkLongerThan(5);
        p.checkToken(TokenType.NAME);
        String tableName = p.getTokenAtIndex().getData();
        p.incIndex(1, true);
        Operation cond = p.parseCondition();
        eng.deleteFrom(tableName,cond);
    }
}
