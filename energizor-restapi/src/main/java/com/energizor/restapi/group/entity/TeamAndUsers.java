package com.energizor.restapi.group.entity;
import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import java.util.List;

@Entity
@Table(name = "team")
@AllArgsConstructor
@Getter
@ToString
public class TeamAndUsers {


    @Id
    @Column(name = "team_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamCode;

    @Column(name = "team_name", length = 20, nullable = false)
    private String teamName;

    @OneToMany
    @JoinColumn(name = "team_code")
    private List<Users> userlist;

    public TeamAndUsers() {}

    public TeamAndUsers teamCode(Long teamCode) {
        this.teamCode = teamCode;
        return this;
    }

    public TeamAndUsers teamName(String teamName) {
        this.teamName = teamName;
        return this;
    }

    public TeamAndUsers userlist(List<Users> userlist) {
        this.userlist = userlist;
        return this;
    }
    public TeamAndUsers build() {
        return new TeamAndUsers(teamCode, teamName, userlist);
    }

}
