package Interpreter.Tokenizer;

import java.io.Serializable;

//OK
public enum TokenType implements Serializable {
    //Need to make case insensitive
    USE("USE"), // USE
    CREATEDB("CREATE DATABASE"), // CREATE DATABASE
    CREATETB("CREATE TABLE"), // CREATE TABLE
    LPAREN("\\("), // ( or )
    RPAREN("\\)"), // ( or )
    DROPDB("DROP DATABASE"), // DROP
    DROPTB("DROP TABLE"),
    ALTER("ALTER TABLE"), //ALTER TABLE
    ALTERATIONTYPE("ADD|DROP"), // ADD or DROP
    INSERT("INSERT INTO"), //INSERT INTO
    VALUES("VALUES"), //VALUES
    SELECT("SELECT"), // SELECT
    STAR("\\*"), //*
    COMMA(","), //,
    FROM("FROM"), // FROM
    WHERE("WHERE"), //WHERE
    UPDATE("UPDATE"), //UPDATE
    SET("SET"), //SET
    STRLIT("'[^']*'"), //'ANYTHING UNTIL'
    BOOL("true|false"), // TRUE or FALSE
    FLOAT("[-+]?[0-9]*\\.[0-9]+"), // num(+optional .num)
    INT("[-+]?[0-9]+"), // num
    COND("AND|OR"), //AND
    OPERATOR("\\>\\=|\\<\\=|\\=\\=|\\>|\\<|\\!\\=|LIKE"), // == > < >= <= != LIKE
    DELETE("DELETE FROM"), //DELETE FROM
    JOIN("JOIN"), //JOIN
    ON("ON"), //ON
    ASSIGN("\\="),
    END(";"),//=
    NAME("[A-Za-z][A-Za-z0-9_$.]+"); // TableName or AttrName


    private final String pattern;

    TokenType(String pattern) {
        this.pattern = pattern;
    }

    public final String pattern() {
        return pattern;
    }

}
