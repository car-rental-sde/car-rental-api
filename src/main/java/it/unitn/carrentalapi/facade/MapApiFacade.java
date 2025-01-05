package it.unitn.carrentalapi.facade;

import java.util.Optional;

public interface MapApiFacade {
    Optional<Integer> getDistanceInMeters(String origin, String destination);
}
