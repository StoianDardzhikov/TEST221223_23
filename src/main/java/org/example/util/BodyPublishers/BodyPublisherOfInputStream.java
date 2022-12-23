package org.example.util.BodyPublishers;

import org.example.HttpRequest;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class BodyPublisherOfInputStream implements HttpRequest.BodyPublisher {

    InputStream content;

    public BodyPublisherOfInputStream(InputStream content) {
        this.content = content;
    }

    public ByteBuffer getBytes() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        content.transferTo(byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return ByteBuffer.wrap(bytes);
    }
}