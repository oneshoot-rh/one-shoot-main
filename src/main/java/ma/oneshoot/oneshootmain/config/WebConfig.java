package ma.oneshoot.oneshootmain.config;

import lombok.RequiredArgsConstructor;
import ma.oneshoot.oneshootmain.utils.interceptors.ClientSpecificRequestInterceptor;
import ma.oneshoot.oneshootmain.utils.interceptors.TenantInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final TenantInterceptor tenantInterceptor;
    private final ClientSpecificRequestInterceptor clientSpecificRequestInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(clientSpecificRequestInterceptor).order(1);
        registry.addInterceptor(tenantInterceptor);
    }
}
