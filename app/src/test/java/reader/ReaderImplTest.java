package reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

class ReaderImplTest {

    @Test
    void testProcessInvalidData() {
        ReaderImpl reader = new ReaderImpl();
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                reader.process("abc"));
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                reader.process("{}"));
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                reader.process("{\"data\":{}}"));
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                reader.process("{\"data\":{\"widgets\":[{\"a\":\"\"}]}}"));
        Assertions.assertDoesNotThrow(() ->
                reader.process("{\"data\":{\"widgets\":[{\"contents\":[]}]}}"));
    }

    @Test
    void testProcessValidData() throws JsonProcessingException {
        ReaderImpl reader = new ReaderImpl();
        Map<String, List<Content>> contentResult =
                reader.process("{\"data\":{\"widgets\":[{\"contents\":[]}]}}");
        Assertions.assertTrue(contentResult.isEmpty());
        contentResult =
                reader.process("{\"data\":{\"widgets\":[{\"contents\":[" +
                        "{\"type\":\"slideshow\", \"title\":\"News Today\"}," +
                        "{\"type\":\"article\", \"title\":\"News\"}," +
                        "{\"type\":\"article\", \"title\":\"News Today\"}," +
                        "{\"type\":\"video\", \"title\":\"News Today\"}," +
                        "{\"type\":\"slideshow\", \"title\":\"News Today\"}," +
                        "{\"type\":\"video\", \"title\":\"News Today\"}," +
                        "{\"type\":\"slideshow\", \"title\":\"News Today\"}," +
                        "{\"type\":\"video\", \"title\":\"News Today\"}," +
                        "{\"type\":\"section\", \"title\":\"News Today\"}," +
                        "{\"type\":\"slideshow\", \"title\":\"News Today\"}" +
                        "]}]}}");
        Assertions.assertEquals(4, contentResult.size());
        Assertions.assertTrue(contentResult.containsKey("article"));
        Assertions.assertEquals(1, contentResult.get("section").size());
        Assertions.assertEquals(3, contentResult.get("video").size());
        Assertions.assertEquals(4, contentResult.get("slideshow").size());

    }
}