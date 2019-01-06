package fr.boilerplate.spring.controller;

import fr.boilerplate.spring.exception.ResourceNotFoundException;
import fr.boilerplate.spring.model.User;
import fr.boilerplate.spring.payload.user.UserIdentityAvailability;
import fr.boilerplate.spring.payload.user.UserSummary;
import fr.boilerplate.spring.repository.UserRepository;
import fr.boilerplate.spring.security.CurrentUser;
import fr.boilerplate.spring.security.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

  @Autowired private UserRepository userRepository;

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  @GetMapping("/me")
  @PreAuthorize("hasRole('USER')")
  public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
    UserSummary userSummary = UserSummary.create(currentUser);
    return userSummary;
  }

  @GetMapping("/checkUsernameAvailability")
  public UserIdentityAvailability checkUsernameAvailability(
      @RequestParam(value = "username") String username) {
    Boolean isAvailable = !userRepository.existsByUsername(username);
    return new UserIdentityAvailability(isAvailable, "username");
  }

  @GetMapping("/checkEmailAvailability")
  public UserIdentityAvailability checkEmailAvailability(
      @RequestParam(value = "email") String email) {
    Boolean isAvailable = !userRepository.existsByEmail(email);
    return new UserIdentityAvailability(isAvailable, "email");
  }

  @GetMapping("/{id}")
  public UserSummary getUserInfos(@PathVariable(value = "id") Long id) {
    User user =
        userRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

    UserSummary userSummary = UserSummary.create(user);

    return userSummary;
  }
}
