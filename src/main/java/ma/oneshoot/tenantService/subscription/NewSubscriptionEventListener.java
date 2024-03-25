package ma.oneshoot.tenantService.subscription;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class NewSubscriptionEventListener implements ApplicationListener<NewSubscriptionEvent> {

    @Override
    public void onApplicationEvent(NewSubscriptionEvent event) {
        log.info("New subscription event received for tenant: " + event.getTenantId());
        // call onboarding service to initialze the database for tenant with id event.getTenantId()
        log.info("Calling onboarding service to initialize the database for tenant with id: " + event.getTenantId());
    }
    
}
