package ru.timofeevAS;

import ru.timofeevAS.generated.ParseException;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;

public class ParserAdapter {
    public static Object parseToObject(String text) throws ParseException {
        MyJSONParser parser = new MyJSONParser(text);
        return parser.parse();
    }

    public static Map<String, Object> parseToMap(String text) throws ParseException{
        MyJSONParser parser = new MyJSONParser(text);
        return parser.object();
    }
    public static boolean isBoxingClass(Class<?> clazz) {
        try {
            String pack = clazz.getPackage().getName();
            if (!"java.lang".equals(pack)){
                return false;
        }
        clazz.getField("TYPE");
        }
        catch (NoSuchFieldException | NullPointerException e)
        {
            return false;
        }
        return true;
    }

    private static boolean isPrimitiveBoxingOrString(Class<?> clazz){
        return clazz.isPrimitive() || isBoxingClass(clazz) || clazz.equals(String.class);
    }

    private static boolean isConsistInJson(Class<?> clazz) {
        if (isPrimitiveBoxingOrString(clazz)) {
            return true;
        }
        if (clazz.isArray()) {
            return isConsistInJson(clazz.getComponentType());
        }
        if (ArrayList.class.isAssignableFrom(clazz)) {
            return true;
        }


        if (Map.class.isAssignableFrom(clazz)) {
            return true;
        }

        if (clazz.getDeclaredFields().length > 0) {
            Field[] fields = clazz.getDeclaredFields();
            boolean result = false;
            for (Field field : fields){
                result = isConsistInJson(field.getType());
            }
            return result;
        }


        return false;
    }

    private static Field[] getFields(Class<?> clazz){
        Field[] fields = clazz.getDeclaredFields();
        ArrayList<String> fieldNames = new ArrayList<String>();
        for (Field field : fields) {
            fieldNames.add(field.getType().getSimpleName().toLowerCase());
        }
        return clazz.getDeclaredFields();
    }

    private static <T> T parsedValueToTClass(LinkedHashMap<String, Object> hashMap, Class<T> clazz) throws IllegalArgumentException, NoSuchFieldException, InstantiationException, IllegalAccessException {
        var obj = clazz.newInstance();

        for (String fieldName : hashMap.keySet()) {
            Object fieldValue = hashMap.get(fieldName);
            Field field = clazz.getDeclaredField(fieldName);

            if (isPrimitiveBoxingOrString(field.getType())) {
                // If primitive type
                field.setAccessible(true);
                field.set(obj, fieldValue);
            }
            else if (LinkedHashMap.class.isAssignableFrom(fieldValue.getClass())){
                // If field not primitive such as POJO class
                field.setAccessible(true);
                field.set(obj, parsedValueToTClass((LinkedHashMap<String, Object>) hashMap.get(fieldName),field.getType()));
            }
            else if (field.getType().isArray()){
                // If field is classic array[]
                Class<?> componentType = field.getType().getComponentType();
                field.setAccessible(true);
                var refList = (ArrayList<Object>) fieldValue;
                var array = Array.newInstance(componentType, ((ArrayList<?>) fieldValue).size());
                int index = 0;
                for (Object value : refList) {
                    if(LinkedHashMap.class.isAssignableFrom(value.getClass())) {
                        Array.set(array, index++, parsedValueToTClass((LinkedHashMap<String, Object>) value, componentType));
                    }
                    else if(isPrimitiveBoxingOrString(value.getClass())){
                        Array.set(array, index++, value);
                    }
                    else{
                        throw new IllegalArgumentException("Illegal insertion attempt.");
                    }
                }
                field.set(obj, array);
            }
        }

        return obj;
    }

    public static <T> T parseToClass(String text, Class<T> tClass) throws Exception {
        // If tClass contains fields, which we can't parse from JSON throw exception.
        if (!isConsistInJson(tClass)){
            throw new IllegalArgumentException("Can't parse into class: " + tClass.getName());
        }

        Map<String, Object> parsedJson = parseToMap(text);

        // Check parsed keys and fields sizes
        var tClassFields = tClass.getDeclaredFields();
        if (parsedJson.size() != tClassFields.length){
            throw new IllegalArgumentException("Json file and class has difference key set sizes: " + tClass.getName());
        }

        T tInstance = parsedValueToTClass((LinkedHashMap<String, Object>) parsedJson, tClass);
        return tInstance;
    }
}
