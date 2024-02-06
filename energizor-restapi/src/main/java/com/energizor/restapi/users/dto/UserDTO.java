package com.energizor.restapi.users.dto;

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
    private Date entDate;
    private Date resignDate;
    private String userStatus;
    private List<UserRoleDTO> userRole;
    private Collection<GrantedAuthority> authorities;

    private int teamCode;
    private DayOffDTO dayoff;

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
