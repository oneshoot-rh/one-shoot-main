package ma.oneshoot.tenantService.subscription;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.oneshoot.tenantService.mailing.EmailService;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewSubscriptionEventListener implements ApplicationListener<NewSubscriptionEvent> {
    private final EmailService emailService;

    @Override
    public void onApplicationEvent(NewSubscriptionEvent event) {
        log.info("New subscription event received for tenant: " + event.getTenant().getId());
        // call onboarding service to initialze the database for tenant with id event.getTenantId()
        log.info("Calling onboarding service to initialize the database for tenant with id: " + event.getTenant().getDomainName());

        // send email with URL and guide to access 
        log.info("Sending email with access and login guide");
        String htmlContent = """
                <html>
                <body>
                <h2>Welcome to OneShoot</h2>
                <p>Thank you for subscribing to OneShoot. You can access your account by clicking on the link below:</p>
                <a href="http://localhost:3000">Login</a>
                <p>Use the following credentials to login:</p>
                <p>Username: %s
                <p>Password: default</p>
                </body>
                </html>
                """.formatted(event.getTenant().getDomainName());
        try {
            emailService.sendHtmlEmail(event.getTenant().getRequestorProfessionalEmail(), "Welcome to OneShoot", htmlContent);
            log.info("email to "+ event.getTenant().getRequestorProfessionalEmail() + " sent successfully");
        } catch (MessagingException e) {
            log.error("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
