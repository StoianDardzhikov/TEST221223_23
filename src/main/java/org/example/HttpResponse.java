package org.example;

import org.example.util.BodyHandlers.BodyHandlerOfString;
import org.example.util.Header;

import java.nio.charset.Charset;
import java.util.List;

public class HttpResponse<T> {
    public static class ResponseInfo {
        int status;
        List<Header> headers;
        byte[] body;

        public ResponseInfo(int status, List<Header> headers, byte[] body) {
            this.status = status;
            this.headers = headers;
            this.body = body;
        }

        public int getStatus() {
            return status;
        }

        public List<Header> getHeaders() {
            return headers;
        }

        public byte[] getBody() {
            return body;
        }
    }

    public static class BodyHandlers {
        public static BodyHandler<String> ofString() {
            return new BodyHandlerOfString();
        }
        public static BodyHandler<String> ofString(Charset charset) {
            return new BodyHandlerOfString(charset);
        }
    }

    public interface BodyHandler<T> {
        BodySubscriber<T> apply(ResponseInfo responseInfo);
    }

    public interface BodySubscriber<T> {
        T getBody();
    }

    T body;
    ResponseInfo responseInfo;

    public HttpResponse(ResponseInfo responseInfo, T body) {
        this.responseInfo = responseInfo;
        this.body = body;
    }

    public T body() {
        return body;
    }

    public int getStatus() {
        return responseInfo.getStatus();
    }

    public List<Header> getHeaders() {
        return responseInfo.getHeaders();
    }
}