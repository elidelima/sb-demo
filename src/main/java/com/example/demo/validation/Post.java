package com.example.demo.validation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Post {
    @Id
    private String id;

    @NotNull(message = "name cannot be null")
    private String name;

    @Size(min = 5, message = "Description too short, must be at least 5 characters long ")
    private String description;
}
