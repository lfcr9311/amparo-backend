package br.com.amparo.backend.web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity (name = "Patient")
@Table (name = "patient")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode (of = "uuid")

public class Patient {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private UUID id;
    private String cpf;

}
