package org.chainoptim.core.subscription.controller;

import org.chainoptim.core.subscription.dto.CreateSubscriptionPlanDTO;
import org.chainoptim.core.subscription.model.SubscriptionPlan;
import org.chainoptim.core.subscription.service.PaymentService;
import org.chainoptim.core.subscription.service.SubscriptionPlanService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/subscription-plans")
public class SubscriptionPlanController {

    private final SubscriptionPlanService planService;
    private final PaymentService paymentService;

    @Autowired
    public SubscriptionPlanController(SubscriptionPlanService planService, PaymentService paymentService) {
        this.planService = planService;
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createSubscriptionPlan(@RequestBody CreateSubscriptionPlanDTO customPlanDTO) {
        SubscriptionPlan plan = planService.createSubscriptionPlan(customPlanDTO);

        try {
            // Delegate the session creation to the PaymentService
            Session session = paymentService.createCheckoutSession(plan.getCustomPlan());

            // Return the session ID to the client
            Map<String, String> responseData = new HashMap<>();
            responseData.put("sessionId", session.getId());

            return ResponseEntity.ok(responseData);
        } catch (StripeException e) {
            return ResponseEntity.status(500).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/confirm-creation")
    public ResponseEntity<String> handleConfirmCreation(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        try {
            return ResponseEntity.ok(paymentService.handleConfirmPlanPaymentConfirmation(payload, sigHeader));
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Webhook Error: Signature verification failed");
        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Webhook Error: " + e.getMessage());
        }
    }
}
