package Interpreter.Engine;

import Interpreter.ParseException;
import Interpreter.Tokenizer.Token;
import Interpreter.Tokenizer.TokenType;

import java.util.List;

//OK
public abstract class Condition implements Operation {

    Token b;
    String attribute;
    List<TokenType> validTokens = null; //Must define in constructor

    public void setValue(Token value) throws ParseException {
        if (!validTokens.contains(value.getType())) throw new ParseException("Cannot use this operator on value of type " + value.getType());
        b = value;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    Token getA(Row row) throws ParseException {
        Token a = row.map.get(attribute);
        if (a==null) throw new ParseException("Attribute " + attribute + " is not in the table");
        return a;
    }
}
