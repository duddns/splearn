package tobyspring.splearn.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MemberTest {

  @Test
  void test_createMember() {
    Member member = new Member("test@test.com", "nickname1", "passwordHash1");

    Assertions.assertThat(member.getEmail()).isEqualTo("test@test.com");
    Assertions.assertThat(member.getNickname()).isEqualTo("nickname1");
    Assertions.assertThat(member.getPasswordHash()).isEqualTo("passwordHash1");
    Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
  }

  @Test
  void test_constructorNullCheck() {
    Assertions.assertThatThrownBy(() -> new Member(null, "nickname1", "passwordHash1"))
        .isInstanceOf(NullPointerException.class);
  }

  @Test
  void test_activate() {
    Member member = new Member("test@test.com", "nickname1", "passwordHash1");

    member.activate();

    Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVE);
  }

  @Test
  void test_activateFail() {
    Member member = new Member("test@test.com", "nickname1", "passwordHash1");

    member.activate();

    Assertions.assertThatThrownBy(() -> {
      member.activate();
    }).isInstanceOf(IllegalStateException.class);
  }

  @Test
  void test_deactivate() {
    Member member = new Member("test@test.com", "nickname1", "passwordHash1");

    member.activate();
    member.deactivate();

    Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
  }

  @Test
  void test_deactivateFail() {
    Member member = new Member("test@test.com", "nickname1", "passwordHash1");

    member.activate();
    member.deactivate();

    Assertions.assertThatThrownBy(() -> {
      member.activate();
    }).isInstanceOf(IllegalStateException.class);
  }
}
