package fr.boilerplate.spring.payload.auth;

public class JwtAuthenticationResponse {
  private String accessToken;
  private String tokenType = "Bearer";

  public JwtAuthenticationResponse(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getAccessToken() {
    return this.accessToken;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  public String getTokenType() {
    return this.tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }
}
