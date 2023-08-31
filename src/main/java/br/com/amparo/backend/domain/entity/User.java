package br.com.amparo.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", schema = "public")
public class User implements Serializable {

    public static final long serialVersionUID = -375473501761249725L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "data_nascimento", nullable = false)
    private String dataNascimento;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "confirm_Password", nullable = false)
    private String confirm_Password;

    @Column(name= "is_Anonymous", nullable = false)
    private Boolean is_Anonymous;

}
