package com.jslog_spring.global.aspect;

import com.jslog_spring.global.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@ControllerAdvice
public class ApiResponseAdvice implements ResponseBodyAdvice<ApiResponse<?>> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return ApiResponse.class.isAssignableFrom(returnType.getParameterType());
    }

    @Override
    public ApiResponse<?> beforeBodyWrite(
            ApiResponse<?> body,
            MethodParameter returnType,
            MediaType selectedContentType,
            Class<? extends HttpMessageConverter<?>> selectedConverterType,
            ServerHttpRequest request,
            ServerHttpResponse response
    ) {
        if (body.getCookies() != null) {
            body.getCookies().forEach(cookie ->
                    response.getHeaders().add("Set-Cookie", cookie.toString())
            );
        }
        // 공통 헤더 추가 가능
        response.getHeaders().add("X-API-VERSION", "1.0");

        // 상태 코드 설정
        response.setStatusCode(HttpStatusCode.valueOf(Integer.parseInt(body.getResultCode())));

        // 로깅 가능
        log.info("API 응답: {}", body);
        // body 자체는 그대로 반환
        return body;
    }
}
