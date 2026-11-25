/**
 * 
 */
package um.haberes.core.configuration;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.TimeUnit;

/**
 * @author daniel
 *
 */

@Configuration
@EnableAsync
@EnableJpaAuditing
@EnableFeignClients(basePackages = "um.haberes.core.client")
@PropertySource("classpath:config/haberes.properties")
public class HaberesConfiguration {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.MINUTES)
                .maximumSize(100));
        // Catálogos que pueden cambiar por SQL directo: la clave es la versión del catálogo,
        // así que el TTL sólo necesita limpiar las claves viejas.
        cacheManager.registerCustomCache("codigos", catalogoCache());
        cacheManager.registerCustomCache("codigos_grupos", catalogoCache());
        return cacheManager;
    }

    private Cache<Object, Object> catalogoCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(60, TimeUnit.SECONDS)
                .maximumSize(100)
                .build();
    }

}
