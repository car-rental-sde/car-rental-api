package it.unitn.carrentalapi.api;

import it.unitn.carrentalapi.openapi.api.AuthApiDelegate;
import it.unitn.carrentalapi.openapi.model.AuthenticationRequestModel;
import it.unitn.carrentalapi.openapi.model.AuthenticationResponseModel;
import it.unitn.carrentalapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthApiDelegateImpl implements AuthApiDelegate {

    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<AuthenticationResponseModel> login(AuthenticationRequestModel authenticationRequestModel) {
        log.debug("Trying to authenticate user...");
        log.trace("Authenticating user: [{}]", authenticationRequestModel);

        Optional<AuthenticationResponseModel> authenticationOptional = authenticationService.authenticate(authenticationRequestModel);

        return authenticationOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }
}
