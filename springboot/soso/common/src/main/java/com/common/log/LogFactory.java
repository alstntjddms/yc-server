package com.common.log;

import com.common.restapi.AsyncRestAPI;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogFactory {
    @Autowired
    private AsyncRestAPI asyncRestAPI;
    private static LogFactory instance;

    public LogFactory(AsyncRestAPI asyncRestAPI) {
        this.asyncRestAPI = asyncRestAPI;
    }

    public static LogFactory getInstance() {
        if (instance == null) {
            throw new IllegalStateException("LogFactory is not initialized.");
        }
        return instance;
    }

    @PostConstruct
    private void initialize() {
        if (instance != null) {
            throw new IllegalStateException("LogFactory has already been initialized.");
        }
        instance = this;
    }

    public Log createLog(Class<?> clazz) {
        return new Log(clazz.getSimpleName(), asyncRestAPI);
    }
}
