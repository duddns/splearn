package tobyspring.splearn.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

  /*
   * NOTE property 줄 붙여쓰는것이 좋은가? 띄어쓰는것이 좋은가?
   */

  // NOTE 이 어노테이션을 붙였다고 해서 jpa 가 없으면 사용할 수 없는가? NO. 어노테이션 = 주석. 이 어노테이션을 붙였다고해서 특정 기술(jpa)에 종속적이라고 말할 정도는 아님.

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private Email email;

  private String nickname;

  private String passwordHash;

  @Enumerated(value = EnumType.STRING)
  private MemberStatus status;


  // NOTE memberRegisterRequest 의 이름은 어떤게 좋을까? request? registerRequest? memberRegisterRequest?
  public static Member register(MemberRegisterRequest memberRegisterRequest, PasswordEncoder passwordEncoder) {
    // NOTE 같은 타임 인자가 많아지면, 나중에 수정할 때 꼬일 수 있음
    // intellij inlay 기능으로 도움을 받을 수 있지만, 깃헙 등에서 코드를 보는 경우 어려울 수 있음
    // 방법1. builder 패턴을 적용하면 좋음
    // builder 패턴의 단점은 인자가 빠져도 코드가 실행됨
    // 방법2. 파라미터 오브젝트 방법도 가능함
    // 주로 record 를 활용

    // NOTE 정적 팩토리 매소드에서 생성자를 호출할 때 생성자를 넘기는데,
    // 이 부분도 기본 생성자로 객체를 생성하고,
    // 생성자 로직을 정적 팩토리 매소드에 넣는 방법을 사용할 수 있음

    Member member = new Member();

    member.email = new Email(memberRegisterRequest.email());
    member.nickname = Objects.requireNonNull(memberRegisterRequest.nickname());
    member.passwordHash = Objects.requireNonNull(passwordEncoder.encode(memberRegisterRequest.password()));

    member.status = MemberStatus.PENDING;

    return member;
  }

  public void activate() {
    // NOTE spring framework 의존성이 추가 되지만, utils 함수를 사용하는 정도는 큰 문제가 아니다. apache commons 사용하는 것과 마찬가지
    Assert.state(status == MemberStatus.PENDING, "PENDING 상태가 아닙니다.");

    this.status = MemberStatus.ACTIVE;
  }

  public void deactivate() {
    Assert.state(status == MemberStatus.ACTIVE, "ACTIVE 상태가 아닙니다.");

    this.status = MemberStatus.DEACTIVATED;
  }

  public boolean verifyPassword(String password, PasswordEncoder passwordEncoder) {
    return passwordEncoder.matches(password, passwordHash);
  }

  public void changeNickname(String nickname) {
    this.nickname = Objects.requireNonNull(nickname);
  }

  public void changePassword(String password, PasswordEncoder passwordEncoder) {
    this.passwordHash = passwordEncoder.encode(Objects.requireNonNull(password));
  }

  public boolean isActive() {
    // NOTE 외부에서 상태를 꺼내는 것은 로직에 적용하는 경우가 많다. 이런 경우 상태를 직접 꺼내지 말고 명확하게 상태를 확인하는 메소드를 만들면 좋다.
    // 모든 상태에 대해 확인하는 메소드를 만들어야 할까? 처음부터 만들어 놓지 말고 필요한 경우에 만들자.
    return status == MemberStatus.ACTIVE;
  }
}
