package org.example;

import org.example.util.BodyPublishers.*;
import org.example.util.Header;

import java.io.InputStream;
import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class HttpRequest {
    public static class BodyPublishers {
        public static BodyPublisher ofFile(Path path) {
            return new BodyPublisherOfFile(path);
        }

        public static BodyPublisher ofInputStream(InputStream is) {
            return new BodyPublisherOfInputStream(is);
        }

        public static BodyPublisher ofString(String str) {
            return new BodyPublisherOfString(str);
        }

        public static BodyPublisher ofByteArray(byte[] arr) {
            return new BodyPublisherOfByteArray(arr);
        }

        public static BodyPublisher noBody() {
            return new BodyPublisherNoBody();
        }
    }
    public interface BodyPublisher {
        ByteBuffer getBytes() throws Exception;
    }
    static class Builder {
        URI uri;
        String method;
        List<Header> headers = new ArrayList<>();

        BodyPublisher bodyPublisher;
        public Builder() {}
        public Builder(URI uri) {
            this.uri = uri;
        }

        public Builder uri(URI uri) {
            this.uri = uri;
            return this;
        }

        public Builder GET() {
            this.method = "GET";
            return this;
        }

        public Builder POST(BodyPublisher bodyPublisher) {
            this.method = "POST";
            this.bodyPublisher = bodyPublisher;
            return this;
        }

        public Builder PUT() {
            this.method = "PUT";
            return this;
        }

        public Builder DELETE() {
            this.method = "DELETE";
            return this;
        }

        public Builder headers(String... headers) {
            if (headers.length % 2 == 0)
                throw new IllegalArgumentException("No value for header: " + headers[headers.length - 1]);
            for (int i = 0; i < headers.length - 1; i += 2) {
                String key = headers[i];
                String value = headers[i + 1];
                Header header = new Header(key, value);
                this.headers.add(header);
            }
            return this;
        }

        public Builder header(String key, String value) {
            if (key == null || value == null)
                throw new IllegalArgumentException("Key and/or value cannot be null!");
            Header header = new Header(key, value);
            headers.add(header);
            return this;
        }

        public HttpRequest build() {
            return new HttpRequest(method, uri, headers, bodyPublisher);
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(URI uri) {
        return new Builder(uri);
    }

    URI uri;
    String method;
    List<Header> headers;
    BodyPublisher bodyPublisher;

    private HttpRequest(String method, URI uri, List<Header> headers, BodyPublisher bodyPublisher) {
        this.method = method;
        this.uri = uri;
        this.headers = headers;
        this.bodyPublisher = bodyPublisher;
    }

    BodyPublisher getBodyPublisher() {
        return bodyPublisher;
    }

    URI getURI() {
        return uri;
    }

    String getMethod() {
        return method;
    }

    List<Header> getHeaders() {
        return headers;
    }
}
