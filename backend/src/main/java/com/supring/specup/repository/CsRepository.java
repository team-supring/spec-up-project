package com.supring.specup.repository;

import com.supring.specup.domain.Cs;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CsRepository extends JpaRepository<Cs, Long> {
    List<Cs> findByWriter(String writer);
}