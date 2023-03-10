package com.ray.finance.smartrepayloan.service;

import com.ray.finance.smartrepayloan.model.emibreakup.LoanEmiBreakup;
import com.ray.finance.smartrepayloan.repository.LoanRepayRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Log4j2
@RequiredArgsConstructor
public
class LoanEmiBreakupService {

    private final LoanRepayRepository repayRepository;


    public Flux<LoanEmiBreakup> getAllBreakups() {
        log.info("Getting all breakups ...");
        return repayRepository.findAll();
    }

    public Mono<LoanEmiBreakup> getBreakup(Integer planId) {
        log.info("Getting breakup for id : {}", planId);
        return repayRepository.findById(planId);
    }

    public Mono<LoanEmiBreakup> createBreakup(LoanEmiBreakup request) {
        log.info("Creating breakup ...");
        return repayRepository.save(request);
    }

    public Mono<LoanEmiBreakup> updateBreakup(LoanEmiBreakup request) {
        log.info("Updating breakup. Id : {}", request.getId());
        return repayRepository.save(request);
    }

    public Mono<Void> deleteBreakup(Integer planId) {
        log.info("Deleting breakup. Id : {}", planId);
        return repayRepository.deleteById(planId);
    }


}
