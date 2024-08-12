package lecture.datajpa.repository;

import lecture.datajpa.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
  List<Member> findByUsernameAndAgeGreaterThan(String username, int age);

  // 가운데는 아무렇게나, 그리고 By뒤에 아무것도 넣지 않으면 전체 조회
  List<Member> findHelloBy();

  List<Member> findTop2By();
  @Query(name = "Member.findByUsername")
  List<Member> findByUsername(@Param("username") String username);

  @Query("select m from Member m where m.username = :username and m.age = :age")
  List<Member> findUser(@Param("username") String username, @Param("age") int age);
}
