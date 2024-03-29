package ma.oneshoot.tenantService.subscription;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ma.oneshoot.tenantService.tenant.SubscriptionType;
import ma.oneshoot.tenantService.tenant.Tenant;
import ma.oneshoot.tenantService.tenant.TenantRegistrationService;
import ma.oneshoot.tenantService.tenant.User;

@RestController
@RequestMapping("/cl/subscriptions")
@RequiredArgsConstructor
public class TenantSubscriptionResource {


    private final TenantRegistrationService tenantRegistrationService;


    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getSubscriptions(){
        return ResponseEntity.ok(tenantRegistrationService.getSubscriptions());
    }

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribeForDemo(@RequestBody @Valid SubscriptionRequestDto subscriptionRequestDto,@RequestParam("isDemo") boolean isDemo){
        var tanant = Tenant.builder()
                .organizationName(subscriptionRequestDto.organizationName())
                .domainName(subscriptionRequestDto.domainName())
                .requestorProfessionalEmail(subscriptionRequestDto.requestorProfessionalEmail())
                .requestorRole(subscriptionRequestDto.requestorRole())
                .build();
        var user = User.builder()
                .username(subscriptionRequestDto.requestorProfessionalEmail())
                .name(subscriptionRequestDto.requestorName())
                .password(subscriptionRequestDto.requestorTemporaryPassword())
                .role(subscriptionRequestDto.requestorRole())
                .tenant(tanant)
                .build();
        tanant.setUsers(List.of(user));
        return ResponseEntity.ok(tenantRegistrationService.registerTenant(tanant,SubscriptionType.valueOf(subscriptionRequestDto.subscriptionType()),isDemo));
    }
    
}
