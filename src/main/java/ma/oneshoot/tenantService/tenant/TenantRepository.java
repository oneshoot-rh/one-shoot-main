package ma.oneshoot.tenantService.tenant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Optional<Tenant> findByOrganizationName(String organizationName);

    boolean existsByDomainName(String domainName);

    Optional<Tenant> findByDomainName(String domain);
}
