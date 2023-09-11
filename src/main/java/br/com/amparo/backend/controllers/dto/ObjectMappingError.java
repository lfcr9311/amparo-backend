package br.com.amparo.backend.controllers.dto;

import java.util.List;

public record ObjectMappingError(List<FieldMappedError> errors){
}
