# Suggested Improvements for `jslog_spring`

이 문서는 `jslog_spring` 프로젝트에 대한 개선 제안 사항을 **GitHub Issues 형식**으로 정리한 것입니다.  
각 항목은 `제목`, `설명`, `Action`, `라벨`, `우선순위`, `난이도`로 구성되어 있습니다.

---

## I. Security & Configuration

### 1. [Security] 민감정보 외부화 (`jwt.secret-key`, `app.admin-api-key`)
- **설명:** `application.yml`에 하드코딩된 키가 있어 보안 위험이 존재함
- **Action:** 환경변수 또는 Vault/Config Server를 사용하여 외부화
- **라벨:** `security`
- **우선순위:** 🔴 High
- **난이도:** M

---

### 2. [Database] 운영환경 DDL 전략 변경
- **설명:** `ddl-auto: update` 설정은 운영환경에서 데이터 손실 위험이 있음
- **Action:** 운영환경은 `none` 또는 `validate`로 변경, Flyway/Liquibase 적용
- **라벨:** `database`, `infra`
- **우선순위:** 🔴 High
- **난이도:** L

---

### 3. [Redis] 키 프리픽스 적용
- **설명:** username을 직접 Redis key로 사용 중 → 충돌 위험 존재
- **Action:** `refresh_token:username` 형태로 prefix 추가
- **라벨:** `infra`, `redis`
- **우선순위:** 🟠 Medium
- **난이도:** S

---

## II. API Design & Consistency

### 4. [API] 응답 구조 일관성 확보
- **설명:** `getAllPosts`는 `Page<Dto>` 직접 반환, 다른 API는 `ApiResponse` 사용 → 불일치
- **Action:** `Page<Dto>` 응답을 `ApiResponse`로 감싸기
- **라벨:** `api`, `consistency`
- **우선순위:** 🟠 Medium
- **난이도:** S

---

### 5. [API] 엔드포인트 네이밍 일관성 (문서 vs 코드)
- **설명:** `GEMINI.md` 문서에는 `/api/members` 명시, 실제 구현은 `/api/auth` 사용
- **Action:** 코드와 문서를 일치시키기 (엔드포인트 변경 또는 문서 수정)
- **라벨:** `api`, `documentation`
- **우선순위:** 🟡 Low ~ 🟠 Medium
- **난이도:** S

---

## III. Error Handling & Validation

### 6. [Auth] 권한 실패 예외 처리 개선
- **설명:** 현재 `NoSuchElementException` 사용 → 의미적으로 부정확
- **Action:** `AccessDeniedException` 또는 커스텀 예외 생성 후 글로벌 핸들러에 매핑
- **라벨:** `auth`, `exception`
- **우선순위:** 🔴 High
- **난이도:** M

---

### 7. [Validation] 요청 DTO 유효성 검증 추가
- **설명:** DTO에 `@NotBlank`, `@Email`, `@Size` 등 검증 누락
- **Action:** Bean Validation 추가 + Controller에서 `@Valid` 적용
- **라벨:** `validation`, `api`
- **우선순위:** 🟠 Medium
- **난이도:** S

---

### 8. [Exception] 일반 예외 처리 개선
- **설명:** `getPostById`에 `catch (Exception e)` → 문제 원인 파악 어려움
- **Action:** 구체적인 예외 처리 + 전역 예외 처리기(`ApiResponseAdvice`)에 위임
- **라벨:** `exception`, `api`
- **우선순위:** 🟠 Medium
- **난이도:** M

---

### 9. [ErrorCode] 에러 코드 일관성 유지
- **설명:** `TOKEN_NOT_FOUND` 코드가 `"A00"` → `"A003"`과 같은 패턴과 불일치
- **Action:** 코드 네이밍 패턴 일치시키기
- **라벨:** `exception`, `consistency`
- **우선순위:** 🟡 Low
- **난이도:** S

---

## IV. Code Quality & Maintainability

### 10. [Security] Refresh Token 단일 사용 정책
- **설명:** 현재 refresh token은 만료 전까지 재사용 가능 → 재사용 공격 위험
- **Action:** 재발급 시 Redis에서 기존 토큰 삭제 (1회용으로 변경)
- **라벨:** `security`, `auth`
- **우선순위:** 🔴 High
- **난이도:** M

---

### 11. [Refactor] BaseEntity @Id 제거 확인
- **설명:** `BaseEntity`의 `@Id` 사용 여부 확인 필요 → 엔티티별로 선언하는 것이 바람직
- **Action:** `BaseEntity`에서 `@Id` 제거, 각 엔티티에 개별 선언
- **라벨:** `refactor`, `entity`
- **우선순위:** 🟠 Medium
- **난이도:** S

---

### 12. [Cleanup] Deprecated 메서드 제거
- **설명:** `MemberService.getMember` @Deprecated → 실제 사용 여부 확인 필요
- **Action:** 사용 안 되면 제거, 사용 중이면 대체 메서드로 교체
- **라벨:** `cleanup`, `refactor`
- **우선순위:** 🟠 Medium
- **난이도:** S

---

### 13. [Refactor] createdAt / updatedAt 네이밍 정리
- **설명:** 현재 `createDate`, `modifyDate` 사용 → JPA 관례와 불일치
- **Action:** `createdAt`, `updatedAt` 으로 변경
- **라벨:** `refactor`, `entity`
- **우선순위:** 🟡 Low
- **난이도:** S

---

### 14. [Test] 단위 테스트 보강
- **설명:** `PostServiceImplTest`는 충분하나 `MemberTest` 부족
- **Action:** Member 엔티티 및 서비스 테스트 강화
- **라벨:** `test`
- **우선순위:** 🟠 Medium
- **난이도:** M

---

### 15. [Performance] JPA Fetch 전략 최적화 검토
- **설명:** 현재 `EAGER` fetch → 성능 문제 가능성 있음
- **Action:** `FetchType.LAZY` + 필요 시 `@EntityGraph` 또는 DTO Projection 사용
- **라벨:** `performance`, `jpa`
- **우선순위:** 🟡 Low
- **난이도:** M  
