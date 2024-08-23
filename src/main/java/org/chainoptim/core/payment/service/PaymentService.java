package org.chainoptim.core.payment.service;

import org.chainoptim.core.payment.model.CustomSubscriptionPlan;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;

public interface PaymentService {

    Session createCheckoutSession(CustomSubscriptionPlan customPlan) throws StripeException;
}
