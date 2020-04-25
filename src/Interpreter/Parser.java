package Interpreter;

import Interpreter.Engine.*;
import Interpreter.Tokenizer.Token;
import Interpreter.Tokenizer.TokenType;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

//TODO decent, but could use some refactoring, maybe move list parsing inside here
public class Parser {

    Map<String, Condition> operators;
    ArrayList<Token> tokens;
    int index;

    public Parser() {
        operators = Map.of("==", new OpEqual(),
                ">", new OpGreaterThan(),
                "<", new OpSmallerThan(),
                ">=", new OpGreaterOrEqual(),
                "<=", new OpSmallerOrEqual(),
                "!=", new OpNotEqual(),
                "LIKE", new OpLike());
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
        index = 0;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public int getIndex() {
        return index;
    }

    public void incIndex(int i, boolean checkOoB) throws ParseException {
        index+=i;
        if (checkOoB && index>=tokens.size()) throw new ParseException("Command is incomplete");
    }

    public Token getTokenAtIndex() {
        return tokens.get(index);
    }

    public Token getTokenAtIndex(int index) {
        return tokens.get(index);
    }

    public void checkLengthEquals(int length) throws ParseException {
        if(tokens.size() != length) throw new ParseException("This command needs exactly " + length + " words.");
    }

    public void checkLongerThan(int length) throws ParseException {
        if(tokens.size() <= length) throw new ParseException("This command needs at least " + length + " words.");
    }

    public void checkToken(TokenType type) throws ParseException {
        checkToken(type, index);
    }

    public void checkToken(TokenType type, String data) throws ParseException {
        checkToken(type, index);
        if(!tokens.get(index).getData().equalsIgnoreCase(data)) throw new ParseException("Required token of type " + type.name() + " with value " + data);
    }

    public void checkToken(TokenType type, int index) throws ParseException {
        if(!tokens.get(index).is(type)) throw new ParseException("Required token of type " + type.name() + " at index " + index);
    }

    public void checkTokenIsValue(int index) throws ParseException {
        if(!tokens.get(index).is(TokenType.STRLIT) &&
                !tokens.get(index).is(TokenType.BOOL) &&
                !tokens.get(index).is(TokenType.FLOAT) &&
                !tokens.get(index).is(TokenType.INT)) throw new ParseException("Required VALUE at index " + index);
    }

    public void checkTokenIsValue() throws ParseException {
        checkTokenIsValue(index);
    }

    public Operation parseCondition() throws ParseException {
        checkToken(TokenType.WHERE);
        incIndex(1, true);
        return recursiveParseCondition(index, tokens.size()-1);
    }

    private Operation recursiveParseCondition(int startIndex, int innerEnd) throws ParseException {
        if (getTokenAtIndex(startIndex).is(TokenType.LPAREN) && getTokenAtIndex(innerEnd).is(TokenType.RPAREN)) {
            return recursiveCall(startIndex,innerEnd);
        } else {
            return baseCase(startIndex, innerEnd);
        }

    }

    private Operation recursiveCall(int startIndex, int innerEnd) throws ParseException {
        int innerIndex = startIndex+1;
        int parentCount = 1;
        while(!getTokenAtIndex(innerIndex).is(TokenType.COND) && parentCount>0){
            if (tokens.get(innerIndex).is(TokenType.LPAREN)) parentCount++;
            if (tokens.get(innerIndex).is(TokenType.LPAREN)) parentCount--;
            innerIndex++;
            if (innerIndex>innerEnd) throw new ParseException("Incomplete Command");
        }
        checkToken(TokenType.RPAREN,innerIndex-1);
        checkToken(TokenType.LPAREN,innerIndex+1);
        if (innerIndex+2>=innerEnd) throw new ParseException("Incomplete Command");
        return new ConditionJoiner(recursiveParseCondition(startIndex+1,innerIndex-2),
                getTokenAtIndex(innerIndex).getData(),
                recursiveParseCondition(innerIndex+2,innerEnd-1));
    }

    private Operation baseCase(int innerIndex, int innerEnd) throws ParseException {
        checkToken(TokenType.NAME,innerIndex);
        String attribute = getTokenAtIndex(innerIndex).getData();
        innerIndex++;
        if (innerIndex>innerEnd) throw new ParseException("Incomplete Command");
        checkToken(TokenType.OPERATOR, innerIndex);
        Condition cond;
        try {
            cond = operators.get(tokens.get(innerIndex).getData()).getClass().getConstructor().newInstance();
        } catch (InstantiationException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new ParseException("CRITICAL: Error while parsing condition");
        }
        innerIndex++;
        if (innerIndex>innerEnd) throw new ParseException("Incomplete Command");
        checkTokenIsValue(innerIndex);
        cond.setAttribute(attribute);
        cond.setValue(getTokenAtIndex(innerIndex));
        return cond;
    }

    public void checkCommandFinished() throws ParseException {
        if(index!=tokens.size()-1) throw new ParseException("Reached end of syntax but command not finished");
    }



}
