package dev.kazimiruk.tasksystem.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CaffeineCache caffeineCache() {
        return new CaffeineCache("loginCache", Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(10))
                .initialCapacity(1)
                .build());
    }

    @Bean
    public CacheManager caffeineCacheManager(CaffeineCache caffeineCache) {
        SimpleCacheManager manager = new SimpleCacheManager();
        manager.setCaches(List.of(caffeineCache));
        return manager;
    }
}
