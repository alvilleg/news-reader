package reader;

import java.util.List;
import java.util.Map;

public interface Printer {
    void print(Map<String, List<Content>> contents);
}
