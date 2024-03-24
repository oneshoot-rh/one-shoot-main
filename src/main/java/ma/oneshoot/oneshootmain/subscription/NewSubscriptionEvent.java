package ma.oneshoot.oneshootmain.subscription;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class NewSubscriptionEvent extends ApplicationEvent {

    private String tenantId;

    public NewSubscriptionEvent(Object source, String tenantId) {
        super(source);
        this.tenantId = tenantId;
    }
    
}
