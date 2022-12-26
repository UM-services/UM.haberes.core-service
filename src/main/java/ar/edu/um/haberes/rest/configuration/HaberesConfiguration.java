/**
 * 
 */
package ar.edu.um.haberes.rest.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author daniel
 *
 */
@Configuration
@EnableJpaAuditing
@PropertySource("classpath:config/haberes.properties")
public class HaberesConfiguration {

}
