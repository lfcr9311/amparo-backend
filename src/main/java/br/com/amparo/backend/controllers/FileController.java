package br.com.amparo.backend.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequestMapping("/file/upload")
@PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
@AllArgsConstructor
@Tag(name = "9. FileUpload")
public class FileController {

    private FileService fileService;

    @PostMapping( consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload file", description = "Load a file and returns a url", responses = {
            @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = FileUploadResponse.class))),
            @ApiResponse(responseCode = "500", description = "Error uploading file"),
            @ApiResponse(responseCode = "403", description = "Auth error")
    })
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestPart MultipartFile file) {
        Optional<FileUploadResponse> fileUploadResponse = fileService.upload(file);
        return fileUploadResponse
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
