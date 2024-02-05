package com.energizor.restapi.calendar.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CalendarParticipantPK implements Serializable {

    @Column(name="user_code")
    private int userCode;

    @Column(name="cal_no")
    private int calNo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalendarParticipantPK calendarParticipantPK = (CalendarParticipantPK) o;
        return userCode == calendarParticipantPK.userCode && calNo == calendarParticipantPK.calNo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userCode, calNo);
    }


}