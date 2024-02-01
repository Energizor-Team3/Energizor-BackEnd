package com.energizor.restapi.calendar.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="CAL_PARTICIPANT")
@IdClass(CalendarParticipantPK.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CalendarParticipant {

//
//    @Id
//    @ManyToOne
//    @JoinColumn(name = "user_code")
//    private User  user;

    @Id
    @ManyToOne
    @JoinColumn(name = "cal_no")
    private Calendar calendar;

}
