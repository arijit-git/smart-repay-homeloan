package com.ray.finance.smartrepayloan.service;

import com.ray.finance.smartrepayloan.model.LoanRepaymentPlan;
import com.ray.finance.smartrepayloan.model.loanplan.LoanPlan;
import com.ray.finance.smartrepayloan.repository.LoanPlanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import static java.lang.Math.pow;

@Service
@Log4j2
@RequiredArgsConstructor
public
class LoanPlanService {

    private final LoanPlanRepository planRepository;


    public Flux<LoanPlan> getAllPlans() {
        log.info("Getting all loan plans ...");
        return this.planRepository.findAll();
    }

    public Mono<LoanPlan> getPlan(Integer planId) {
        log.info("Getting loan plan for id : {}", planId);
        return this.planRepository.findById(planId);
    }

    public Mono<LoanPlan> createLoanPlan(LoanPlan request) {
        log.info("Creating loan plans ...");
        return this.planRepository.save(request);
    }

    public Mono<LoanPlan> updateLoanPlan(LoanPlan request) {
        log.info("Updating loan plan. Id : {}", request.getId());
        return planRepository.save(request);
    }

    public Mono<Void> deleteLoanPlan(Integer planId) {
        log.info("Deleting loan plan. Id : {}", planId);
        return this.planRepository.deleteById(planId);
    }

    // business logic implementations

    public Mono<LoanRepaymentPlan> getRepaymentPlan(LoanPlan request) {
        log.info("Getting repay details for principal amount : {}", request.getAmount());
        // calculate repay details
        // build repay plan
        double rate = request.getInterestRate();
        double pv = request.getAmount();
        int prepayCount = request.getNoOfYearlyEmiPrepayment();

        rate = rate / (12 * 100);
        int nper = request.getTenure() * 12;
        int startPeriod = 1;
        int endPeriod = request.getTenure() * 12;

        double totalInterestPaid = 0;
        double towardsInterest;
        double towardsPrincipal;
        int installmentCount = 0;
        // calculate emi
        var emi = round(-pmt(rate, nper, pv), 2);
        double outstanding = pv;
        log.info("First Outstanding = {}", outstanding);
        log.info("First EMI = {}", emi);
        // calculate cumulative interest paid
        double prepayment = prepayCount * emi;
        boolean prepaymentPaid = false;
        for (int i = startPeriod; i <= endPeriod; i++) {
            log.info("Installment ==> {}", i);
            log.info("Current Outstanding Before deduction = {}", round(outstanding, 0));
            if (outstanding > 0) {
                towardsInterest = (outstanding * rate);
                towardsPrincipal = (emi - towardsInterest);
                totalInterestPaid += towardsInterest;
                if(prepaymentPaid){
                    outstanding = outstanding - prepayment;
                    prepaymentPaid = false;
                }
                outstanding -= towardsPrincipal;
                log.info("Towards Principal = {}", towardsPrincipal);
                log.info("Towards Interest = {}", towardsInterest);
                var mod  = i % 12;
                if(prepayCount != 0 && mod == 0){
                    log.info("===> Prepayment = {}", prepayment);
                    prepaymentPaid = true;
                }
                log.info("Current Outstanding After deduction = {}", round(outstanding, 0));
                installmentCount++;
            }
        }
        log.info("Installment count = {}", installmentCount);
        String totalInterest = String.format("%.2f", totalInterestPaid);
        log.info("Total Interest Paid = {}", totalInterest);
        // send response
        LoanRepaymentPlan res = LoanRepaymentPlan.builder()
                .principal(pv)
                .interestPaid(totalInterest)
                .emi(round(emi, 2))
                .emiCount(installmentCount)
                .build();

        return Mono.just(res);
    }

    public static double cumipmt(double rate, double nper, double pv, double startPeriod, double endPeriod, int type) {
        double cumInterest = 0.0;
        double payment = pmt(rate, nper, pv);
        for (int i = (int) startPeriod; i <= endPeriod; i++) {
            double interest = ipmt(rate, i, nper, pv, payment, type);
            log.info("IPMT = {}", interest);
            cumInterest += interest;
        }
        return cumInterest;
    }

    // Helper function to calculate the interest payment for a specific period
    public static double ipmt(double rate, double per, double nper, double pv, double pmt, int type) {
        double interest = 0.0;
        if (type == 0) {
            interest = pv * Math.pow(1 + rate, per - 1) * rate;
        } else if (type == 1) {
            interest = (pv + pmt * (1 + rate)) * Math.pow(1 + rate, per - 1) * rate - pmt;
        }
        return interest;
    }

    // Helper function to calculate the payment per period
    public static double pmt(double rate, double nper, double pv) {
        return -rate * pv / (1 - Math.pow(1 + rate, -nper));
    }


    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }


}
