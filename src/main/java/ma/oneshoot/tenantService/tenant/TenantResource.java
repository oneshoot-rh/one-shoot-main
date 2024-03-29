package ma.oneshoot.tenantService.tenant;


import lombok.RequiredArgsConstructor;
import ma.oneshoot.tenantService.mailing.EmailService;
import ma.oneshoot.tenantService.subscription.NewSubscriptionEvent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cl/tenants")
@RequiredArgsConstructor
public class TenantResource {

    private final TenantService tenantService;
    private final EmailService emailService;

    @GetMapping("/availability/{domain}")
    public ResponseEntity<Boolean> checkTenantDomainAvailability(@PathVariable String domain){
        //emailService.sendEmail("bakkalimounir41@gmail.com", "Tenant Domain Availability", "Tenant Domain Availability Check for domain: "+domain);
        return ResponseEntity.ok(tenantService.isTenantDomainAvailable(domain));
    }
}
