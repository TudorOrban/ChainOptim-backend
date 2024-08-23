package org.chainoptim.core.payment.service;

import org.chainoptim.core.payment.model.CustomSubscriptionPlan;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public Session createCheckoutSession(CustomSubscriptionPlan customPlan) throws StripeException {
        com.stripe.Stripe.apiKey = stripeSecretKey;

        // Create line items based on the custom plan details
        SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                .setPriceData(
                        SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency("usd")
                                .setUnitAmount((long) Math.round(customPlan.getTotalDollarsMonthly() * 100)) // Convert dollars to cents
                                .setProductData(
                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                .setName("Custom Subscription Plan - " + customPlan.getPlanTier())
                                                .build()
                                )
                                .build()
                )
                .setQuantity(1L)
                .build();

        // Create the session params
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSuccessUrl("https://yourdomain.com/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("https://yourdomain.com/cancel")
                .build();

        // Create and return the session
        return Session.create(params);
    }
}
