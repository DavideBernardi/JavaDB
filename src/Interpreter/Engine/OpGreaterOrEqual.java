package Interpreter.Engine;

import Interpreter.ParseException;
import Interpreter.Tokenizer.Token;
import Interpreter.Tokenizer.TokenType;

import java.util.List;

//OK
public class OpGreaterOrEqual extends Condition {

    public OpGreaterOrEqual() {
        validTokens = List.of(TokenType.INT, TokenType.FLOAT);
    }

    public boolean compare(Row row) throws ParseException {
        Token a = getA(row);
        if (!validTokens.contains(a.getType())) throw new ParseException(">= is only computable on Number Values");
        return Float.parseFloat(a.getData()) >=  Float.parseFloat(b.getData());
    }
}
