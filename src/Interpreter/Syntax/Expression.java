package Interpreter.Syntax;

import Interpreter.Engine.Engine;
import Interpreter.Parser;
import Interpreter.ParseException;
import java.io.IOException;

public interface Expression {
    void interpret(Parser p, Engine eng) throws ParseException, IOException;
}
