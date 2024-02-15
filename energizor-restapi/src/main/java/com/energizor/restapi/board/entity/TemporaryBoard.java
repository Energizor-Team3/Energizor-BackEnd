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
@Setter
@ToString(exclude="user")
public class TemporaryBoard extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="temporary_code")
    private int temporaryCode;

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

    public TemporaryBoard setTemporaryCode(int temporaryCode) {
        this.temporaryCode=temporaryCode;
        return this;
    }

    public TemporaryBoard setTitle(String title) {
        this.title=title;
        return this;
    }

    public TemporaryBoard setContent(String content) {
        this.content=content;
        return this;
    }

    public TemporaryBoard setViewCount(int viewCount) {
        this.viewCount=viewCount;
        return this;
    }

    public TemporaryBoard setUser(User user) {
        this.user=user;
        return this;
    }



    public TemporaryBoard build() {
        return new TemporaryBoard(temporaryCode,title,content,viewCount,deleteDate,user,boardType);
    }

    public void changeBoardDeletedAt(LocalDateTime deleteDate) {this.deleteDate=deleteDate;}

}
