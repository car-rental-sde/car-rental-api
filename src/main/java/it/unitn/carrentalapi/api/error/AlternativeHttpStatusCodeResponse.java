package it.unitn.carrentalapi.api.error;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpStatusCodeException;

@Getter
public class AlternativeHttpStatusCodeResponse extends HttpStatusCodeException {

    private final Object body;

    public AlternativeHttpStatusCodeResponse(HttpStatusCode statusCode, Object body) {
        super(statusCode);
        this.body = body;
    }
}
