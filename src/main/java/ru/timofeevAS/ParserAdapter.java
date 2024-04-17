package ru.timofeevAS;

import ru.timofeevAS.generated.ParseException;

public class ParserAdapter {
    public static Object parseToObject(String text) throws ParseException {
        MyJSONParser parser = new MyJSONParser(text);
        return parser.parse();
    }
}
