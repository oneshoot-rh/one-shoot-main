package ma.oneshoot.oneshootmain.tenant;

import lombok.RequiredArgsConstructor;
import ma.oneshoot.oneshootmain.utils.SubscriptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TenantRegistrationService {

    private final UserRepository userRepository;
    private final TenantService tenantService;
    private final SubscriptionRepository subscriptionRepository;
    private final PasswordEncoder passwordEncoder;





    @Transactional
    public Tenant registerTenant(Tenant tenant,SubscriptionType subscriptionType) {
        Objects.requireNonNull(tenant, "Tenant object cannot be null");
        Objects.requireNonNull(subscriptionType, "Subscription type cannot be null");
        Objects.requireNonNull(tenant.getUsers(), "Tenant should have at least one user");
        tenant.getUsers().forEach(user -> user.setPassword(passwordEncoder.encode(user.getPassword())));
        userRepository.saveAll(tenant.getUsers());
        tenantService.saveTenant(tenant);
        Subscription subscription = Subscription.builder()
                .tenant(tenant)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(SubscriptionUtil.getSubscriptionDurationInDays(subscriptionType)))
                .subscriptionId(UUID.randomUUID())
                .subscriptionType(subscriptionType)
                .build();
        subscriptionRepository.save(subscription);
        tenantService.initDataBase(tenant.getOrganizationName());
        return tenant;
    }
}
