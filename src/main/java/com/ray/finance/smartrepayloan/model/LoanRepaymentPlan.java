package com.ray.finance.smartrepayloan.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanRepaymentPlan {
    @JsonAlias("principal_amount")
    private double principal_amount;
    @JsonAlias("total_interest_paid")
    private String total_interest_paid;
    @JsonAlias("emi_amount")
    private double emi_amount;
    @JsonAlias("loan_closure_months_count")
    private int loan_closure_months_count;
}
