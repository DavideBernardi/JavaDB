package Interpreter.Tokenizer;

import java.io.Serializable;

//OK
public class Token implements Serializable {

    TokenType type;
    String data;

    public Token(TokenType type, String data) {
        this.type = type;
        //If the token is a String Literal, strip the '' from the String
        if (type.equals(TokenType.STRLIT)) {
            data = data.substring(1,data.length()-1);
        }
        this.data = data;
    }

    public TokenType getType() {
        return type;
    }

    public String getData() {
        return data;
    }

    public boolean is(TokenType type) {
        return this.type.equals(type);
    }

    public String toString() {
        return data;
    }
}
