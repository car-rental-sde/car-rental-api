package it.unitn.carrentalapi.facade.impl;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import it.unitn.carrentalapi.facade.MapApiFacade;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.Scanner;

@Slf4j
@Component
public class MapApiFacadeImpl implements MapApiFacade {
    private static final String API_KEY = ""; // Replace with your Google Maps API key
    // Sample distance: "46.067039090624654,11.15040096289398", "46.068824845398694,11.115242324953396"

    @Override
    public Optional<Integer> getDistanceInMeters(String origin, String destination) {
        if (StringUtils.isBlank(API_KEY)) {
            log.warn("Google Maps API key is not set. Returning a default distance of 1000 meters.");
            return Optional.of(1000);
        }

        try {
            String response = getDistanceFromGoogleMaps(origin, destination);
            JsonObject jsonObject = JsonParser.parseString(response).getAsJsonObject();

            String status = jsonObject.getAsJsonArray("rows")
                    .get(0).getAsJsonObject()
                    .getAsJsonArray("elements")
                    .get(0).getAsJsonObject()
                    .get("status").getAsString();

            if ("OK".equals(status)) {
                int distance = jsonObject.getAsJsonArray("rows")
                        .get(0).getAsJsonObject()
                        .getAsJsonArray("elements")
                        .get(0).getAsJsonObject()
                        .getAsJsonObject("distance")
                        .get("value").getAsInt();

                return Optional.of(distance);
            } else {
                System.out.println("No valid route found. Status: " + status);
                return Optional.empty();
            }
        } catch (Exception e) {
            log.error("Error occurred while getting distance from Google Maps API", e);
            return Optional.empty();
        }
    }

    private String getDistanceFromGoogleMaps(String origin, String destination) throws IOException {
        String urlString = String.format(
                "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s&destinations=%s&key=%s",
                origin, destination, API_KEY);

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (Scanner scanner = new Scanner(conn.getInputStream())) {
            StringBuilder response = new StringBuilder();
            while (scanner.hasNext()) {
                response.append(scanner.nextLine());
            }
            return response.toString();
        }
    }
}
