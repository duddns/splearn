package tobyspring.splearn.domain;

public interface PasswordEncoder {

  /*
   * NOTE 패스워드 암호화는 application 의 required 인터페이스에 해당할 수 있음
   * 하지만 application.required 패키지로 이 인터페이스를 옮기면 domain -> application 을 참조하는 상황이 발생함
   * 이것은 옳지 않음
   * 도메인도 애플리케이션 안에 있는 것
   * PasswordEncoder 가 도메인에 있어도 application 안에 required 인터페이스를 정의한다라는 헥사고날 아키텍처의 기본 구현 방식에 잘 맞는것임
   */

  String encode(String password);

  boolean matches(String password, String passwordHash);
}
