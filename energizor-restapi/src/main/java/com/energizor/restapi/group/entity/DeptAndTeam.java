package com.energizor.restapi.group.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import java.util.List;


@Entity
@Table(name = "department")
@AllArgsConstructor
@Getter
@ToString
public class DeptAndTeam {

        /* 부서기준으로 하나의 부서안에 속해있는 팀 전부 조회 ( 부서 + 팀s ) */

        @Id
        @Column(name = "dept_code", nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long deptCode;

        @Column(name = "dept_name", length = 20, nullable = false)
        private String deptName;

        @OneToMany
        @JoinColumn(name="dept_code")
        private List<TeamGroup> teamList;

        public DeptAndTeam() {}

        public DeptAndTeam deptCode(Long deptCode) {
                this.deptCode = deptCode;
                return this;
        }

        public DeptAndTeam deptName(String deptName) {
                this.deptName = deptName;
                return this;
        }

        public DeptAndTeam teamList(List<TeamGroup> teamList) {
                this.teamList = teamList;
                return this;
        }

        public DeptAndTeam build() {
                return new DeptAndTeam(deptCode, deptName, teamList);
        }


}