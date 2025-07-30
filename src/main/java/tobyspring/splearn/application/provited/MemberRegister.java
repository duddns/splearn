package tobyspring.splearn.application.provited;

import tobyspring.splearn.domain.Member;
import tobyspring.splearn.domain.MemberRegisterRequest;

/**
 * 회원의 등록과 관련된 기능을 제공한다
 */
public interface MemberRegister {

  /*
   * NOTE application 에서 entity 를 반환하는 것에 대하여 논의의 여지가 있음
   */

  Member register(MemberRegisterRequest memberRegisterRequest);
}
