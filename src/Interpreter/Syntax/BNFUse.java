package Interpreter.Syntax;

import Interpreter.*;
import Interpreter.Engine.Engine;
import Interpreter.Tokenizer.TokenType;

public class BNFUse implements Expression {

    public void interpret(Parser p, Engine eng) throws ParseException {
        p.checkLengthEquals(2);
        p.checkToken(TokenType.NAME);
        eng.useDB(p.getTokenAtIndex().getData());
    }
}
