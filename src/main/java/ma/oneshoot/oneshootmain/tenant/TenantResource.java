package ma.oneshoot.oneshootmain.tenant;


import lombok.RequiredArgsConstructor;
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

    @GetMapping("/availability/{domain}")
    public ResponseEntity<Boolean> checkTenantDomainAvailability(@PathVariable String domain){
        return ResponseEntity.ok(tenantService.isTenantDomainAvailable(domain));
    }
}
