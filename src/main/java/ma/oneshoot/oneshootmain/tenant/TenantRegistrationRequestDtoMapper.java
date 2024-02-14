package ma.oneshoot.oneshootmain.tenant;


import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TenantRegistrationRequestDtoMapper {

    TenantRegistrationRequestDtoMapper INSTANCE = Mappers.getMapper(TenantRegistrationRequestDtoMapper.class);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(source = "organizationName", target = "organizationName"),
            @Mapping(source = "requesterRole", target = "requestorRole"),
            @Mapping(source = "requesterProfessionalEmail", target = "requestorProfessionalEmail"),
            @Mapping(source = "requester", target = "users", qualifiedByName = "mapUsers")
    })
    Tenant toTenant(TenantRegistrationRequestDto requestDto);

   @Named("mapUsers")
    default List<User> mapUsers(RequestorDto value) {
        User user = new User();
        user.setName(value.fullName());
        user.setUsername(value.professionalEmail());
        user.setPassword(value.password());
        return List.of(user);
    }
}
