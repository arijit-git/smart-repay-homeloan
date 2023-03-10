package com.ray.finance.smartrepayloan.model.loanplan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "loan_plan")
public class LoanPlan {
    @Id
    private Long id;
    @Column("amount")
    private double amount;
    @Column("tenure")
    private int tenure;
    @Column("interest_rate")
    private double interestRate;
    @Column("no_yearly_pre_payment")
    private int noOfYearlyEmiPrepayment;
    @Column("yearly_emi_increase_percentage")
    private double yearlyEmiIncreasePercentage;
}
