package ma.oneshoot.oneshootmain.tenant.db.resolver;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.util.Enumeration;

@Component
public class HttpTenantResolver implements TenantResolver<HttpServletRequest> {
    @Override
    public String resolveTenantId(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        headerNames.asIterator().forEachRemaining(System.out::println);
        return request
                .getHeader("x-forwarded-host")
                .split("\\.")[0];
    }
}
