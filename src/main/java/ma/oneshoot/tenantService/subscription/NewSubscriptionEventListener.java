package ma.oneshoot.tenantService.subscription;

import java.util.Objects;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.client.RestTemplate;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.oneshoot.tenantService.keycloak.KeycloakAdminService;
import ma.oneshoot.tenantService.mailing.EmailService;
import ma.oneshoot.tenantService.tenant.Tenant;
import ma.oneshoot.tenantService.utils.exceptions.TenantDBInitException;

@Component
@Slf4j
@RequiredArgsConstructor
public class NewSubscriptionEventListener  implements ApplicationListener<NewSubscriptionEvent>{
    private final EmailService emailService;
    private final KeycloakAdminService keycloakAdminService;
    private final RestTemplate restTemplate;
    private final KafkaTemplate<String, String> kafkaTemplate;

    //@EventListener(NewSubscriptionEvent.class)
    //@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Override
    public void onApplicationEvent(NewSubscriptionEvent event)  {
        var tenant = event.getTenant();
        var user = tenant.getUsers().get(0);
        Objects.requireNonNull(tenant);
        Objects.requireNonNull(user);
        String htmlContent;
        log.info("New subscription event received for tenant: " + tenant.getId());
        // create user in keycloak
        var response = keycloakAdminService.createUser(user);
        if (Objects.isNull(response)) {
            log.error("Error creating user in keycloak for tenant: " + tenant.getId());
            // html content with error message
            htmlContent = """
                    <html>
                    <body>
                    <h2>Welcome to OneShoot</h2>
                    <p>Thank you for subscribing to OneShoot.</p>
                    <p>We are sorry for the inconvenience, but there was an unexpected error while creating your account.</p>
                    <p>Please contact support for assistance.</p>
                    <strong>Domain: %s</strong>
                    <strong>Support Email: support@oneshoot.com</strong>
                    <strong>Support Phone: +212 123 456 789</strong>
                    </body>
                    """.formatted(tenant.getDomainName());
        }
        // call onboarding service to initialze the database for tenant with id event.getTenantId()
        log.info("Calling onboarding service to initialize the database for tenant with id: " + tenant.getDomainName());
        // var requestEntity = new HttpEntity<>(tenant);
        // var onboardServiceCreateDbResponse = restTemplate.exchange("http://EONBOARDSERVICE/api/v1/tenants/dbinit", HttpMethod.POST,requestEntity,Object.class);
        // var statusCode = onboardServiceCreateDbResponse.getStatusCode();
        // if (!statusCode.equals(HttpStatus.CREATED)) {
        //     // overriden methods not allows to throw checked exception! (was before when I was implementing ApplicationListener)
        //     log.debug("(Failed to instantiate database for tenant "+tenant.getDomainName()+")");
        //     throw new TenantDBInitException("SERVER EROR: Something bad happen (Failed to instantiate database for tenant "+tenant.getDomainName());
        // }
        kafkaTemplate.send("tenant-db-init", tenant.getDomainName());
        log.info("EonbordService Successfully created the database");
        // send email with URL and guide to access 
        log.info("Sending email with access and login guide");
        String tenantAccessDomain = "http://"+tenant.getDomainName()+".oneshoot.local";
        // call keycloak to add redirect url
        keycloakAdminService.addRedirectUrl(tenantAccessDomain);
        htmlContent = """
                <html>
                <body>
                <h2>Welcome to OneShoot</h2>
                <p>Thank you for subscribing to OneShoot. You can access your account by clicking on the link below:</p>
                <a href="%s">Login</a>
                <p>Use the following credentials to login:</p>
                <p>Username: %s </p>
                <p>Temporary Password: %s </p>
                </body>
                </html>
                """.formatted(tenantAccessDomain+":3000/login",user.getUsername(), user.getPassword());
        try {
            emailService.sendHtmlEmail(tenant.getRequestorProfessionalEmail(), "Welcome to OneShoot", htmlContent);
            log.info("email to "+ tenant.getRequestorProfessionalEmail() + " sent successfully");
        } catch (MessagingException e) {
            log.error("Error sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}
