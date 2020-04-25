package Interpreter.Syntax;

import Interpreter.Engine.Engine;
import Interpreter.Parser;
import Interpreter.ParseException;
import Interpreter.Tokenizer.TokenType;

//OK
public class BNFDropDB implements Expression {

    public void interpret(Parser p, Engine eng) throws ParseException {
        p.checkLengthEquals(2);
        p.checkToken(TokenType.NAME);
        eng.dropDB(p.getTokenAtIndex().getData());
    }
}
