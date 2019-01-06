package fr.boilerplate.spring.payload.user;

import fr.boilerplate.spring.model.User;
import fr.boilerplate.spring.security.UserPrincipal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class UserSummary {
  private Long id;
  private String firstname;
  private String lastname;
  private String username;
  private String email;
  private Collection<? extends GrantedAuthority> authorities;

  public UserSummary(
      Long id,
      String firstname,
      String lastname,
      String username,
      String email,
      Collection<? extends GrantedAuthority> authorities) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.email = email;
    this.authorities = authorities;
  }

  public static UserSummary create(User user) {
    List<GrantedAuthority> authorities =
        user.getAuthorities()
            .stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
            .collect(Collectors.toList());

    return new UserSummary(
        user.getId(),
        user.getFirstname(),
        user.getLastname(),
        user.getUsername(),
        user.getEmail(),
        authorities);
  }

  public static UserSummary create(UserPrincipal user) {
    return new UserSummary(
        user.getId(),
        user.getFirstname(),
        user.getLastname(),
        user.getUsername(),
        user.getEmail(),
        user.getAuthorities());
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstname() {
    return this.firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return this.lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
    this.authorities = authorities;
  }
}
