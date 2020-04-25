package Interpreter.Engine;

import Interpreter.ParseException;
import Interpreter.Tokenizer.Token;
import Interpreter.Tokenizer.TokenType;

import java.util.List;

//OK
public class OpLike extends Condition {

    public OpLike() {
        validTokens = List.of(TokenType.STRLIT);
    }

    public boolean compare(Row row) throws ParseException {
        Token a = getA(row);
        if (!a.is(TokenType.STRLIT)) throw new ParseException("LIKE condition is only computable on String Values");
        return a.getData().contains(b.getData());
    }
}
