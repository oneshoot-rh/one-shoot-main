package ma.oneshoot.oneshootmain.tenant.db.resolver;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Enumeration;

@Component
@Slf4j
public class HttpTenantResolver implements TenantResolver<HttpServletRequest> {
    @Override
    public String resolveTenantId(HttpServletRequest request) {
        return (((request.getHeader("forwarded").split("host=")[1]).split(";"))[0]).split("\\.")[0];
    }
}
