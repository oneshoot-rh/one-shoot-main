package ma.oneshoot.tenantService.utils.aop;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.oneshoot.tenantService.tenant.TenantRepository;
import ma.oneshoot.tenantService.tenant.db.TenantContext;
import ma.oneshoot.tenantService.utils.annotations.TenantSpecific;
import ma.oneshoot.tenantService.utils.exceptions.TenantNotFoundException;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//@Aspect
//@Component
@RequiredArgsConstructor
@Slf4j
public class TenantSpecificAspect {
    private final TenantRepository tenantRepository;

    @Before("@annotation(tenantSpecific)")
    public void checkCustomCondition(TenantSpecific tenantSpecific) throws TenantNotFoundException {
        log.info("__Checking tenant specific condition__");

        if (tenantRepository.findByOrganizationName(TenantContext.getTenantId()).isEmpty()) {
            throw new TenantNotFoundException("Organization not found");
        }
    }
}
