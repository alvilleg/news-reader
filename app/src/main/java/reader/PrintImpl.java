package reader;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class PrintImpl implements Printer {

    final Output output;

    public PrintImpl(Output output) {
        this.output = output;
    }

    @Override
    public void print(Map<String, List<Content>> contents) {
        output.init();
        contents.entrySet().stream().sorted(Comparator.comparing(Map.Entry::getKey)).forEach(e -> printByType(e));
        output.close();
    }

    private void printByType(Map.Entry<String, List<Content>> stringListEntry) {
        output.write(String.format("%s (%s)", stringListEntry.getKey(), stringListEntry.getValue().size()));
        stringListEntry.getValue().stream().sorted(Comparator.comparing(Content::getTitle)).forEach(l -> output.write(l.getTitle()));
        output.write("-----------------");
    }
}
