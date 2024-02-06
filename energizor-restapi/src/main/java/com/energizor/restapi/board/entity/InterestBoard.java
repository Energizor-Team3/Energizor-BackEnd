package com.energizor.restapi.board.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @Column(name="delete_date")
    private LocalDateTime deleteDate;

    @ManyToOne
    @JoinColumn(name="board_code")
    private Board board;

    @ManyToOne
    @JoinColumn(name="user_code")
    private User user;

    @ManyToOne
    @JoinColumn(name="user_code")
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

    public InterestBoard deleteDate(LocalDateTime deleteDate) {
        this.deleteDate=deleteDate;
        return this;
    }

    public void changeReplyDeleteDate(LocalDateTime deleteDate) {this.deleteDate=deleteDate;}

    @Override
    public String toString() {
        return "InterestBoard interestCode: " + interestCode;
    }

}
