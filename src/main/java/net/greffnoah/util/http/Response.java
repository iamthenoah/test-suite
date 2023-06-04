package net.greffnoah.util.http;

import lombok.Data;
import org.apache.http.HttpResponse;

@Data
public class Response<T> {
    public T body;
    public HttpResponse response;

    public static <T> Response<T> create(T object, HttpResponse response) {
        Response<T> res = new Response<>();
        res.setBody(object);
        res.setResponse(response);
        return res;
    }
}
