package fr.boilerplate.spring.controller;

import fr.boilerplate.spring.config.app.AppConstants;
import fr.boilerplate.spring.config.app.AppProperties;
import fr.boilerplate.spring.exception.ResourceNotFoundException;
import fr.boilerplate.spring.model.User;
import fr.boilerplate.spring.payload.PagedResponse;
import fr.boilerplate.spring.payload.user.UserIdentityAvailability;
import fr.boilerplate.spring.payload.user.UserSummary;
import fr.boilerplate.spring.security.CurrentUser;
import fr.boilerplate.spring.security.UserPrincipal;
import fr.boilerplate.spring.service.UserService;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

  @Autowired private UserService userService;

  private final AppProperties appProperties;

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);

  public UserController(@Autowired AppProperties appProperties) {
    this.appProperties = appProperties;
  }

  @GetMapping("/me")
  @PreAuthorize("hasRole('USER')")
  public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
    UserSummary userSummary = UserSummary.create(currentUser);
    return userSummary;
  }

  @GetMapping("/checkUsernameAvailability")
  public UserIdentityAvailability checkUsernameAvailability(
      @RequestParam(value = "username") String username) {
    Boolean isAvailable = !userService.existsByUsername(username);
    return new UserIdentityAvailability(isAvailable, "username");
  }

  @GetMapping("/checkEmailAvailability")
  public UserIdentityAvailability checkEmailAvailability(
      @RequestParam(value = "email") String email) {
    Boolean isAvailable = !userService.existsByEmail(email);
    return new UserIdentityAvailability(isAvailable, "email");
  }

  @GetMapping("/{id}")
  public UserSummary getUserInfos(@PathVariable(value = "id") Long id) {
    User user =
        userService.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

    UserSummary userSummary = UserSummary.create(user);

    return userSummary;
  }

  @GetMapping("")
  public PagedResponse<UserSummary> getUsers(
      @CurrentUser UserPrincipal currentUser,
      @RequestParam(value = "page", defaultValue = AppConstants.page_number_default) @Min(0)
          int page,
      @RequestParam(value = "size", defaultValue = AppConstants.page_size_default)
          @Min(AppConstants.page_size_min)
          @Max(AppConstants.page_size_max)
          int size) {
    return userService.getUsers(page, size);
  }
}
