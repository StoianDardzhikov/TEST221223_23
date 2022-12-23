package org.example;

import com.sun.source.doctree.SerialDataTree;
import org.example.util.Header;

import java.io.*;
import java.net.Socket;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class HttpClient {

    public <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> bodyHandler) throws Exception {
        URI uri = request.getURI();
        String host = uri.getHost();
        int port = uri.getPort();
        if (port < 0)
            port = 80;

        Socket socket = new Socket(host, port);
        String path = uri.getPath();
        String method = request.getMethod();
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write((method + " " + path + " HTTP/1.1\n").getBytes());
        List<Header> headers = request.getHeaders();
        headers.add(new Header("Host", host));
        for (Header header : headers) {
            outputStream.write(header.getBytes());
        }
        outputStream.write("\n".getBytes());
        HttpRequest.BodyPublisher bodyPublisher = request.getBodyPublisher();
        if (bodyPublisher != null) {
            outputStream.write(bodyPublisher.getBytes().array());
        }
        InputStream inputStream = socket.getInputStream();
        InputStreamReader isReader = new InputStreamReader(inputStream);
        BufferedReader reader = new BufferedReader(isReader);
        String statusString = reader.readLine();
        List<Header> responseHeaders = new ArrayList<>();
        int contentLength = 0;
        String headerString = reader.readLine();
        while (headerString != null && !headerString.equals("")) {
            String[] headerParts = headerString.split(":");
            String key = headerParts[0];
            String value = headerParts[1].substring(1);
            if (key.equals("Content-Length")) {
                contentLength = Integer.parseInt(value);
            }
            Header header = new Header(key, value);
            responseHeaders.add(header);
            headerString = reader.readLine();
        }

        int readBytes = 0;
        byte[] bodyBytes = new byte[contentLength];
        while (readBytes < contentLength) {
            bodyBytes[readBytes++] = (byte) reader.read();
        }
        String[] statusParts = statusString.split(" ");
        int status = Integer.parseInt(statusParts[1]);
        HttpResponse.ResponseInfo responseInfo = new HttpResponse.ResponseInfo(status, headers, bodyBytes);
        HttpResponse.BodySubscriber<T> bodySubscriber = bodyHandler.apply(responseInfo);
        T body = bodySubscriber.getBody();
        return new HttpResponse<T>(responseInfo, body);
    }
}