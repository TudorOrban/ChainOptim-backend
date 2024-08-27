package org.chainoptim.core.tenant.subscription.service;

import org.chainoptim.core.tenant.subscription.model.CustomSubscriptionPlan;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.Invoice;
import com.stripe.model.Price;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Value("${stripe.confirm.payment.webhook.secret}")
    private String endpointSecret;

    public Session createCheckoutSession(CustomSubscriptionPlan customPlan) throws StripeException {
        com.stripe.Stripe.apiKey = stripeSecretKey;

        long totalAmountInCents = Math.round(customPlan.getTotalDollarsMonthly() * 100);

        // Create the price dynamically based on the total amount
        Price price = createStripePrice(totalAmountInCents);

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setPrice(price.getId())
                        .setQuantity(1L)
                        .build())
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .setSuccessUrl("http://localhost:4200/subscribe/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:4200/subscribe/cancel")
                .build();

        return Session.create(params);
    }

    private Price createStripePrice(long amount) throws StripeException {
        PriceCreateParams priceParams = PriceCreateParams.builder()
                .setUnitAmount(amount)
                .setCurrency("usd")
                .setRecurring(PriceCreateParams.Recurring.builder()
                        .setInterval(PriceCreateParams.Recurring.Interval.MONTH)
                        .build())
                .setProductData(PriceCreateParams.ProductData.builder()
                        .setName("Custom Subscription")
                        .build())
                .build();

        return Price.create(priceParams);
    }

    public String handleConfirmPlanPaymentConfirmation(String payload, String sigHeader) throws StripeException {
        Event event = Webhook.constructEvent(
                payload, sigHeader, endpointSecret
        );

        switch (event.getType()) {
            case "checkout.session.completed":
                Session session = (Session) event.getDataObjectDeserializer().getObject().orElse(null);
                if (session != null) {
                    handleCheckoutSessionCompleted(session);
                }
                break;
            case "invoice.paid":
                Invoice invoice = (Invoice) event.getDataObjectDeserializer().getObject().orElse(null);
                if (invoice != null) {
                    handleInvoicePaid(invoice);
                }
                break;
            default:
                System.out.println("Unhandled event type: " + event.getType());
        }

        return "Received";

    }

    private void handleCheckoutSessionCompleted(Session session) {
        // Implement your logic to handle a completed checkout session
        System.out.println("Checkout session completed for session ID: " + session.getId());
    }

    private void handleInvoicePaid(Invoice invoice) {
        // Implement your logic to handle a paid invoice
        System.out.println("Invoice paid for invoice ID: " + invoice.getId());
    }
}
