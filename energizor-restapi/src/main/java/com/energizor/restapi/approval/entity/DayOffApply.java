package com.energizor.restapi.approval.entity;

import com.energizor.restapi.users.entity.Dayoff;
import com.energizor.restapi.users.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Date;
@Entity
@Table(name = "dayoff_apply")
@AllArgsConstructor
@Getter

public class DayOffApply {
    @Id
    @Column(name = "off_apply_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int offApplyCode;
    @Column(name = "off_apply_date")
    private LocalDate offApplyDate;
    @Column(name = "off_start")
    private LocalDate offStart;
    @Column(name = "off_end")
    private LocalDate offEnd;
    @Column(name = "off_day")
    private int offDay;
    @Column(name = "off_reason")
    private String offReason;
    @Column(name = "off_state")
    private String offState;
    @JoinColumn(name = "off_code")
    @OneToOne
    private Dayoff dayoff;
    @JoinColumn(name = "document_code")
    @OneToOne
    private Document document;
    @JoinColumn(name = "user_code")
    @OneToOne
    private User user;
    @Column(name = "off_apply_title")
    private String offApplyTitle;


    public DayOffApply() {
    }

    @Override
    public String toString() {
        return "DayOffApply{" +
                "offApplyCode=" + offApplyCode +
                ", offApplyDate=" + offApplyDate +
                ", offStart=" + offStart +
                ", offEnd=" + offEnd +
                ", offDay=" + offDay +
                ", offReason='" + offReason + '\'' +
                ", offState='" + offState + '\'' +
                ", document=" + document +
                ", offApplyTitle='" + offApplyTitle + '\'' +
                '}';
    }

    public DayOffApply offApplyCode(int offApplyCode) {
        this.offApplyCode = offApplyCode;
        return this;
    }

    public DayOffApply offApplyDate(LocalDate offApplyDate) {
        this.offApplyDate = offApplyDate;
        return this;
    }
    public DayOffApply offStart(LocalDate offStart) {
        this.offStart = offStart;
        return this;
    }
    public DayOffApply offEnd(LocalDate offEnd) {
        this.offEnd = offEnd;
        return this;
    }
    public DayOffApply offDay(int offDay) {
        this.offDay = offDay;
        return this;
    }
    public DayOffApply offReason(String offReason) {
        this.offReason = offReason;
        return this;
    }
    public DayOffApply offState(String offState) {
        this.offState = offState;
        return this;
    }
    public DayOffApply dayoff(Dayoff dayoff) {
        this.dayoff = dayoff;
        return this;
    }
    public DayOffApply document(Document document) {
        this.document = document;
        return this;
    }
    public DayOffApply user(User user) {
        this.user = user;
        return this;
    }

    public DayOffApply offApplyTitle(String offApplyTitle) {
        this.offApplyTitle = offApplyTitle;
        return this;
    }



    public DayOffApply build() {
        return new DayOffApply(offApplyCode, offApplyDate, offStart, offEnd, offDay, offReason, offState, dayoff, document, user, offApplyTitle);
    }
}
