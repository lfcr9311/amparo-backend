package br.com.amparo.backend.controllers;

import br.com.amparo.backend.dto.FileUploadResponse;
import br.com.amparo.backend.service.impl.FileService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@Tag(name = "8. Dosage")
@RequestMapping("/file/upload")
@PreAuthorize("hasRole('PATIENT') or hasRole('DOCTOR')")
@AllArgsConstructor
public class FileController {

    private FileService fileService;

    @PostMapping
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestPart MultipartFile file) {
        Optional<FileUploadResponse> fileUploadResponse = fileService.upload(file);
        return fileUploadResponse
                .map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

}
