package com.energizor.restapi.users.dto;

import com.energizor.restapi.group.dto.TeamGroupDTO;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO implements UserDetails {

    private int userCode;
    private String userId;
    private String userName;
    private String userPw;
    private String userRank;
    private String email;
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date entDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date resignDate;

    private String userStatus;
    private List<UserRoleDTO> userRole;
    private Collection<GrantedAuthority> authorities;

    private TeamDTO team;
    private DayOffDTO dayoff;

    private String profilePath;

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if(userRole != null) {
            userRole.forEach(role -> {
                authorities.add(() -> role.getAuthority().getAuthName());
            });
            return authorities;
        }
        return new ArrayList<>();
    }


    @Override
    public String getPassword() {
        return this.userPw;
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
