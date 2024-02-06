package ma.oneshoot.oneshootmain.utils.interceptors;

import io.micrometer.common.KeyValue;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.oneshoot.oneshootmain.tenant.db.TenantContext;
import ma.oneshoot.oneshootmain.tenant.db.resolver.HttpTenantResolver;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.filter.ServerHttpObservationFilter;

@Component
@RequiredArgsConstructor
@Slf4j

public class TenantInterceptor implements HandlerInterceptor {

    private final HttpTenantResolver tenantResolver;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String resolveTenantId = tenantResolver.resolveTenantId(request);
        String tenantId = resolveTenantId ==null ? TenantContext.DEFAULT_TENANT_ID : resolveTenantId;
        TenantContext.setTenantId(tenantId);
        MDC.put("tenantId", tenantId);
        ServerHttpObservationFilter.findObservationContext(request)
                .ifPresent(observation -> observation.addHighCardinalityKeyValue(KeyValue.of("tenantId", tenantId))
                );
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        clear();
    }

    private void clear() {
        MDC.clear();
        TenantContext.clear();
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        clear();
    }
}
