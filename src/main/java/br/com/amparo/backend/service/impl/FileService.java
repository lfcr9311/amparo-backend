package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.dto.FileUploadResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.api.configuration.S3ClientFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
public class FileService {

    private AmazonS3 amazonS3;

    public Optional<FileUploadResponse> upload(MultipartFile partFile) {
        try {
            File file = File.createTempFile("tmp_" + partFile.getName(), ".tmp");
            partFile.transferTo(file);
            amazonS3.putObject(new PutObjectRequest("amparo-files", partFile.getOriginalFilename(), file)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
            URL url = amazonS3.getUrl("amparo-files", partFile.getOriginalFilename());
            file.delete();
            return Optional.of(new FileUploadResponse(url.toString()));
        } catch (IOException e) {
            log.error("Error trying to upload file to S3", e);
            return Optional.empty();
        }
    }
}
