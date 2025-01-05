package it.unitn.carrentalapi.facade;

import java.util.Optional;

public interface CurrencyExchangeFacade {
    Optional<Long> convert(Long amount, String fromCurrency, String toCurrency);
}
