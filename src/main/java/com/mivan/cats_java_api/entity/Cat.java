package com.mivan.cats_java_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;

import java.time.Instant;

@Entity
@Table(name = "cats")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Cat {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("cat_id")
    @Column(name = "cat_id", nullable = false, unique = true, length = 200)
    private String catId;

    @JsonProperty("url")
    @Column(nullable = false, length = 200)
    private String url;

    @JsonProperty("width")
    @Column(nullable = false)
    private Integer width;

    @JsonProperty("height")
    @Column(nullable = false)
    private Integer height;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private String breeds;

    @JsonProperty("breeds")
    @JsonRawValue
    public String getBreeds() {
        return (breeds == null || breeds.isEmpty()) ? "[]" : breeds;
    }

    @JsonProperty("api_used")
    @Column(name = "api_used", length = 200)
    private String apiUsed;

    @JsonProperty("created_at")
    @Column(name = "created_at")
    private Instant createdAt;

    @JsonProperty("updated_at")
    @Column(name = "updated_at")
    private Instant updatedAt;
}
