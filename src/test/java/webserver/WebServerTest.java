package webserver;

import static webserver.handler.Controller.mapping;
import static webserver.response.Response.emptyResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import webserver.request.Request;
import webserver.response.Response;

public class WebServerTest {

    private String testDirectory = "./src/test/resources/";

    public Request generateRequest(String request) throws IOException {
        InputStream in = new FileInputStream(new File(testDirectory + request));
        return new Request(in);
    }

    public Response generateResponse(Request request) {
        return mapping(request.getRequestType()).apply(request, emptyResponse());
    }
}
