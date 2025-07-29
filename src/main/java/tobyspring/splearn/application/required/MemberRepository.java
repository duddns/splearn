package tobyspring.splearn.application.required;

import org.springframework.data.repository.Repository;
import tobyspring.splearn.domain.Member;

/**
 * 회웑 정보를 저장하거나 조회한다
 */
public interface MemberRepository extends Repository<Member, Long> {

  /*
   * application 에서 특정 기술(spring data jpa)에 종속되는 것에 대해 논의의 여지가 있음
   */

  Member save(Member member);
}
