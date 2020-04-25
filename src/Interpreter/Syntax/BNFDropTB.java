package Interpreter.Syntax;

import Interpreter.Engine.Engine;
import Interpreter.ParseException;
import Interpreter.Parser;
import Interpreter.Tokenizer.TokenType;

public class BNFDropTB implements Expression {

    public void interpret(Parser p, Engine eng) throws ParseException {
        p.checkLengthEquals(2);
        p.checkToken(TokenType.NAME);
        eng.dropTB(p.getTokenAtIndex().getData());
    }
}
