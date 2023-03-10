package com.ray.finance.smartrepayloan.repository;

import com.ray.finance.smartrepayloan.model.loanplan.LoanPlan;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface LoanPlanRepository extends ReactiveCrudRepository<LoanPlan, Integer> {

}
