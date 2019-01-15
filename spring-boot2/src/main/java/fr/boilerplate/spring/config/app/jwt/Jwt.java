package fr.boilerplate.spring.config.app.jwt;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

public class Jwt {

  @NotNull @NotBlank @Getter @Setter private String secret;

  @NotNull @NotBlank @Getter @Setter private int expirationInMs;
}
