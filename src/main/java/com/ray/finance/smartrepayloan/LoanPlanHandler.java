package com.ray.finance.smartrepayloan;

import com.ray.finance.smartrepayloan.model.LoanRepaymentPlan;
import com.ray.finance.smartrepayloan.model.emibreakup.LoanEmiBreakup;
import com.ray.finance.smartrepayloan.model.loanplan.LoanPlan;
import com.ray.finance.smartrepayloan.service.LoanEmiBreakupService;
import com.ray.finance.smartrepayloan.service.LoanPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
@RequiredArgsConstructor
class LoanPlanHandler {

    private final LoanPlanService loanPlanService;
    private final LoanEmiBreakupService emiBreakupService;

    // loan plan
    public Mono<ServerResponse> all(ServerRequest req) {
        return ServerResponse.ok().body(this.loanPlanService.getAllPlans(), LoanPlan.class);
    }

    public Mono<ServerResponse> create(ServerRequest req) {
        return req.bodyToMono(LoanPlan.class)
                .flatMap(plan -> Mono.just(this.loanPlanService.createLoanPlan(plan)))
                .flatMap(plan -> ServerResponse.ok().body(plan, LoanPlan.class));
    }

    public Mono<ServerResponse> get(ServerRequest req) {
        return this.loanPlanService.getPlan(Integer.valueOf(req.pathVariable("id")))
                .flatMap(plan -> ServerResponse.ok().body(Mono.just(plan), LoanPlan.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> update(ServerRequest req) {
        return req.bodyToMono(LoanPlan.class)
                .flatMap(plan -> Mono.just(loanPlanService.updateLoanPlan(plan)))
                .flatMap(plan -> ServerResponse.ok().body(plan, LoanPlan.class));
    }

    public Mono<ServerResponse> delete(ServerRequest req) {
        return ServerResponse.noContent().build(this.loanPlanService.deleteLoanPlan(Integer.valueOf(req.pathVariable("id"))));
    }

    // emi breakup
    public Mono<ServerResponse> getAllEmiBreakup(ServerRequest req) {
        return ServerResponse.ok().body(emiBreakupService.getAllBreakups(), LoanEmiBreakup.class);
    }

    public Mono<ServerResponse> getEmiBreakupById(ServerRequest req) {
        return emiBreakupService.getBreakup(Integer.valueOf(req.pathVariable("id")))
                .flatMap(post -> ServerResponse.ok().body(Mono.just(post), LoanEmiBreakup.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> createEmiBreakup(ServerRequest req) {
        return req.bodyToMono(LoanEmiBreakup.class)
                .flatMap(plan -> Mono.just(emiBreakupService.createBreakup(plan)))
                .flatMap(plan -> ServerResponse.ok().body(plan, LoanEmiBreakup.class));
    }

    public Mono<ServerResponse> updateBreakup(ServerRequest req) {
        return req.bodyToMono(LoanEmiBreakup.class)
                .flatMap(plan -> Mono.just(emiBreakupService.updateBreakup(plan)))
                .flatMap(plan -> ServerResponse.ok().body(plan, LoanEmiBreakup.class));
    }

    public Mono<ServerResponse> deleteBreakupById(ServerRequest req) {
        return ServerResponse.noContent().build(emiBreakupService.deleteBreakup(Integer.valueOf(req.pathVariable("id"))));
    }

    // business logic

    public Mono<ServerResponse> calculate(ServerRequest req) {
        return req.bodyToMono(LoanPlan.class)
                //.flatMap(loanPlanService::createLoanPlan)
                .flatMap(plan -> Mono.just(loanPlanService.getRepaymentPlan(plan)))
                .flatMap(plan -> ServerResponse.ok().body(plan, LoanRepaymentPlan.class));
    }

}
