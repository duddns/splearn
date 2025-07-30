package tobyspring.splearn.domain;

import static tobyspring.splearn.domain.MemberFixture.createMemberRegisterRequest;
import static tobyspring.splearn.domain.MemberFixture.createPasswordEncoder;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MemberTest {

  Member member;
  PasswordEncoder passwordEncoder;


  @BeforeEach
  void setUp() {
    passwordEncoder = createPasswordEncoder();

    member = Member.register(createMemberRegisterRequest(), passwordEncoder);
  }


  @Test
  void test_registerMember() {
    Assertions.assertThat(member.getEmail().address()).isEqualTo("test@test.com");
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

  @Test
  void test_isActive() {
    Assertions.assertThat(member.isActive()).isFalse();

    member.activate();

    Assertions.assertThat(member.isActive()).isTrue();
  }

  @Test
  void test_invalidEmail() {
    Assertions.assertThatThrownBy(() -> {
      MemberRegisterRequest memberRegisterRequest = MemberFixture.createMemberRegisterRequest("invalid@email");
      Member.register(memberRegisterRequest, passwordEncoder);
    }).isInstanceOf(IllegalArgumentException.class);

    MemberRegisterRequest memberRegisterRequest = MemberFixture.createMemberRegisterRequest("valid@email.com");
    Member.register(memberRegisterRequest, passwordEncoder);
  }
}
