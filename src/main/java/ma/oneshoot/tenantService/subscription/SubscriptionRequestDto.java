package ma.oneshoot.tenantService.subscription;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SubscriptionRequestDto(
    @NotNull String organizationName,
    @NotNull String domainName,
    @NotNull String requestorRole,
    @NotNull String requestorProfessionalEmail,
    @NotNull String requestorName,
    @NotNull @NotBlank String requestorTemporaryPassword,
    @NotNull @NotBlank String subscriptionType
) {
    
}
