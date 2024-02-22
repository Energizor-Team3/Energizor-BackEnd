package com.energizor.restapi.board.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.energizor.restapi.users.entity.User;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude="user")
public class Board extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_code")
    private int boardCode;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

    @Column(name="view_count")
    private int viewCount;

    @Column(name="delete_date")
    private LocalDateTime deleteDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_code")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board_type_code")
    private BoardType boardType;

    @OneToMany(mappedBy = "board")
    private List<InterestBoard> interestBoards=new ArrayList<>();

    @Column(name="temporary_opt")
    private Boolean temporaryOpt;

    public Board boardCode(int boardCode) {
        this.boardCode=boardCode;
        return this;
    }

    public Board title(String title) {
        this.title=title;
        return this;
    }

    public Board content(String content) {
        this.content=content;
        return this;
    }

    public Board viewCount(int viewCount) {
        this.viewCount=viewCount;
        return this;
    }

    public Board user(User user) {
        this.user=user;
        return this;
    }

    public Board boardType(BoardType boardType){
        this.boardType=boardType;
        return this;
    }

    public Board build() {
        return new Board(boardCode,title,content,viewCount,deleteDate,user,boardType,interestBoards,temporaryOpt);
    }

    public void changeBoardDeletedAt(LocalDateTime deleteDate) {this.deleteDate=deleteDate;}

}
