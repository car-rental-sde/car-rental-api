package it.unitn.carrentalapi.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(columnDefinition = "TEXT")
    private String value;
    private LocalDateTime created;

    public TokenEntity(String value) {
        this.value = value;
        created = LocalDateTime.now();
    }
}
