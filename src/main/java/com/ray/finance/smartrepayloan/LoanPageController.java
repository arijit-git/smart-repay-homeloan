package com.ray.finance.smartrepayloan;

import com.ray.finance.smartrepayloan.service.LoanPlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring6.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring6.context.webflux.ReactiveDataDriverContextVariable;

@Controller
@RequiredArgsConstructor
public class LoanPageController {

    private final LoanPlanService loanPlanService;

    @RequestMapping("/")
    public String index() {

        return "index";

    }

    @RequestMapping("/loanplans")
    public String loanPlans(final Model model) {
        // loads 1 and display 1, stream data, data driven mode.
        IReactiveDataDriverContextVariable reactiveDataDrivenMode =
                new ReactiveDataDriverContextVariable(loanPlanService.getAllPlans(), 1);
        model.addAttribute("plans", reactiveDataDrivenMode);
        return "loanplans";

    }
    @RequestMapping("/about")
    public String about() {
        return "about";

    }
    @RequestMapping("/emicalculator")
    public String emiCalculator() {
        return "emicalculator";

    }
}
