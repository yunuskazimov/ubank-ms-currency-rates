package az.ubank.msexchange.client.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class CurrencyClientResponse {
    private String base;
    private String date;
    private Map<String, BigDecimal> rates;
}