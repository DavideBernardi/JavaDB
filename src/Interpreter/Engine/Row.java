package Interpreter.Engine;

import Interpreter.Tokenizer.Token;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

//OK
public class Row implements Serializable {

    HashMap<String, Token> map;

    Row(ArrayList<String> attributes, ArrayList<Token> values) {
        map = new HashMap<>();
        for(int i=0;i<attributes.size();i++) {
            map.put(attributes.get(i),values.get(i));
        }
    }
}
