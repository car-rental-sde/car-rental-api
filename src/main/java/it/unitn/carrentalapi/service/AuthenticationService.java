package it.unitn.carrentalapi.service;

import it.unitn.carrentalapi.openapi.model.AuthenticationRequestModel;
import it.unitn.carrentalapi.openapi.model.AuthenticationResponseModel;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponseModel authenticate(AuthenticationRequestModel request);
    void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
