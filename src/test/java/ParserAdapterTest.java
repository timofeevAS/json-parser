import org.testng.annotations.Test;
import ru.timofeevAS.ParserAdapter;
import ru.timofeevAS.generated.ParseException;
import ru.timofeevAS.pojos.Dealer;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.testng.Assert.assertEquals;

public class ParserAdapterTest {
    @Test
    public void testParseToObject() throws ParseException {
        String jsonText = "{\"value1\":123}";
        Object result = ParserAdapter.parseToObject(jsonText);
        Object expect = "{value1=123}";

        assertEquals(expect.toString(), result.toString());
    }

    @Test
    public void TestParseToClass() {
        String filepath = "src/main/resources/dealer.json";

        try {
            String content = Files.readString(Paths.get(filepath), StandardCharsets.UTF_8);

            Dealer dealer = ParserAdapter.parseToClass(content, Dealer.class);
            String expect = "Dealer{partners=[partner1, partner2, partner3], cars=[Car{name='Volvo', isSold=false, owner=Person{name='Alexey', age=25}}, Car{name='Ford', isSold=true, owner=Person{name='Andrew', age=33}}], manager=Person{name='Michael', age=29}}\n";
            assertEquals(dealer.toString(), expect);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
