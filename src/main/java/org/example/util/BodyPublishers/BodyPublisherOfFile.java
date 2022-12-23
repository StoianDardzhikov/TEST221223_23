package org.example.util.BodyPublishers;

import org.example.HttpRequest;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

public class BodyPublisherOfFile implements HttpRequest.BodyPublisher {
    Path filePath;

    public BodyPublisherOfFile(Path filePath) {
        this.filePath = filePath;
    }

    public ByteBuffer getBytes() throws Exception {
        if (Files.notExists(filePath))
            throw new FileNotFoundException("File not found: " + filePath);

        InputStream inputStream = Files.newInputStream(filePath);
        BodyPublisherOfInputStream bodyPublisherOfInputStream = new BodyPublisherOfInputStream(inputStream);
        return bodyPublisherOfInputStream.getBytes();
    }
}
