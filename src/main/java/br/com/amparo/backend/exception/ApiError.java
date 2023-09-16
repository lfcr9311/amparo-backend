package br.com.amparo.backend.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum ApiError {

    DADOS_INVALIDOS("Dados inválidos"),
    DADO_NAO_ENCONTRADO("Dado não encontrado"),
    EMAIL_JA_EXISTE("O email informado já existe."),
    CRM_JA_EXISTE("O CRM informado já existe.");

    private final String message;

}
