package ru.netology;

import java.util.List;

public class Request {
    protected String path;
    protected String params;

    public Request(String path) {
        this.path=path;
    }

    public void setParams(String params){
        this.params=params;
    }

    String getQueryParam(String name){
        return getQueryParams().getFirst();
    }

    List<String> getQueryParams(){
        return List.of(params.split(" "));
    }

}
