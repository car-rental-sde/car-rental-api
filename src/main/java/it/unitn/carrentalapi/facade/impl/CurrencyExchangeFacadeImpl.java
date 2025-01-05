package it.unitn.carrentalapi.facade.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import it.unitn.carrentalapi.facade.CurrencyExchangeFacade;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class CurrencyExchangeFacadeImpl implements CurrencyExchangeFacade {

    private static final String ACCESS_KEY = ""; // Replace with your actual access key, f.e. during CI/CD

    private final WebClient webClient;

    @Override
    public Optional<Long> convert(Long amount, String fromCurrency, String toCurrency) {
        if (fromCurrency.equals(toCurrency)) {
            log.info("No conversion needed, currencies are the same");
            return Optional.of(amount);
        }

        if (ACCESS_KEY.isEmpty()) {
            log.error("Access key is not set, currency conversion is disabled");
            return Optional.empty();
        }

        try {
            log.trace("Converting [{} {}] to [{}]", amount, fromCurrency, toCurrency);
            Mono<CurrencyRatesResponse> responseMono = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .scheme("https")
                            .host("api.exchangeratesapi.io")
                            .path("/v1/latest")
                            .queryParam("access_key", ACCESS_KEY)
                            .queryParam("base", fromCurrency)
                            .queryParam("symbols", toCurrency)
                            .build())
                    .retrieve()
                    .bodyToMono(CurrencyRatesResponse.class);

            CurrencyRatesResponse response = responseMono.block();

            if (response != null && response.getRates() != null && response.getRates().containsKey(toCurrency)) {
                Double exchangeRate = response.getRates().get(toCurrency);
                if (exchangeRate != null) {
                    Double convertedAmount = amount * exchangeRate;
                    log.info("Converted [{} {}] to [{} {}] at rate [{}]", amount, fromCurrency, convertedAmount, toCurrency, exchangeRate);
                    return Optional.of(convertedAmount.longValue());
                }
            }

            log.error("Conversion failed, no rate found for [{}]", toCurrency);
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error during currency conversion", e);
            return Optional.empty();
        }
    }

    @Setter
    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CurrencyRatesResponse {
        private Map<String, Double> rates;
    }
}
