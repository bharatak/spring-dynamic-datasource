package com.example.dynamicbeanrefresh;

import org.springframework.context.ApplicationEvent;

public class DataSourceChangeEvent extends ApplicationEvent {
    private String url;

    private String username;

    private String password;

    public DataSourceChangeEvent(Object source) {
        super(source);
    }

    public DataSourceChangeEvent(Object source, String url, String username, String password) {
        super(source);
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
