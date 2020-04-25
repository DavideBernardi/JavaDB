package Interpreter;


import Interpreter.Engine.Engine;
import Interpreter.Syntax.BNFCommand;
import Interpreter.Tokenizer.Token;
import Interpreter.Tokenizer.TokenType;
import Interpreter.Tokenizer.Tokenizer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//TODO Change execute return from List of strings to one big String
//Wrapper class containing Tokenizer, Parser, Engine.
public class Interpreter {

    Tokenizer tokenizer; //Create an array of tokens from the input
    Parser p; //Keeps track of where we are in the tokens array, check syntax is correct
    Engine eng; //Performs the database tasks
    BNFCommand command; //First

    public Interpreter() {
        command = new BNFCommand();
        p = new Parser();
        tokenizer = new Tokenizer();
        eng = new Engine();
    }

    public List<String> execute(String input) throws ParseException, IOException {
        if (input == null) throw new ParseException("No Input");

        ArrayList<Token> tokens = tokenizer.tokenize(input); //Tokenize command

        if (!tokens.get(tokens.size()-1).is(TokenType.END)) throw new ParseException("';' expected at the end of a command.");
        tokens.remove(tokens.size()-1);
        tokens.trimToSize();

        p.setTokens(tokens); //Put tokens inside parser
        command.interpret(p,eng); //Initiate interpreting
        return eng.getOutput();
    }




}
