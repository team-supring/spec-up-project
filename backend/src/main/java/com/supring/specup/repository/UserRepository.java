package com.supring.specup.repository;

import com.supring.specup.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByMemberId(String memberId);

    Optional<User> findByMemberId(String memberId);
}
