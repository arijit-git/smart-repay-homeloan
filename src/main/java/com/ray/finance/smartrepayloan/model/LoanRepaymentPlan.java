package com.ray.finance.smartrepayloan.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanRepaymentPlan {
    @Id
    private int smartId;
    private double principal;
    private String interestPaid;
    private double emi;
    private int emiCount;
}
