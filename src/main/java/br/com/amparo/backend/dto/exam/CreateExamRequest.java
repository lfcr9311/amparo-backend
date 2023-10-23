package br.com.amparo.backend.dto.exam;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateExamRequest (String description, @NotNull LocalDateTime exam_date,@NotNull Boolean is_done
        , String file, String image){

}

