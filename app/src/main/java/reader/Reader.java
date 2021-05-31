package reader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Reader {
    Map<String, List<Content>> read(String url) throws IOException, InterruptedException;
}
