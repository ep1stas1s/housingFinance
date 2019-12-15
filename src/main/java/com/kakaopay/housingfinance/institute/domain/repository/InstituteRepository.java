package com.kakaopay.housingfinance.institute.domain.repository;

import com.kakaopay.housingfinance.institute.domain.Institute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstituteRepository extends JpaRepository<Institute, Long> {
}
