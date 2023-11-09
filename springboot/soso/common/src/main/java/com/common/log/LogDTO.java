package com.common.log;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LogDTO {
    String className;
    String methodName;
    String log;

    public LogDTO(String className, String methodName, String log) {
        this.className = className;
        this.methodName = methodName;
        this.log = log;
    }

    @Override
    public String toString() {
        return "[" + className + "]"
                + " " + "[" + methodName + "]"
                + " " + log;
    }
}
