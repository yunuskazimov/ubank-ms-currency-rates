package az.ubank.msexchange.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyResponseDto {
    private BigDecimal result;

    public static CurrencyResponseDto of(BigDecimal result) {
        return new CurrencyResponseDto(result);
    }
}
