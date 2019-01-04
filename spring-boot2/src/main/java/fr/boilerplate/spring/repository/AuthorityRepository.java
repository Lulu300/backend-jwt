package fr.boilerplate.spring.repository;

import fr.boilerplate.spring.model.Authority;
import fr.boilerplate.spring.model.AuthorityName;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
  Optional<Authority> findByName(AuthorityName authorityName);
}
