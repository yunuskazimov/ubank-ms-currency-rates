package az.ubank.msexchange.service;

import az.ubank.msexchange.model.CurrencyRequestDto;
import az.ubank.msexchange.model.CurrencyResponseDto;

public interface CurrencyService {
    CurrencyResponseDto exchange(CurrencyRequestDto requestDto);
}
