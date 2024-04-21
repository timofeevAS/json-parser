package ru.timofeevAS;

import ru.timofeevAS.pojos.Car;
import ru.timofeevAS.pojos.Dealer;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        String filepath = "src/main/resources/dealer.json";

        try {
            String content = Files.readString(Paths.get(filepath), StandardCharsets.UTF_8);

            Dealer dealer = ParserAdapter.parseToClass(content, Dealer.class);
            System.out.println(dealer);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}