package fr.boilerplate.spring.payload.user;

public class UserIdentityAvailability {
  private Boolean available;
  private String field;

  public UserIdentityAvailability(Boolean available, String field) {
    this.available = available;
    this.field = field;
  }

  public Boolean isAvailable() {
    return this.available;
  }

  public Boolean getAvailable() {
    return this.available;
  }

  public void setAvailable(Boolean available) {
    this.available = available;
  }

  public String getField() {
    return this.field;
  }

  public void setField(String field) {
    this.field = field;
  }
}
