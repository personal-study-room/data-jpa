package lecture.datajpa.repository;

import lecture.datajpa.entity.Member;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

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
}