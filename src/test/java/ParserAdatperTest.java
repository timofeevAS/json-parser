import org.testng.annotations.Test;
import ru.timofeevAS.ParserAdapter;
import ru.timofeevAS.generated.ParseException;

import static org.testng.Assert.assertEquals;

public class ParserAdatperTest {
    @Test
    public void testParseToObject() throws ParseException {
        String jsonText = "{\"value1\":123}";
        Object result = ParserAdapter.parseToObject(jsonText);
        Object expect = "{value1=123}";

        assertEquals(expect.toString(), result.toString());
    }
}
