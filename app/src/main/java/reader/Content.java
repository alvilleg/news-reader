package reader;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Builder
@ToString
@Data
public class Content {
    private final String type;
    private final String title;
}
