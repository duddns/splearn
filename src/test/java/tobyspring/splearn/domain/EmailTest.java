package tobyspring.splearn.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class EmailTest {

  @Test
  void test_equality() {
    Email email1 = new Email("test1@test.com");
    Email email2 = new Email("test1@test.com");

    Assertions.assertThat(email1).isEqualTo(email2);
  }
}
