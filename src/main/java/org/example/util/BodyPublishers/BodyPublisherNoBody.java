package org.example.util.BodyPublishers;

import org.example.HttpRequest;

import java.nio.ByteBuffer;

public class BodyPublisherNoBody implements HttpRequest.BodyPublisher {

    public ByteBuffer getBytes() {
        return ByteBuffer.allocate(0);
    }
}
