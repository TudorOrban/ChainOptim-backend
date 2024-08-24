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

        String stripePriceId = "price_1PrIw1RvgicEPEXuwJpow9dd";

        // Create the session params
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setPrice(stripePriceId)
                        .setQuantity(1L)
                        .build())
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSuccessUrl("http://localhost:4200/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:4200/cancel")
                .build();

        // Create and return the session
        return Session.create(params);
    }
}
