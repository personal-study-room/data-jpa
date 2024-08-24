package lecture.datajpa.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberDTO {

  private Long id;
  private String username;
  private String teamName;
}
