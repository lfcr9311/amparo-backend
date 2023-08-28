package br.com.amparo.backend.web.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;
@Entity(name = "Doctor")
@Table(name = "doctor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "uuid")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private int crm;
    private String uf;

}
