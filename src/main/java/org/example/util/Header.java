package org.example.util;

import java.nio.charset.StandardCharsets;

public class Header {
    String key;
    String value;

    public String toString() {
        return key + ": " + value + "\n";
    }

    public byte[] getBytes() {
        return toString().getBytes(StandardCharsets.UTF_8);
    }

    public Header(String key, String value) {
        this.key = key;
        this.value = value;
    }
}
