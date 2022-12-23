package org.example.util.BodySubscribers;

import org.example.HttpResponse;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class BodySubscriberOfString implements HttpResponse.BodySubscriber<String> {
    byte[] content;
    Charset charset;

    public BodySubscriberOfString(byte[] content, Charset charset) {
        this.content = content;
        this.charset = charset;
    }

    public BodySubscriberOfString(byte[] content) {
        this(content, StandardCharsets.UTF_8);
    }

    public String getBody() {
        return new String(content, charset);
    }
}