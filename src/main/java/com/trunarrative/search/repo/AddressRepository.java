package com.trunarrative.search.repo;

import com.trunarrative.search.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
