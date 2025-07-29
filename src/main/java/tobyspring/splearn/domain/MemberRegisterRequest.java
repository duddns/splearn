package tobyspring.splearn.domain;

public record MemberRegisterRequest(
    // 3개의 record component 를 받는 record header
    String email,
    String nickname,
    String password
) {

}
