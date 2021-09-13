package com.hesham.backend.model;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class StripeClient {

    private final String SOURCE = "source";

    @Autowired
    StripeClient() {
        Stripe.apiKey = "sk_test_51JUZpRD3Qqgxv1SjLZPUXIUCXm6mC8VeIfQcz6MhfGi4UZIzhyoxYA2AGX3sawLA1Zu6ZJOFT9ejOQp1VUGqHYLv00Vbc5ECXr";
    }

    public Charge chargeCreditCard(String token, double amount) throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", (int)amount);
        chargeParams.put("currency", "USD");
        chargeParams.put(SOURCE, token);
        return  Charge.create(chargeParams);
    }
    public Customer createCustomer(String token, String email) throws StripeException {
        Map<String, Object> customerParams = new HashMap<>();
        customerParams.put("email", email);
        customerParams.put(SOURCE, token);
        return Customer.create(customerParams);
    }
    public Charge chargeCustomerCard(String customerId, int amount) throws StripeException {
        String sourceCard = Customer.retrieve(customerId).getDefaultSource();
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "USD");
        chargeParams.put("customer", customerId);
        chargeParams.put(SOURCE, sourceCard);
        return Charge.create(chargeParams);
    }

}