package tobyspring.splearn.domain;

import java.util.regex.Pattern;

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
