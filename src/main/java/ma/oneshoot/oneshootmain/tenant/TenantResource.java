package ma.oneshoot.oneshootmain.tenant;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cl/tenants")
@RequiredArgsConstructor
public class TenantResource {

    private final TenantService tenantService;
    private final TenantRegistrationService tenantRegistrationService;

    @GetMapping("/availability/{domain}")
    public ResponseEntity<Boolean> checkTenantDomainAvailability(@PathVariable String domain){
        return ResponseEntity.ok(tenantService.isTenantDomainAvailable(domain));
    }

    @PostMapping("/register")
        public ResponseEntity<Tenant> registerTenant(@RequestBody TenantRegistrationRequestDto requestDto){
        Tenant tenant = TenantRegistrationRequestDtoMapper.INSTANCE.toTenant(requestDto);
        return ResponseEntity.ok(tenantRegistrationService.registerTenant(tenant, SubscriptionType.FREE));
    }
}
