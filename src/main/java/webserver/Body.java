package webserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;
import utils.IOUtils;

public class Body {

    private String body;

    public Body(BufferedReader bufferedReader, Object contentLength) throws IOException {
        parseBody(bufferedReader, contentLength);
    }

    private void parseBody(BufferedReader bufferedReader, Object contentLength) throws IOException {
        int readSize = Optional.ofNullable(contentLength)
            .map(x -> (String) x)
            .map(Integer::parseInt)
            .orElse(0);

        this.body = IOUtils.readData(bufferedReader, readSize);
    }

    public <T> T bodyMapper(Class<T> type) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(IOUtils.parseStringToObject(body), type);
    }

    public String getBody() {
        return body;
    }
}
