package com.energizor.restapi.approval.entity;

import com.energizor.restapi.approval.dto.DayOffDTO;
import com.energizor.restapi.approval.dto.DocumentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;
@Entity
@Table(name = "dayoff_apply")
@AllArgsConstructor
@Getter
@ToString
public class DayOffApply {
    @Id
    @Column(name = "off_apply_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int offApplyCode;
    @Column(name = "off_apply_date")
    private Date offApplyDate;
    @Column(name = "off_start")
    private Date offStart;
    @Column(name = "off_end")
    private Date offEnd;
    @Column(name = "off_day")
    private int offDay;
    @Column(name = "off_reason")
    private String offReason;
    @Column(name = "off_state")
    private String offState;
    @JoinColumn(name = "off_code")
    @OneToOne
    private DayOff dayOff;
    @JoinColumn(name = "document_code")
    @OneToOne
    private Document document;


    public DayOffApply() {
    }

    public DayOffApply offApplyCode(int offApplyCode) {
        this.offApplyCode = offApplyCode;
        return this;
    }

    public DayOffApply offApplyDate(Date offApplyDate) {
        this.offApplyDate = offApplyDate;
        return this;
    }
    public DayOffApply offStart(Date offStart) {
        this.offStart = offStart;
        return this;
    }
    public DayOffApply offEnd(Date offEnd) {
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
    public DayOffApply dayOff(DayOff dayOff) {
        this.dayOff = dayOff;
        return this;
    }
    public DayOffApply document(Document document) {
        this.document = document;
        return this;
    }



    public DayOffApply build() {
        return new DayOffApply(offApplyCode, offApplyDate, offStart, offEnd, offDay, offReason, offState, dayOff, document);
    }
}
