package org.pedrojaraujo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import java.time.Instant;

@Entity
public class IdempotencyRecord extends PanacheEntityBase {

    @Id
    @Column(nullable = false, unique = true)
    public String key;

    @Column(nullable = false)
    public String requestPath;

    @Column(nullable = false)
    public Instant createdAt;

    @Column(columnDefinition = "TEXT")
    public String responseBody;

    @Column
    public Integer responseStatus;


    // Cria um novo registro
    public static IdempotencyRecord create(String key, String path) {
        IdempotencyRecord record = new IdempotencyRecord();
        record.key = key;
        record.requestPath = path;
        record.createdAt = Instant.now();
        return record;
    }
}