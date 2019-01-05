package fr.boilerplate.spring.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.boilerplate.spring.model.User;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {
  private Long id;

  private String firstname;

  private String lastname;

  private String username;

  @JsonIgnore private String email;

  @JsonIgnore private String password;

  private Collection<? extends GrantedAuthority> authorities;

  public UserPrincipal(
      Long id,
      String firstname,
      String lastname,
      String username,
      String email,
      String password,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
  }

  public static UserPrincipal create(User user) {
    List<GrantedAuthority> authorities =
        user.getAuthorities()
            .stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
            .collect(Collectors.toList());

    return new UserPrincipal(
        user.getId(),
        user.getFirstname(),
        user.getLastname(),
        user.getUsername(),
        user.getEmail(),
        user.getPassword(),
        authorities);
  }

  public Long getId() {
    return this.id;
  }

  public String getFirstname() {
    return this.firstname;
  }

  public String getLastname() {
    return this.lastname;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  public String getEmail() {
    return this.email;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
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

  @Override
  public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof UserPrincipal)) {
      return false;
    }
    UserPrincipal userPrincipal = (UserPrincipal) o;
    return Objects.equals(id, userPrincipal.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
