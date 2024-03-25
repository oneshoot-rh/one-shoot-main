package ma.oneshoot.tenantService.utils.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.oneshoot.tenantService.tenant.Tenant;
import ma.oneshoot.tenantService.tenant.TenantRepository;
import ma.oneshoot.tenantService.tenant.db.TenantContext;
import ma.oneshoot.tenantService.tenant.db.resolver.HttpTenantResolver;
import ma.oneshoot.tenantService.utils.exceptions.TenantNotFoundException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class ClientSpecificRequestInterceptor implements HandlerInterceptor {

    private final TenantRepository tenantRepository;
    private final HttpTenantResolver tenantResolver;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/cl")){
            String resolveTenantId = tenantResolver.resolveTenantId(request);
            String tenantId = resolveTenantId ==null ? TenantContext.DEFAULT_TENANT_ID : resolveTenantId;
            Optional<Tenant> byOrganizationName = tenantRepository.findByOrganizationName(tenantId);
            if (byOrganizationName.isEmpty()) throw new TenantNotFoundException(String.format("No organization with id %s is registered in OneShoot domain," +
                    " Or can not process request.\nPlease contact contact@oneshoot.com for further information",tenantId));
        }
        return true;
    }

}
