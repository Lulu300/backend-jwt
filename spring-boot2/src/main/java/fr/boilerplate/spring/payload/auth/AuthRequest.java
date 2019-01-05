package fr.boilerplate.spring.payload.auth;

import javax.validation.constraints.NotBlank;

public class AuthRequest {
  @NotBlank private String usernameOrEmail;

  @NotBlank private String password;

  public String getUsernameOrEmail() {
    return this.usernameOrEmail;
  }

  public void setUsernameOrEmail(String usernameOrEmail) {
    this.usernameOrEmail = usernameOrEmail;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
