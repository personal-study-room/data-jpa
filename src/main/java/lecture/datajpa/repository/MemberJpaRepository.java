package lecture.datajpa.repository;


import jakarta.persistence.EntityManager;
import lecture.datajpa.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberJpaRepository {

  private final EntityManager em;

  public Member save(Member member) {
    em.persist(member);
    return member;
  }

  public void delete(Member member) {
    em.remove(member);
  }

  public Member find(Long id) {
    return em.find(Member.class, id);
  }

  public List<Member> findAll() {
    // JPQL
    return em.createQuery("select m from Member m", Member.class)
            .getResultList();
  }

  public Optional<Member> findById(Long id) {
    Member member = em.find(Member.class, id);
    return Optional.ofNullable(member);
  }

  public long count() {
    return em.createQuery("select count(m) from Member m", Long.class)
            .getSingleResult();
  }

  public List<Member> findByUsernameAndAgeGreaterThan(String username, int age) {
    return em.createQuery("select m from Member m where m.username = :username and m.age >= :age", Member.class)
            .setParameter("username", username)
            .setParameter("age", age)
            .getResultList();
  }

  public List<Member> findByUsername(String username) {
    return em.createNamedQuery("Member.findByUsername", Member.class)
            .setParameter("username", username)
            .getResultList();
  }


}
