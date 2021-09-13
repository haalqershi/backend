package com.hesham.backend.controller;

import com.hesham.backend.model.StripeClient;
import com.stripe.model.Charge;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/payment")
@Api(value = "Stripe Payment Resource REST Endpoint")
public class PaymentController {

    private static Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private StripeClient stripeClient;

    @Autowired
    PaymentController(StripeClient stripeClient) {
        this.stripeClient = stripeClient;
    }

    @PostMapping("/charge")
    @ApiOperation(value = "charge a card", notes = "used to charge card", response = Charge.class)
    public Charge chargeCard(HttpServletRequest request) throws Exception {
        logger.info("charging card with amount of " + request.getHeader("amount"));
        String token = request.getHeader("token");
        Double amount = Double.parseDouble(request.getHeader("amount"));
        return this.stripeClient.chargeCreditCard(token, amount);
    }
}