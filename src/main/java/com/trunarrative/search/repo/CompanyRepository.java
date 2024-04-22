package com.trunarrative.search.repo;

import com.trunarrative.search.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, String> {
}
