package org.example.util.BodyHandlers;

import org.example.HttpResponse;
import org.example.util.BodySubscribers.BodySubscriberOfString;

import java.nio.charset.Charset;

public class BodyHandlerOfString implements HttpResponse.BodyHandler<String> {
    Charset charset;

    public BodyHandlerOfString() {}

    public BodyHandlerOfString(Charset charset) {
        this.charset = charset;
    }

    public HttpResponse.BodySubscriber<String> apply(HttpResponse.ResponseInfo responseInfo) {
        if (charset != null)
            return new BodySubscriberOfString(responseInfo.getBody(), charset);
        return new BodySubscriberOfString(responseInfo.getBody());
    }
}