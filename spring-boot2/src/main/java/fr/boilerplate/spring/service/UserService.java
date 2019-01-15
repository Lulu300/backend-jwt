package fr.boilerplate.spring.service;

import fr.boilerplate.spring.model.User;
import fr.boilerplate.spring.payload.PagedResponse;
import fr.boilerplate.spring.payload.user.UserSummary;
import fr.boilerplate.spring.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired private UserRepository userRepository;

  private static final Logger logger = LoggerFactory.getLogger(UserService.class);

  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  public Optional<User> findByUsernameOrEmail(String username, String email) {
    return userRepository.findByUsernameOrEmail(username, email);
  }

  public Optional<User> findById(Long id) {
    return userRepository.findById(id);
  }

  public List<User> findByIdIn(List<Long> userIds) {
    return userRepository.findByIdIn(userIds);
  }

  public Optional<User> findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public Boolean existsByUsername(String username) {
    return userRepository.existsByEmail(username);
  }

  public Boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  public PagedResponse<UserSummary> getUsers(int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "username");
    Page<User> users = userRepository.findAll(pageable);
    List<UserSummary> usersSummary =
        users
            .map(
                user -> {
                  return UserSummary.create(user);
                })
            .getContent();
    return new PagedResponse<UserSummary>(
        usersSummary,
        users.getNumber(),
        users.getSize(),
        users.getTotalElements(),
        users.getTotalPages(),
        users.isLast());
  }
}
