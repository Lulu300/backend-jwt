package fr.boilerplate.spring.config.app;

import fr.boilerplate.spring.config.app.jwt.Jwt;
import javax.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app")
@Validated
public class AppProperties {

  @Valid @Getter @Setter private final Jwt jwt = new Jwt();
}
