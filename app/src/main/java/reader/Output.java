package reader;

public interface Output {
    void init();

    void write(String s);

    void close();
}
