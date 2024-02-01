package com.energizor.restapi.calendar.entity;

import lombok.*;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CalendarParticipantPK implements Serializable {
    private String user_code;
    private int cal_no;


}
