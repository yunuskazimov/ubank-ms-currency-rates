package az.ubank.msexchange.model;

import az.ubank.msexchange.exception.ErrorCodes;
import lombok.Data;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
public class CurrencyRequestDto {
    private String from;
    private String to;
    @Positive(message = ErrorCodes.INVALID_AMOUNT)
    private BigDecimal amount;
}
