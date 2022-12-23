package org.example.util.BodyPublishers;

import org.example.HttpRequest;

import java.nio.ByteBuffer;

public class BodyPublisherOfByteArray implements HttpRequest.BodyPublisher {
    byte[] content;

    public BodyPublisherOfByteArray(byte[] content) {
        this.content = content;
    }

    public ByteBuffer getBytes() {
        return ByteBuffer.wrap(content);
    }
}