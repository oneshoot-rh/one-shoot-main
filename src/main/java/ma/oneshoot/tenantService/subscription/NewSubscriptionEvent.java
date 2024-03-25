package ma.oneshoot.tenantService.subscription;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import ma.oneshoot.tenantService.tenant.Tenant;

@Getter
public class NewSubscriptionEvent extends ApplicationEvent {

    private Tenant tenant;

    public NewSubscriptionEvent(Object source, Tenant tenant) {
        super(source);
        this.tenant = tenant;
    }
    
}
