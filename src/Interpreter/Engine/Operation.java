package Interpreter.Engine;

import Interpreter.ParseException;

//OK
public interface Operation {
    boolean compare(Row row) throws ParseException;
}
