package com.example.jwtstarterkit.dtos.responses;

import java.time.LocalDateTime;

public class SuccessResponse<T> {

    private LocalDateTime timestamp;
    private boolean success;
    private T data;

    public SuccessResponse() {
        this.timestamp = LocalDateTime.now();
        this.success = true;
    }

    public SuccessResponse(T data) {
        this();
        this.data = data;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
