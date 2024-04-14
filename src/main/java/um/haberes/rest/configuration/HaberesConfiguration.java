/**
 * 
 */
package um.haberes.rest.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * @author daniel
 *
 */

@Data
@Configuration
@EnableJpaAuditing
@PropertySource("classpath:config/haberes.properties")
public class HaberesConfiguration {

}
