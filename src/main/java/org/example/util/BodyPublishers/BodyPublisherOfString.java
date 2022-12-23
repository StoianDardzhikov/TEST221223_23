package org.example.util.BodyPublishers;

import org.example.HttpRequest;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class BodyPublisherOfString implements HttpRequest.BodyPublisher {

    String content;

    public BodyPublisherOfString(String content) {
        this.content = content;
    }

    public ByteBuffer getBytes() {
        byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
        return ByteBuffer.wrap(bytes);
    }
}
