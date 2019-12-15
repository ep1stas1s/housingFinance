package com.kakaopay.housingfinance.credit.domain.repository;

import com.kakaopay.housingfinance.credit.domain.MonthlyCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyCreditRepository extends JpaRepository<MonthlyCredit, Long> {
}
