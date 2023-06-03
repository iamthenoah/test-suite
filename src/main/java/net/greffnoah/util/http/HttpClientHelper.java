package net.greffnoah.util.http;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class HttpClientHelper {

    public static <T> T performRequest(HttpRequestType requestType, String uri, Object body, Class<T> responseType, String accessToken) throws Exception {
        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpRequestBase httpRequest;
        switch (requestType) {
            case GET:
                httpRequest = new HttpGet(uri);
                break;
            case POST:
                httpRequest = new HttpPost(uri);
                break;
            case DELETE:
                httpRequest = new HttpDelete(uri);
                break;
            case PUT:
                httpRequest = new HttpPut(uri);
                break;
            default:
                throw new IllegalArgumentException();
        }

        ObjectMapper objectMapper = new ObjectMapper();

        if (body != null) {
            String json = objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL).writerWithDefaultPrettyPrinter().writeValueAsString(body);
            StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
            assert httpRequest instanceof HttpEntityEnclosingRequestBase;
            ((HttpEntityEnclosingRequestBase) httpRequest).setEntity(stringEntity);
        }

        if (accessToken != null) {
            httpRequest.addHeader("Authorization", "Bearer " + accessToken);
        }

        HttpResponse response = httpClient.execute(httpRequest);

        String responseBody = EntityUtils.toString(response.getEntity());

        if (responseType == null) {
            return null;
        } else if (responseType.equals(String.class)) {
            return responseType.cast(responseBody);
        } else {
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.readValue(responseBody, responseType);
        }
    }

    public static <T> T performRequest(HttpRequestType requestType, String uri, Object body, Class<T> responseType) throws Exception {
        return performRequest(requestType, uri, body, responseType, null);
    }

    public static boolean isResponseSuccessful(HttpResponse response) {
        int statusCode = response.getStatusLine().getStatusCode();
        return statusCode >= HttpStatus.SC_OK && statusCode < HttpStatus.SC_MULTIPLE_CHOICES;
    }
}
