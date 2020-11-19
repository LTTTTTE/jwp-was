package webserver.handler;

import static webserver.HttpMethod.GET;
import static webserver.HttpMethod.POST;

import db.DataBase;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import model.UserCreateRequest;
import model.UserLoginRequest;
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
                UserCreateRequest user = request.getBody(UserCreateRequest.class);
                DataBase.addUser(user.toEntity());

                response.redirectTo(request, "/index.html ");
                return response;
            }
        );
        mapper.put(
            RequestTypeMatcher.of(POST, "/user/login"),
            (request, response) -> {
                UserLoginRequest loginRequest = request.getBody(UserLoginRequest.class);
                boolean valid = Optional.ofNullable(DataBase.findUserById(loginRequest.getUserId()))
                    .filter(user -> user.isSamePassword(loginRequest.getPassword()))
                    .isPresent();

                if (valid) {
                    response.redirectTo(request, "/index.html ");
                } else {
                    response.redirectTo(request, "/user/login_failed.html ");
                }

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
