package tobyspring.splearn.domain;

public class MemberFixture {

  public static MemberRegisterRequest createMemberRegisterRequest(String email) {
    return new MemberRegisterRequest(email, "nickname1", "secret1");
  }

  public static MemberRegisterRequest createMemberRegisterRequest() {
    return new MemberRegisterRequest("test@test.com", "nickname1", "secret1");
  }

  public static PasswordEncoder createPasswordEncoder() {
    return new PasswordEncoder() {

      @Override
      public String encode(String password) {
        return password.toUpperCase();
      }

      @Override
      public boolean matches(String password, String passwordHash) {
        return encode(password).equals(passwordHash);
      }
    };
  }
}
