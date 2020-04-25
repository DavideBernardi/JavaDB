package Interpreter.Tokenizer;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//OK
public class Tokenizer {

    Pattern pattern;

    public Tokenizer() {
        StringBuilder patternBuffer = new StringBuilder();
        for(TokenType tkType : TokenType.values()) {
            patternBuffer.append(String.format("|(?<%s>%s)", tkType.name(), tkType.pattern()));
        }
        pattern = Pattern.compile(patternBuffer.substring(1), Pattern.CASE_INSENSITIVE);
    }

    public ArrayList<Token> tokenize(String input) {
            Matcher matcher = pattern.matcher(input);
            ArrayList<Token> tokens = new ArrayList<>();
            while (matcher.find()) {
                for (TokenType tkType : TokenType.values()) {
                    if (matcher.group(tkType.name()) != null) {
                        tokens.add(new Token(tkType, matcher.group(tkType.name())));
                    }
                }
            }
            return tokens;
    }
}
