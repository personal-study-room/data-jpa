package lecture.datajpa.entity;


import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED) // jpa가 프록시 기술을 쓸 때, protected 정도로 열어 놔야 한다
@ToString(of = {"id", "username", "age"})
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "member_id")
  private Long id;
  private String username;
  private int age;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "team_id")
  private Team team;

  public void changeTeam(Team team) {
    this.team = team;
    team.getMembers().add(this);
  }
}
