package it.unitn.carrentalapi.service;

import it.unitn.carrentalapi.openapi.model.AuthenticationRequestModel;
import it.unitn.carrentalapi.openapi.model.AuthenticationResponseModel;

import java.util.Optional;

public interface AuthenticationService {
    Optional<AuthenticationResponseModel> authenticate(AuthenticationRequestModel request);
}
