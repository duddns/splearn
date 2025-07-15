package tobyspring.splearn.domain;

import java.util.Objects;
import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class Member {

  /*
   * property 줄 붙여쓰는것이 좋은가? 띄어쓰는것이 좋은가?
   *
   */

  private String email;

  private String nickname;

  private String passwordHash;

  private MemberStatus status;


  private Member(String email, String nickname, String passwordHash) {
    this.email = Objects.requireNonNull(email);
    this.nickname = Objects.requireNonNull(nickname);
    this.passwordHash = Objects.requireNonNull(passwordHash);

    this.status = MemberStatus.PENDING;
  }

  public static Member create(String email, String nickname, String password, PasswordEncoder passwordEncoder) {
    return new Member(email, nickname, passwordEncoder.encode(password));
  }

  public void activate() {
    // spring framework 의존성이 추가 되지만, utils 함수를 사용하는 정도는 큰 문제가 아니다. apache commons 사용하는 것과 마찬가지
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
    this.nickname = nickname;
  }

  public void changePassword(String password, PasswordEncoder passwordEncoder) {
    this.passwordHash = passwordEncoder.encode(password);
  }
}
