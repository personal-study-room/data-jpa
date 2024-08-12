package lecture.datajpa.repository;

import lecture.datajpa.entity.Member;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
@RequiredArgsConstructor
class MemberRepositoryTest {

  private final MemberRepository memberRepository;

  @Test
  void testMember() {
    System.out.println("memberRepository = " + memberRepository.getClass());
    // memberRepository = class jdk.proxy2.$Proxy129 -> 구현체는 알아서 꽂아준다

    Member member = Member.builder()
            .username("memberA")
            .build();
    Member savedMember = memberRepository.save(member);

    Member findMember = memberRepository.findById(member.getId()).orElse(null);

    assertThat(findMember).isEqualTo(member);
    assertThat(findMember).isEqualTo(savedMember);

    assertThat(findMember.getId()).isEqualTo(member.getId());
    assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

  }

  @Test
  void basicCRUD() {
    Member member1 = Member.builder()
            .username("member1")
            .build();

    Member member2 = Member.builder()
            .username("member2")
            .build();

    memberRepository.save(member1);
    memberRepository.save(member2);

    Member findMember1 = memberRepository.findById(member1.getId()).get();
    Member findMember2 = memberRepository.findById(member2.getId()).get();
    assertThat(findMember1).isEqualTo(member1);
    assertThat(findMember2).isEqualTo(member2);

    // 리스트 검증
    List<Member> result = memberRepository.findAll();
    assertThat(result.size()).isEqualTo(2);

    // 카운트 검증
    long count = memberRepository.count();
    assertThat(count).isEqualTo(2);

    //삭제
    memberRepository.delete(member1);
    memberRepository.delete(member2);
    long deletedCount = memberRepository.count();
    assertThat(deletedCount).isEqualTo(0);
  }

  @Test
  void findByUsernameAndAgeGreaterThen() {
    Member member1 = Member.builder()
            .username("memberA")
            .age(10)
            .build();

    Member member2 = Member.builder()
            .username("memberB")
            .age(20)
            .build();

    memberRepository.save(member1);
    memberRepository.save(member2);

    List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("memberB", 15);

    assertThat(result.get(0).getUsername()).isEqualTo("memberB");
    assertThat(result.get(0).getAge()).isEqualTo(20);
    assertThat(result.get(0)).isEqualTo(member2);

  }

  @Test
  void queryMethodTest() {
    Member member1 = Member.builder()
            .username("memberA")
            .age(10)
            .build();

    Member member2 = Member.builder()
            .username("memberB")
            .age(20)
            .build();

    memberRepository.save(member1);
    memberRepository.save(member2);

    /**
     *
     select
       m1_0.member_id,
       m1_0.age,
       m1_0.team_id,
       m1_0.username
     from
      member m1_0
     */
    List<Member> result = memberRepository.findHelloBy();

    assertThat(result.size()).isEqualTo(2);
  }


  @Test
  void queryMethodTest2() {
    Member member1 = Member.builder()
            .username("memberA")
            .age(10)
            .build();

    Member member2 = Member.builder()
            .username("memberB")
            .age(20)
            .build();

    Member member3 = Member.builder()
            .username("memberC")
            .age(10)
            .build();

    Member member4 = Member.builder()
            .username("memberD")
            .age(20)
            .build();

    memberRepository.save(member1);
    memberRepository.save(member2);
    memberRepository.save(member3);
    memberRepository.save(member4);

    List<Member> result = memberRepository.findTop2By();
    /**
     * select
     *         m1_0.member_id,
     *         m1_0.age,
     *         m1_0.team_id,
     *         m1_0.username
     *     from
     *         member m1_0
     *     limit
     *         ?
     */
    assertThat(result.size()).isEqualTo(2);
  }

  @Test
  void findByNamedQuery() {
    Member member1 = Member.builder()
            .username("memberA")
            .age(10)
            .build();

    Member member2 = Member.builder()
            .username("memberB")
            .age(20)
            .build();

    memberRepository.save(member1);
    memberRepository.save(member2);

    List<Member> results = memberRepository.findByUsername("memberB");

    assertThat(results.get(0).getUsername()).isEqualTo("memberB");
    assertThat(results.get(0).getAge()).isEqualTo(20);
    assertThat(results.get(0)).isEqualTo(member2);

  }
}