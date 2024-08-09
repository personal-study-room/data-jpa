package lecture.datajpa.entity;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@Transactional
@RequiredArgsConstructor
class MemberTest {

  private final EntityManager em;

  @Test
  void testEntity() {
    Team teamA = Team.builder()
            .name("teamA")
            .build();

    Team teamB = Team.builder()
            .name("teamB")
            .build();

    em.persist(teamA);
    em.persist(teamB);
    Member member1 = Member.builder()
            .age(10)
            .username("member1")
            .team(teamA)
            .build();

    Member member2 = Member.builder()
            .age(20)
            .username("member2")
            .team(teamA)
            .build();
    Member member3 = Member.builder()
            .age(30)
            .username("member3")
            .team(teamB)
            .build();
    Member member4 = Member.builder()
            .age(40)
            .username("member4")
            .team(teamB)
            .build();

    em.persist(member1);
    em.persist(member2);
    em.persist(member3);
    em.persist(member4);

    em.flush();
    em.clear();
    System.out.println("===================================================================");
    List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();

    for (Member member : members) {
      System.out.println("member = " + member);
      System.out.println("member.getTeam() = " + member.getTeam());
    }

  }

}