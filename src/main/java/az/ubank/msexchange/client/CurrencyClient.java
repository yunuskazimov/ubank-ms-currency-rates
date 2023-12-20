package az.ubank.msexchange.client;

import az.ubank.msexchange.client.model.CurrencyClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "fixer-currency-client", url = "${api.fixer.url}")
public interface CurrencyClient {

    @GetMapping(value = "/latest")
    CurrencyClientResponse getRatesBaseUSD(@RequestParam("access_key") String access_key);

}
