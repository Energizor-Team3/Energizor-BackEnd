package com.energizor.restapi.users.entity;

import lombok.*;

import java.io.Serializable;

/* 복합키 타입을 정의할 때는 Serializable을 반드시 구현 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode      // 필수?
public class UserRolePK implements Serializable {

    private int userCode;
    private int authCode;
}
