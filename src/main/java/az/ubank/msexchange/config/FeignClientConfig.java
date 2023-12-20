package az.ubank.msexchange.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "az.ubank.msexchange.client")
public class FeignClientConfig {
}