package com.kakaopay.housingfinance.credit.domain.repository;

import com.kakaopay.housingfinance.credit.domain.MonthlyCredit;
import com.kakaopay.housingfinance.credit.domain.YearlyCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyCreditRepository extends JpaRepository<MonthlyCredit, Long> {

    @Query("SELECT mc.year AS year, mc.institute AS institute, SUM(mc.supportAmount) AS detailAmount " +
            "FROM MonthlyCredit mc " +
            "GROUP BY mc.year, mc.institute")
    List<YearlyCredit> findAllYearlyCreditInfo();
}
