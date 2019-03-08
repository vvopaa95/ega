package com.vvopaa.ega.security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vvopaa.ega.common.model.AuditEntity;
import com.vvopaa.ega.security.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecurityUser extends AuditEntity implements UserDetails {
  private String username;
  private String password;
  private boolean enabled;
  @Getter
  private Set<RoleEnum> roles;

  public SecurityUser(String username, String password, Set<RoleEnum> roles) {
    this.username = username;
    this.password = password;
    this.roles = roles;
    this.enabled = true;
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
