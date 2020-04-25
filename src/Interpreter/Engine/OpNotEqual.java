package Interpreter.Engine;

import Interpreter.ParseException;
import Interpreter.Tokenizer.Token;
import Interpreter.Tokenizer.TokenType;

import java.util.List;

//OK
public class OpNotEqual extends Condition {

    public OpNotEqual() {
        validTokens = List.of(TokenType.STRLIT, TokenType.BOOL, TokenType.INT, TokenType.FLOAT);
    }

    public boolean compare(Row row) throws ParseException {
        Token a = getA(row);
        switch (a.getType()) {
            case STRLIT:
            case BOOL:
                if (!a.is(b.getType())) throw new ParseException("Compared two values of different type");
                return !a.getData().equals(b.getData());
            case INT:
            case FLOAT:
                if (!b.is(TokenType.INT)&&!b.is(TokenType.FLOAT)) throw new ParseException("Compared two values of incomparable types");
                return Float.parseFloat(a.getData()) !=  Float.parseFloat(b.getData());
            default:
                throw new ParseException("CRITICAL: compared two non-value tokens");
        }
    }
}
