package ma.oneshoot.tenantService.tenant.db;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class TenantContext {

    private static final ThreadLocal<String> tenantId = new InheritableThreadLocal<>();
    public static final String DEFAULT_TENANT_ID = "tenantService";


    public static void setTenantId(String tenant) {
        log.info("Setting tenant to " + tenant);
        tenantId.set(tenant);
    }
    
    public static String getTenantId() {
        return tenantId.get();
    }

    public static void clear() {
        tenantId.remove();
    }
}
