package br.com.amparo.backend.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String email;
    private String password;
    private String salt;
    private String name;
    private String cellphone;
    private String profilePicture;
    private boolean isAnonymous;

}
