package com.energizor.restapi.group.entity;
import com.energizor.restapi.users.entity.Team;
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


        @Id
        @Column(name = "dept_code", nullable = false)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int deptCode;

        @Column(name = "dept_name", length = 20, nullable = false)
        private String deptName;

        @OneToMany
        @JoinColumn(name="dept_code")
        private List<Team> teamList;

        public DeptAndTeam() {}

        public DeptAndTeam deptCode(int deptCode) {
                this.deptCode = deptCode;
                return this;
        }

        public DeptAndTeam deptName(String deptName) {
                this.deptName = deptName;
                return this;
        }

        public DeptAndTeam teamList(List<Team> teamList) {
                this.teamList = teamList;
                return this;
        }

        public DeptAndTeam build() {
                return new DeptAndTeam(deptCode, deptName, teamList);
        }


}