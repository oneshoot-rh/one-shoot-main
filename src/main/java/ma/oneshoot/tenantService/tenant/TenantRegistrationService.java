package ma.oneshoot.tenantService.tenant;

import lombok.RequiredArgsConstructor;
import ma.oneshoot.tenantService.subscription.FreeTrial;
import ma.oneshoot.tenantService.subscription.FreeTrialRepository;
import ma.oneshoot.tenantService.subscription.NewSubscriptionEvent;
import ma.oneshoot.tenantService.subscription.SubscriptionPayment;
import ma.oneshoot.tenantService.subscription.SubscriptionPaymentRepository;
import ma.oneshoot.tenantService.utils.SubscriptionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TenantRegistrationService {

    private final UserRepository userRepository;
    private final TenantService tenantService;
    private final SubscriptionRepository subscriptionRepository;
    private final PasswordEncoder passwordEncoder;
    private final FreeTrialRepository freeTrialRepository;
    private final SubscriptionPaymentRepository subscriptionPaymentRepository;
    @Autowired
    private final ApplicationEventPublisher applicationEventPublisher;





    @Transactional(rollbackFor = Exception.class)
    public Tenant registerTenant(Tenant tenant,SubscriptionType subscriptionType, boolean isDemo) {
        Objects.requireNonNull(tenant, "Tenant object cannot be null");
        Objects.requireNonNull(subscriptionType, "Subscription type cannot be null");
        Objects.requireNonNull(tenant.getUsers(), "Tenant should have at least one user");
        // verify if tenant already exists
        if (tenantService.existsByDomainName(tenant.getDomainName())) {
            throw new IllegalArgumentException("Tenant with domain name " + tenant.getDomainName() + " already exists");
        }
        tenant.getUsers().forEach(user -> user.setPassword(passwordEncoder.encode(user.getPassword())));
        userRepository.saveAll(tenant.getUsers());
        var savedTenant = tenantService.saveTenant(tenant);
        if (isDemo) {
            var freetrial = FreeTrial.builder()
            .endDate(LocalDateTime.now().plus(10, ChronoUnit.DAYS))
            .tenant(savedTenant)
            .organizationName(tenant.getOrganizationName())
            .build();
            freeTrialRepository.save(freetrial);
        }
        // for demo payment delayed to 10 days
        var subscriptionPayment = SubscriptionPayment.builder()
                .tenant(savedTenant)
                .organizationName(tenant.getOrganizationName())
                .paymentDelay(LocalDateTime.now().plusDays(SubscriptionUtil.getPaymentDelayInDays(subscriptionType)))
                .amount(SubscriptionUtil.getSubscriptionPrice(subscriptionType))
                .reference(UUID.randomUUID())
                .build();
        subscriptionPaymentRepository.save(subscriptionPayment); 
        Subscription subscription = Subscription.builder()
                .tenant(tenant)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(SubscriptionUtil.getSubscriptionDurationInDays(subscriptionType)))
                .subscriptionId(UUID.randomUUID())
                .subscriptionType(subscriptionType)
                .build();
        subscriptionRepository.save(subscription);
        tenantService.initDataBase(tenant.getDomainName());
        NewSubscriptionEvent newSubscriptionEvent = new NewSubscriptionEvent(this, tenant.domainName);
        applicationEventPublisher.publishEvent(newSubscriptionEvent);
        return tenant;
    }


    public List<Subscription> getSubscriptions(){
        return subscriptionRepository.findAll();
    }
}
