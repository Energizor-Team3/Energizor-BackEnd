package com.energizor.restapi.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import com.energizor.restapi.users.entity.User;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="interest_board")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class InterestBoard extends BaseEntity{

    @Id
    @Column(name="interest_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int interestCode;

    @ManyToOne
    @JoinColumn(name="board_code")
    private Board board;

    @ManyToOne
    @JoinColumn(name="user_code", insertable=false, updatable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name="owner_code", insertable=false, updatable=false)
    private User owner;


    public InterestBoard board(Board board) {
        this.board=board;
        return this;
    }

    public InterestBoard user(User user) {
        this.user=user;
        return this;
    }

    public InterestBoard owner(User owner) {
        this.owner=owner;
        return this;
    }




    @Override
    public String toString() {
        return "InterestBoard interestCode: " + interestCode;
    }

}
