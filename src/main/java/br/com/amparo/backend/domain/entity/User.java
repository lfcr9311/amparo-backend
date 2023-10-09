package br.com.amparo.backend.domain.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class User {
    @Setter
    private String id;
    private String email;
    private String password;
    private String salt;
    private String name;
    private String cellphone;
    private String profilePicture;
    private boolean isAnonymous;

}
