package com.jslog_spring.global.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {
    @JsonIgnore
    private final String resultCode;
    private final String msg;
    private final T data;

    public static <T> ApiResponse<T> success(String resultCode, String msg, T data) {
        return of(resultCode, msg, data);
    }


    public static ApiResponse<Void> success(String resultCode, String msg) {
        return of(resultCode, msg, null);
    }

    public static <T> ApiResponse<T> failure(String resultCode, String msg) {
        return of(resultCode, msg, null);
    }

    public static <T> ApiResponse<T> of(String resultCode, String msg, T data) {
        return new ApiResponse<>(resultCode, msg, data);
    }
}
