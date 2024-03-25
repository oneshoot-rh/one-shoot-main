package ma.oneshoot.tenantService.tenant.db.resolver;

import org.springframework.lang.NonNull;

@FunctionalInterface
public interface TenantResolver<T> {

    String resolveTenantId(@NonNull T obj);
}
