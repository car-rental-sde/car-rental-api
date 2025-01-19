package it.unitn.carrentalapi.service.impl;

import it.unitn.carrentalapi.entity.UserEntity;
import it.unitn.carrentalapi.openapi.model.AuthenticationRequestModel;
import it.unitn.carrentalapi.openapi.model.AuthenticationResponseModel;
import it.unitn.carrentalapi.repository.UserRepository;
import it.unitn.carrentalapi.security.JwtService;
import it.unitn.carrentalapi.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public Optional<AuthenticationResponseModel> authenticate(AuthenticationRequestModel request) {
        Optional<UserEntity> userOptional = userRepository.findByUsername(request.getUsername());
        if (userOptional.isEmpty()) {
            log.warn("User not found: [{}]", request.getUsername());
            return Optional.empty();
        }
        UserEntity user = userOptional.get();

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        var jwtToken = jwtService.generateToken(user.getUsername());

        return Optional.ofNullable(AuthenticationResponseModel.builder()
                .accessToken(jwtToken)
                .build());
    }
}
