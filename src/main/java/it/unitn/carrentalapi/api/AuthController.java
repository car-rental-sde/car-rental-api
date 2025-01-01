package it.unitn.carrentalapi.api;

import it.unitn.carrentalapi.openapi.model.AuthenticationRequestModel;
import it.unitn.carrentalapi.openapi.model.AuthenticationResponseModel;
import it.unitn.carrentalapi.openapi.model.RegisterRequestModel;
import it.unitn.carrentalapi.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController { // TODO: Implement Delegate from swagger

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponseModel> authenticateUser(@RequestBody AuthenticationRequestModel request) {
//        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        // Generate JWT
//        return jwtService.generateToken(username);

        log.debug("Authenticating user: {}", request); // Trying to...
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseModel> register(@RequestBody RegisterRequestModel request) {
        log.debug("Registering user: {}", request); // Trying to...
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
