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

  @Test
  void basicCRUD() {
    Member member1 = Member.builder()
            .username("member1")
            .build();

    Member member2 = Member.builder()
            .username("member2")
            .build();

    memberJpaRepository.save(member1);
    memberJpaRepository.save(member2);

    Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
    Member findMember2 = memberJpaRepository.findById(member2.getId()).get();
    assertThat(findMember1).isEqualTo(member1);
    assertThat(findMember2).isEqualTo(member2);

    // 리스트 검증
    List<Member> result = memberJpaRepository.findAll();
    assertThat(result.size()).isEqualTo(2);

    // 카운트 검증
    long count = memberJpaRepository.count();
    assertThat(count).isEqualTo(2);

    //삭제
    memberJpaRepository.delete(member1);
    memberJpaRepository.delete(member2);
    long deletedCount = memberJpaRepository.count();
    assertThat(deletedCount).isEqualTo(0);
  }
}
