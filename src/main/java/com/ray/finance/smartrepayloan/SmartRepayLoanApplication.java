package com.ray.finance.smartrepayloan;

import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@SpringBootApplication
public class SmartRepayLoanApplication {

    @Bean
    ConnectionFactoryInitializer initializer(@Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        ResourceDatabasePopulator resource =
                new ResourceDatabasePopulator(new ClassPathResource("schema.sql"));
        initializer.setDatabasePopulator(resource);
        return initializer;
    }

    public static void main(String[] args) {
        SpringApplication.run(SmartRepayLoanApplication.class, args);
    }

    public static final String PLANS_API = "/plans";
    public static final String PLANS_API_BY_ID = "/plans/{id}";
    public static final String EMI_BREAKUP_API = "/emibreakup";
    public static final String EMI_BREAKUP_API_BY_ID = "/emibreakup/{id}";

    public static final String REPAY_API = "/calculate";

    @Bean
    @CrossOrigin("http://localhost:8081/")
    public RouterFunction<ServerResponse> routes(LoanPlanHandler planHandler) {

        return route(GET(PLANS_API), planHandler::all)
                .andRoute(POST(PLANS_API), planHandler::create)
                .andRoute(GET(PLANS_API_BY_ID), planHandler::get)
                .andRoute(PUT(PLANS_API), planHandler::update)
                .andRoute(DELETE(PLANS_API_BY_ID), planHandler::delete)
                .andRoute(GET(EMI_BREAKUP_API), planHandler::getAllEmiBreakup)
                .andRoute(GET(EMI_BREAKUP_API_BY_ID), planHandler::getEmiBreakupById)
                .andRoute(POST(EMI_BREAKUP_API), planHandler::createEmiBreakup)
                .andRoute(PUT(EMI_BREAKUP_API), planHandler::updateBreakup)
                .andRoute(DELETE(EMI_BREAKUP_API_BY_ID), planHandler::deleteBreakupById)

                .andRoute(POST(REPAY_API), planHandler::calculate)
                ;
    }

}


