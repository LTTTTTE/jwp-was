package webserver;

import static utils.FileIoUtils.decode;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import utils.FileIoUtils;
import utils.IOUtils;

public class Body {

    private String body;

    public Body(BufferedReader bufferedReader, String contentLength, String queryParams) throws IOException {
        parseBody(bufferedReader, contentLength);

        if (queryParams != null) {
            this.body += "&" + queryParams;
        }
    }

    public Body(String body) {
        this(body.getBytes(StandardCharsets.UTF_8));
    }

    public Body(byte[] fileData) {
        this.body = Optional.ofNullable(fileData)
            .map(FileIoUtils::encode)
            .orElse("");
    }

    private void parseBody(BufferedReader bufferedReader, String contentLength) throws IOException {
        int readSize = Optional.ofNullable(contentLength)
            .map(Integer::parseInt)
            .orElse(0);

        this.body = IOUtils.readData(bufferedReader, readSize);
    }

    public <T> T bodyMapper(Class<T> type) {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.convertValue(IOUtils.parseStringToObject(body), type);
    }

    public byte[] getBody() {
        return Base64.getDecoder().decode(body);
    }

    public int getLength() {
        return decode(body).length;
    }

    public boolean isEmpty() {
        return body.isEmpty();
    }
}
