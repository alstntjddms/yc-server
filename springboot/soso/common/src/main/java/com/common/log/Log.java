package com.common.log;

import com.common.restapi.AsyncRestAPI;
import com.common.url.URL;

public class Log {
    private final String className;
    private final AsyncRestAPI asyncRestAPI;
    public Log(String className, AsyncRestAPI asyncRestAPI) {
        this.className = className;
        this.asyncRestAPI = asyncRestAPI;
    }
    public void debug(String methodName, String log) {
        asyncRestAPI.post(URL.LOG_SERVER + "/debug", new LogDTO(className, methodName, log), LogDTO.class).subscribe();
    }
    public void info(String methodName, String log) {
        asyncRestAPI.post(URL.LOG_SERVER + "/info", new LogDTO(className, methodName, log), LogDTO.class).subscribe();
    }
    public void warn(String methodName, String log) {
        asyncRestAPI.post(URL.LOG_SERVER + "/warn", new LogDTO(className, methodName, log), LogDTO.class).subscribe();
    }
}
