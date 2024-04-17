package ru.timofeevAS;

import ru.timofeevAS.generated.JSONParser;

import java.io.ByteArrayInputStream;

public class MyJSONParser extends JSONParser {

    public MyJSONParser (String text){
        super(new ByteArrayInputStream(text.getBytes()));
    }
}
