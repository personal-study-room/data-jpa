package lecture.datajpa.repository;

import lecture.datajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
  List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

  // 가운데는 아무렇게나, 그리고 By뒤에 아무것도 넣지 않으면 전체 조회
  List<Member> findHelloBy();

  List<Member> findTop2By();
}
