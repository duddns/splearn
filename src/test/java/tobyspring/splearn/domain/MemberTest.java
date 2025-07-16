package tobyspring.splearn.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberTest {

  Member member;
  PasswordEncoder passwordEncoder;


  @BeforeEach
  void setUp() {
    passwordEncoder = new PasswordEncoder() {

      @Override
      public String encode(String password) {
        return password.toUpperCase();
      }

      @Override
      public boolean matches(String password, String passwordHash) {
        return encode(password).equals(passwordHash);
      }
    };

    MemberCreateRequest memberCreateRequest = new MemberCreateRequest("test@test.com", "nickname1", "secret1");

    member = Member.create(memberCreateRequest, passwordEncoder);
  }


  @Test
  void test_createMember() {
    Assertions.assertThat(member.getEmail()).isEqualTo("test@test.com");
    Assertions.assertThat(member.getNickname()).isEqualTo("nickname1");
    Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
  }

  @Test
  void test_activate() {
    member.activate();

    Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
  }

  @Test
  void test_activateFail() {
    member.activate();

    Assertions.assertThatThrownBy(() -> {
      member.activate();
    }).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void test_deactivate() {
    member.activate();
    member.deactivate();

    Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
  }

  @Test
  void test_deactivateFail() {
    member.activate();
    member.deactivate();

    Assertions.assertThatThrownBy(() -> {
      member.activate();
    }).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void test_verifyPassword() {
    Assertions.assertThat(member.verifyPassword("secret1", passwordEncoder)).isTrue();
    Assertions.assertThat(member.verifyPassword("secret2", passwordEncoder)).isFalse();
  }

  @Test
  void test_changeNickname() {
    Assertions.assertThat(member.getNickname()).isEqualTo("nickname1");

    member.changeNickname("nickname2");

    Assertions.assertThat(member.getNickname()).isEqualTo("nickname2");
  }

  @Test
  void test_changePassword() {
    member.changePassword("secret2", passwordEncoder);

    Assertions.assertThat(member.verifyPassword("secret2", passwordEncoder)).isTrue();
  }
}
