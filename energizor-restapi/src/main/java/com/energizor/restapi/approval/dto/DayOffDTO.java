package com.energizor.restapi.approval.dto;

import com.energizor.restapi.users.dto.UserDTO;
import lombok.*;

import java.time.Year;

@AllArgsConstructor
@NoArgsConstructor

@Setter
@Getter
public class DayOffDTO {
    private int offCode;
    private Year offYear;
    private int offCount;
    private int offUsed;
    private UserDTO userDTO;

    @Override
    public String toString() {
        return "DayOffDTO{" +
                "offCode=" + offCode +
                ", offYear=" + offYear +
                ", offCount=" + offCount +
                ", offUsed=" + offUsed +
                '}';
    }
}
