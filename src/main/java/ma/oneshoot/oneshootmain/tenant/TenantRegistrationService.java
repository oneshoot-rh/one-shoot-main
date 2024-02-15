package ma.oneshoot.oneshootmain.tenant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        subscriptionRepository.findByOrganizationName(tenant.getOrganizationName()).ifPresent(subscription -> {
            if (subscriptionType.equals(SubscriptionType.FREE)) throw new RuntimeException("Tenant with name " + tenant.getOrganizationName() + " already registered for a freemium subscription.");
            else {
                log.info("Tenant with name "+ tenant.getOrganizationName() + " is registering for a premium subscription. Subscription will be updated");
            }
        });
        tenant.getUsers().forEach(user -> user.setPassword(passwordEncoder.encode(user.getPassword())));
        userRepository.saveAll(tenant.getUsers());
        tenantService.saveTenant(tenant);
        Subscription subscription = Subscription.builder()
                .tenant(tenant)
                .organizationName(tenant.getOrganizationName())
                .startDate(LocalDateTime.now())
               // .endDate(LocalDateTime.now().plusDays(SubscriptionUtil.getSubscriptionDurationInDays(subscriptionType)))
                .subscriptionId(UUID.randomUUID())
                .subscriptionType(subscriptionType)
                .build();
        subscriptionRepository.save(subscription);
        tenantService.initDataBase(tenant.getOrganizationName());
        return tenant;
    }
}
