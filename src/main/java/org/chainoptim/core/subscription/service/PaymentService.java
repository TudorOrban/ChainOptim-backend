package org.chainoptim.core.subscription.service;

import org.chainoptim.core.subscription.model.CustomSubscriptionPlan;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

public interface PaymentService {

    Session createCheckoutSession(CustomSubscriptionPlan customPlan) throws StripeException;
    String handleConfirmPlanPaymentConfirmation(String payload, String sigHeader) throws StripeException;
}
