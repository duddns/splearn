package tobyspring.splearn.domain;

import jakarta.persistence.Embeddable;
import java.util.regex.Pattern;

// jpa 3.2 부터 record 에 @Embeddable 을 사용할 수 있음
@Embeddable
public record Email(
    String address
) {

  // RFC 5322
  // ^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$
  // OWASP
  // ^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$
  private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

  public Email {
    if (!EMAIL_PATTERN.matcher(address).matches()) {
      throw new IllegalArgumentException("이메일 형식이 올바르지 않습니다: " + address);
    }
  }
}
