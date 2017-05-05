package ua.dp.dzms.loganalyzer.entity;

import java.time.LocalDateTime;

public class LogToken {
    private LocalDateTime localDateTime;
    private HttpMethod method;
    private String message;

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getMessage() {
        return message;
    }
}
