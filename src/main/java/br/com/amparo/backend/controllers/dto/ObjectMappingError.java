package br.com.amparo.backend.controllers.dto;

import br.com.amparo.backend.configuration.security.ValidationError;

import java.util.List;

public record ObjectMappingError(List<ValidationError> errors){
}
