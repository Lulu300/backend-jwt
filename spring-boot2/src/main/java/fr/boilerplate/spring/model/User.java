package fr.boilerplate.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "app_user")
public class User extends DateAudit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotBlank
  @NotNull
  @Size(max = 50)
  @Column(name = "firstname", length = 50)
  private String firstname;

  @NotBlank
  @NotNull
  @Size(max = 50)
  @Column(name = "lastname", length = 50)
  private String lastname;

  @NotBlank
  @NotNull
  @Size(max = 40)
  @Column(name = "username", length = 40, unique = true)
  private String username;

  @NaturalId
  @NotBlank
  @NotNull
  @Size(max = 50)
  @Email
  @Column(name = "email", length = 50, unique = true)
  private String email;

  @NotBlank
  @NotNull
  @Size(min = 4, max = 100)
  @Column(name = "password", length = 100)
  private String password;

  @NotBlank
  @NotNull
  @Column(name = "enabled")
  private Boolean enabled;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_authorities",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
  @JsonIgnoreProperties("users")
  private Set<Authority> authorities;

  public User() {}

  public User(
      Long id,
      String firstname,
      String lastname,
      String username,
      String email,
      String password,
      Boolean enabled,
      Set<Authority> authorities) {
    this.id = id;
    this.firstname = firstname;
    this.lastname = lastname;
    this.username = username;
    this.email = email;
    this.password = password;
    this.enabled = enabled;
    this.authorities = authorities;
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

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Boolean isEnabled() {
    return this.enabled;
  }

  public Boolean getEnabled() {
    return this.enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Set<Authority> getAuthorities() {
    return this.authorities;
  }

  public void setAuthorities(Set<Authority> authorities) {
    this.authorities = authorities;
  }
}
