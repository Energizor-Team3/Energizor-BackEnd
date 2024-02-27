package com.energizor.restapi.group.entity;
import com.energizor.restapi.note.dto.SendNoteDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Entity(name="groupUsers")
@Table(name = "users")
@AllArgsConstructor
@Getter
@ToString
public class UsersGroup {


    /* 사원기준으로 사원개인의 정보와 속해있는 팀 조회 ( 사원 + 팀 ) */

    @Id
    @Column(name = "user_code", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userCode;

    @Column(name = "user_name")
    private String userName;

    public UsersGroup() {}

    public UsersGroup userCode(int userCode) {
        this.userCode = userCode;
        return this;
    }

    public UsersGroup userName(String userName) {
        this.userName = userName;
        return this;
    }

    public UsersGroup build() {
        return new UsersGroup(userCode, userName);
    }


}
