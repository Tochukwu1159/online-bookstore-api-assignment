
package com.wizer.wizerassessment.repositories;

import com.wizer.wizerassessment.models.User;
import com.wizer.wizerassessment.utils.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByEmail(String email);

    Optional<User> findByEmail(String username);

    User findByEmailAndRoles(String email, Roles role);
}
