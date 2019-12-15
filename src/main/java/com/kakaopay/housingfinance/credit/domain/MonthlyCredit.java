package com.kakaopay.housingfinance.credit.domain;

import com.kakaopay.housingfinance.institute.domain.Institute;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Getter
@Entity
@ToString
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor
@Table(indexes = {@Index(columnList = "YEAR, INSTITUTE_ID")})
public class MonthlyCredit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "YEAR", nullable = false)
    private int year;

    @Column(name = "MONTH", nullable = false)
    private int month;

    @Column(name = "SUPPORT_AMOUNT", nullable = false)
    private int supportAmount;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "INSTITUTE_ID",
            foreignKey = @ForeignKey(name = "FK_MONTHLY_CREDIT_TO_INSTITUTE"))
    private Institute institute;

    @Builder
    public MonthlyCredit(int year, int month, int supportAmount, Institute institute) {
        this.year = year;
        this.month = month;
        this.supportAmount = supportAmount;
        this.institute = institute;
    }
}
