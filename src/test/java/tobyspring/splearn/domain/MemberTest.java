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
}
