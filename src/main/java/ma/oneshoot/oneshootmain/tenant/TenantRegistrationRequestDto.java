package ma.oneshoot.oneshootmain.tenant;

public record TenantRegistrationRequestDto(
        String organizationName,
        String requesterFullName,
        RequestorDto requester,
        String requesterRole,
        String requesterProfessionalEmail,
        String requesterPassword,
        String requesterConfirmPassword
) {
}
