package com.vvopaa.ega.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vvopaa.ega.common.model.AuditEntity;
import com.vvopaa.ega.user.embed.UserInfo;
import com.vvopaa.ega.user.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class User extends AuditEntity implements UserDetails {
  private String username;
  private String password;
  private boolean enabled;
  private Set<RoleEnum> roles;
  private UserInfo userInfo;

  User(String username, String password, Set<RoleEnum> roles, UserInfo userInfo) {
    this.username = username;
    this.password = password;
    this.roles = roles;
    this.userInfo = userInfo;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream().map(authority -> new SimpleGrantedAuthority(authority.name())).collect(Collectors.toList());
  }

  @JsonIgnore
  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }
}
