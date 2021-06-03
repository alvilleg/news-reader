package reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class ReaderImpl implements Reader {

    @Override
    public Map<String, List<Content>> read(String url) throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().build();

        HttpResponse<String> httpResponse =
                httpClient.send(createRequest(url), HttpResponse.BodyHandlers.ofString());
        return process(httpResponse.body());
    }

    protected Map<String, List<Content>> process(String body) {
        List<Content> contentList = new LinkedList<>();
        Map<String, List<Content>> classifiedContent;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Map<String, Object>> map = objectMapper.readValue(body, Map.class);

            readWidgets(map, contentList);
            classifiedContent = contentList.stream().collect(Collectors.groupingBy(Content::getType));
        } catch (JsonProcessingException e) {
           throw new IllegalArgumentException();
        }
        return classifiedContent;
    }

    protected void readWidgets(Map<String, Map<String, Object>> map, List<Content> contentList) {
        if (!map.containsKey("data") || !map.get("data").containsKey("widgets")) {
            throw new IllegalArgumentException();
        }
        List<Map<String, Object>> widgets = (List) map.get("data").get("widgets");
        for (var w : widgets) {
            readContents(w, contentList);
        }
    }

    protected void readContents(Map w, List<Content> contentList) {
        if (!w.containsKey("contents")) {
            throw new IllegalArgumentException();
        }
        List<Map<String, Object>> contents = (List) w.get("contents");
        for (var c : contents) {
            Content content = Content.builder().type(String.valueOf(c.get("type"))).
                    title(String.valueOf(c.get("title"))).build();
            contentList.add(content);
        }
    }

    private HttpRequest createRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .headers("Content-type", "application/json")
                .build();
    }

}
