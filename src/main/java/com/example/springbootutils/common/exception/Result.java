package com.example.springbootutils.common.exception;

public class Result {
    public boolean success;
    public Object value;

    public Result() {}

    public Result(boolean success) {
        this.success = success;
    }

    public Result(boolean success, Object value) {
        this.success = success;
        this.value = value;
    }
}
