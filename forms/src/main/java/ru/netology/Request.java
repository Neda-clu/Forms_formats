package ru.netology;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import java.io.BufferedOutputStream;

import java.nio.charset.StandardCharsets;
import java.util.List;


public class Request {
    private  String method;
    private String headers;
    private String body;
    public Request(String request_line,String headers,String body){
        this.method=request_line;
        this.headers=headers;
        this.body=body;
    }

    public String getMethod(){return method;}

    public String getQueryParam(String name) {
        List<NameValuePair> params = URLEncodedUtils.parse(headers, StandardCharsets.UTF_16);
        for (NameValuePair nameValuePair : params) {
            if (nameValuePair.getName() == name) {
                return nameValuePair.getValue();
            }
        }
        return "Not found ";
    }

    public List<org.apache.http.NameValuePair> getQueryParams() {
        List<NameValuePair> params = URLEncodedUtils.parse(headers, StandardCharsets.UTF_16);
        return params;
    }

}
