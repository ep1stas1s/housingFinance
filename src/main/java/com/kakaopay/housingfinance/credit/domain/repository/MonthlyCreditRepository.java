package com.kakaopay.housingfinance.credit.domain.repository;

import com.kakaopay.housingfinance.credit.domain.MonthlyCredit;
import com.kakaopay.housingfinance.credit.domain.YearlyAverageCredit;
import com.kakaopay.housingfinance.credit.domain.YearlyCredit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonthlyCreditRepository extends JpaRepository<MonthlyCredit, Long> {
    String INSTITUTE_ID = "instituteId";

    @Query("SELECT mc.year AS year, mc.institute AS institute, SUM(mc.supportAmount) AS detailAmount " +
            "FROM MonthlyCredit mc " +
            "GROUP BY mc.year, mc.institute")
    List<YearlyCredit> findAllYearlyCreditInfo();

    @Query("SELECT mc.year AS year, mc.institute.name AS instituteName, AVG(mc.supportAmount) AS averageAmount " +
            "FROM MonthlyCredit mc " +
            "GROUP BY mc.year, mc.institute.id " +
            "HAVING mc.institute.id = :instituteId " +
            "AND mc.year < 2017 " +
            "ORDER BY averageAmount")
    List<YearlyAverageCredit> findAllYearlyCredit(@Param(INSTITUTE_ID) long instituteId);
}
