package webserver.handler;

import static webserver.HttpMethod.GET;
import static webserver.HttpMethod.POST;

import db.DataBase;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import model.User;
import webserver.request.Request;
import webserver.request.RequestType;
import webserver.request.RequestTypeMatcher;
import webserver.response.Response;

public class Controller {

    private static final Map<RequestTypeMatcher, BiFunction<Request, Response, Response>> mapper = new HashMap<>();

    static {
        mapper.put(
            RequestTypeMatcher.of(GET, "/"),
            (request, response) -> {
                response.redirectTo(request, "/index.html ");
                return response;
            }
        );
        mapper.put(
            RequestTypeMatcher.of(POST, "/user/create"),
            (request, response) -> {
                User user = request.getBody(User.class);
                DataBase.addUser(user);

                response.redirectTo(request, "/index.html ");
                return response;
            }
        );
    }

    public static BiFunction<Request, Response, Response> mapping(RequestType requestType) {
        return Optional.ofNullable(mapper.get(RequestTypeMatcher.of(requestType)))
            .orElseGet(() -> (request, response) -> {
                response.ok(request);
                return response;
            });
    }
}
