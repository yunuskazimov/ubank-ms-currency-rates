package az.ubank.msexchange.config;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CacheEvictScheduler {

    @Scheduled(fixedDelay = 60 * 1000L)
    @CacheEvict("currency")
    public void evictCurrencyCache() {
    }

}