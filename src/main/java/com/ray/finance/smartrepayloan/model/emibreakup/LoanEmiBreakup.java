package com.ray.finance.smartrepayloan.model.emibreakup;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "emi_breakup")
public class LoanEmiBreakup {
    @Id
    private int id;
    @Column("month_num")
    private int monthNum;
    @Column("amount_towards_principal")
    private int amountTowardsPrincipal;
    @Column("amount_towards_interest")
    private int amountTowardsInterest;
    @Column("total_amount")
    private int totalAmount;
    @Column("pre_payment_amount")
    private int prepaymentAmount;
}
