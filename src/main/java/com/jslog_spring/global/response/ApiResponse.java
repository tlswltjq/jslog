package com.jslog_spring.global.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;

import java.util.List;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiResponse<T> {
    @JsonIgnore
    private final String resultCode;    //FIXME : 향후 미래에 코드 안정화 되면 String -> int
    private final String msg;
    private final T data;
    @JsonIgnore
    private final List<ResponseCookie> cookies;

    public static <T> ApiResponse<T> success(String resultCode, String msg, T data, List<ResponseCookie> cookies) {
        return of(resultCode, msg, data, cookies);
    }

    public static <T> ApiResponse<T> success(String resultCode, String msg, T data) {
        return of(resultCode, msg, data, null);
    }

    public static ApiResponse<Void> success(String resultCode, String msg) {
        return of(resultCode, msg, null, null);
    }

    public static <T> ApiResponse<T> failure(String resultCode, String msg) {
        return of(resultCode, msg, null, null);
    }

    public static <T> ApiResponse<T> of(String resultCode, String msg, T data, List<ResponseCookie> cookies) {
        return new ApiResponse<>(resultCode, msg, data, cookies);
    }
}
