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
class MemberJpaRepositoryTest {


  private final MemberJpaRepository memberJpaRepository;

  @Test
  void testMember() {
    Member member = Member.builder()
            .username("memberA")
            .build();

    Member savedMember = memberJpaRepository.save(member);

    Member findMember = memberJpaRepository.find(savedMember.getId());

    // 동일 트랜잭션 내에서는 같은 entity를 보장
    assertThat(findMember).isEqualTo(member);
    assertThat(findMember).isEqualTo(savedMember);

    assertThat(findMember.getId()).isEqualTo(member.getId());
    assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
  }
}
