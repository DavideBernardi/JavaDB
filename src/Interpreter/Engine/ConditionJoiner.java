package Interpreter.Engine;

import Interpreter.ParseException;


//OK
public class ConditionJoiner implements Operation {

    Operation cond1;
    Operation cond2;
    String joinType;

    public ConditionJoiner(Operation cond1,String joinType ,Operation cond2) {
        this.cond1 = cond1;
        this.joinType = joinType;
        this.cond2 = cond2;

    }

    public boolean compare(Row row) throws ParseException {
        switch(joinType.toUpperCase()) {
            case "OR":
                return cond1.compare(row) || cond2.compare(row);
            case "AND":
                return cond1.compare(row) && cond2.compare(row);
            default: throw new ParseException("CRITICAL: Unknown JOIN TYPE");
        }

    }
}
