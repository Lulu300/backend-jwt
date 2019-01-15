package fr.boilerplate.spring.controller;

import fr.boilerplate.spring.exception.AppException;
import fr.boilerplate.spring.model.Authority;
import fr.boilerplate.spring.model.AuthorityName;
import fr.boilerplate.spring.model.User;
import fr.boilerplate.spring.payload.ApiResponse;
import fr.boilerplate.spring.payload.auth.AuthRequest;
import fr.boilerplate.spring.payload.auth.JwtAuthenticationResponse;
import fr.boilerplate.spring.payload.signup.SignUpRequest;
import fr.boilerplate.spring.repository.AuthorityRepository;
import fr.boilerplate.spring.repository.UserRepository;
import fr.boilerplate.spring.security.JwtTokenProvider;
import java.net.URI;
import java.util.Collections;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired AuthenticationManager authenticationManager;

  @Autowired UserRepository userRepository;

  @Autowired AuthorityRepository authorityRepository;

  @Autowired PasswordEncoder passwordEncoder;

  @Autowired JwtTokenProvider tokenProvider;

  @PostMapping("")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequest authRequest) {

    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authRequest.getUsernameOrEmail(), authRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = tokenProvider.generateToken(authentication);
    return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return new ResponseEntity<ApiResponse>(
          new ApiResponse(false, "Username is already taken!"), HttpStatus.BAD_REQUEST);
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return new ResponseEntity<ApiResponse>(
          new ApiResponse(false, "Email Address already in use!"), HttpStatus.BAD_REQUEST);
    }

    User user =
        new User(
            signUpRequest.getFirstname(),
            signUpRequest.getLastname(),
            signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            signUpRequest.getPassword());

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    Authority userAuthority =
        authorityRepository
            .findByName(AuthorityName.ROLE_USER)
            .orElseThrow(() -> new AppException("User Authority not set."));

    user.setAuthorities(Collections.singleton(userAuthority));

    User result = userRepository.save(user);

    URI location =
        ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/user/{id}")
            .buildAndExpand(result.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "User registered successfully"));
  }
}
