package um.haberes.core.hexagonal.seguridad.infrastructure.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(SeguridadProperties.class)
public class SeguridadConfiguration {
}
