package az.ubank.msexchange.service.impl;

import az.ubank.msexchange.client.CurrencyClient;
import az.ubank.msexchange.client.model.CurrencyClientResponse;
import az.ubank.msexchange.model.CurrencyRequestDto;
import az.ubank.msexchange.model.CurrencyResponseDto;
import az.ubank.msexchange.exception.ClientServerException;
import az.ubank.msexchange.exception.InvalidCurrencyException;
import az.ubank.msexchange.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "currency")
public class CurrencyServiceImpl implements CurrencyService {
    @Value("${api.fixer.apiKey}")
    private String accessKey;
    @Value("${api.fixer.base}")
    private String base;
    private final CurrencyClient currencyClient;

    @Override
    @Cacheable
    public CurrencyResponseDto exchange(CurrencyRequestDto requestDto) {
        log.info("service exchange start with Request DTO: {}", requestDto);
        CurrencyClientResponse clientResponse = getCurrencyFromClient();
        BigDecimal fromRate = getFromRate(requestDto, clientResponse);
        BigDecimal toRate = getToRate(requestDto, clientResponse);
        return calculate(requestDto, fromRate, toRate);
    }

    private CurrencyResponseDto calculate(CurrencyRequestDto requestDto,
                                          BigDecimal fromRate,
                                          BigDecimal toRate) {
        if (requestDto.getFrom().equals(base)) {
            return exchangeFromEur(requestDto, toRate);
        } else if (requestDto.getTo().equals(base)) {
            return exchangeToEur(requestDto, fromRate);
        } else {
            return exchangeAnotherRates(requestDto, fromRate, toRate);
        }
    }

    private CurrencyResponseDto exchangeAnotherRates(CurrencyRequestDto requestDto,
                                                     BigDecimal fromRate,
                                                     BigDecimal toRate) {
        return CurrencyResponseDto.of(requestDto.getAmount()
                .multiply(toRate).divide(fromRate, 5, RoundingMode.HALF_UP));
    }

    private CurrencyResponseDto exchangeToEur(CurrencyRequestDto requestDto, BigDecimal from) {
        return CurrencyResponseDto.of(requestDto.getAmount()
                .divide(from, 5, RoundingMode.HALF_UP));
    }

    private CurrencyResponseDto exchangeFromEur(CurrencyRequestDto requestDto, BigDecimal to) {
        return CurrencyResponseDto.of((requestDto.getAmount()
                .multiply(to))
                .multiply(requestDto.getAmount()));
    }

    private BigDecimal getToRate(CurrencyRequestDto requestDto, CurrencyClientResponse clientResponse) {
        BigDecimal toRate = clientResponse.getRates().get(requestDto.getTo());
        if (toRate != null) {
            return toRate;
        } else {
            throw new InvalidCurrencyException("Enter Valid Rate");
        }
    }

    private BigDecimal getFromRate(CurrencyRequestDto requestDto, CurrencyClientResponse clientResponse) {
        BigDecimal fromRate = clientResponse.getRates().get(requestDto.getFrom());
        if (fromRate != null) {
            return fromRate;
        } else {
            throw new InvalidCurrencyException("Enter Valid Rate");
        }
    }

    private CurrencyClientResponse getCurrencyFromClient() {
        CurrencyClientResponse clientResponse = currencyClient.getRatesBaseUSD(accessKey);
        if (clientResponse.getBase().equals(base)) {
            return clientResponse;
        } else {
            throw new ClientServerException("Client Server Connection Error");
        }
    }
}
