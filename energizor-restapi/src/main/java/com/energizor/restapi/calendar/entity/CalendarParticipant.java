package com.energizor.restapi.calendar.entity;

import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="CAL_PARTICIPANT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CalendarParticipant {

    @EmbeddedId
    private CalendarParticipantPK calParticipant;

}