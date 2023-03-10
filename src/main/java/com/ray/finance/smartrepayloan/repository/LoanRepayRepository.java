package com.ray.finance.smartrepayloan.repository;

import com.ray.finance.smartrepayloan.model.emibreakup.LoanEmiBreakup;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface LoanRepayRepository extends ReactiveCrudRepository<LoanEmiBreakup, Integer> {

}
