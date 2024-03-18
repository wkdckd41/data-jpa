package study.datajpa.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "username", "age"})
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY) // 지연로딩
    @JoinColumn(name = "team_id") // 외래키 매핑
    private Team team;

    public Member(String username) {
        this.username = username;
    }

    public Member(String member1, int i, Team team) {
        this.username = member1;
        this.age = i;
        if (team != null) {
            changeTeam(team);
        }
    }

    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
