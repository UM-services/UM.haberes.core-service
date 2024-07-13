/**
 * 
 */
package um.haberes.rest.configuration;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author daniel
 *
 */

@Configuration
@EnableJpaAuditing
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "um.haberes.rest.client")
@PropertySource("classpath:config/haberes.properties")
public class HaberesConfiguration {

}
