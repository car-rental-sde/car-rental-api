package it.unitn.carrentalapi.service;

import it.unitn.carrentalapi.openapi.model.AuthenticationRequestModel;
import it.unitn.carrentalapi.openapi.model.AuthenticationResponseModel;
import it.unitn.carrentalapi.openapi.model.RegisterRequestModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {

    AuthenticationResponseModel register(RegisterRequestModel request);
    AuthenticationResponseModel authenticate(AuthenticationRequestModel request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
