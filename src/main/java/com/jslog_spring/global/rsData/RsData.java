package com.jslog_spring.global.rsData;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@AllArgsConstructor
public class RsData<T> {
    private final String resultCode;
    private final String msg;
    private final T data;

    // (1) 성공 응답을 위한 팩토리 메서드
    public static <T> RsData<T> success(String msg, T data) {
        // new RsData(...) 대신 of()를 호출
        return of("S-1", msg, data);
    }

    // (2) 성공 응답 (데이터 없음)을 위한 팩토리 메서드
    public static RsData<Void> success(String msg) {
        // new RsData(...) 대신 of()를 호출
        return of("S-1", msg, null);
    }

    // (3) 실패 응답을 위한 팩토리 메서드 (오류 코드와 메시지)
    public static <T> RsData<T> fail(String resultCode, String msg) {
        // new RsData(...) 대신 of()를 호출
        return of(resultCode, msg, null);
    }

    // (4) 실패 응답 (일반적인 실패 메시지)을 위한 팩토리 메서드
    public static <T> RsData<T> fail(String msg) {
        // new RsData(...) 대신 of()를 호출
        return of("F-1", msg, null);
    }

    // (5) 'of' 팩토리 메서드: 공통 로직의 중앙 집중식 관리
    public static <T> RsData<T> of(String resultCode, String msg, T data) {
        // *** RsData 객체가 생성될 때마다 항상 수행될 공통 로직을 여기에 작성합니다. ***
        log.info("RsData 생성됨: resultCode={}, msg={}", resultCode, msg);

        return new RsData<>(resultCode, msg, data); // 실제 객체 생성은 기본 생성자에 위임
    }

    // 성공 여부 확인 메서드
    public boolean isSuccess() {
        return resultCode.startsWith("S-");
    }

    // 실패 여부 확인 메서드
    public boolean isFail() {
        return !isSuccess();
    }
}