package tobyspring.splearn.application.required;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import tobyspring.splearn.domain.Member;
import tobyspring.splearn.domain.MemberFixture;

@DataJpaTest
class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  EntityManager entityManager;


  @Test
  void test_registerMember() {
    Member member = Member.register(MemberFixture.createMemberRegisterRequest(), MemberFixture.createPasswordEncoder());

    Assertions.assertThat(member.getId()).isNull();

    memberRepository.save(member);

    Assertions.assertThat(member.getId()).isNotNull();

    entityManager.flush();
  }

  @Test
  void test_duplicateEmailFail() {
    Member member = Member.register(MemberFixture.createMemberRegisterRequest(), MemberFixture.createPasswordEncoder());

    memberRepository.save(member);

    Member member2 = Member.register(MemberFixture.createMemberRegisterRequest(), MemberFixture.createPasswordEncoder());
    Assertions.assertThatThrownBy(() -> memberRepository.save(member2))
        .isInstanceOf(DataIntegrityViolationException.class);
  }
}
