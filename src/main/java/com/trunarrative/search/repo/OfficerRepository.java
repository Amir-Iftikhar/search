package com.trunarrative.search.repo;

import com.trunarrative.search.entity.Officer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficerRepository extends JpaRepository<Officer, String> {
}
