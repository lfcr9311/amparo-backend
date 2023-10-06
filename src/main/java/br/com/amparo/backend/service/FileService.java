package br.com.amparo.backend.service;

import java.io.File;
import java.time.LocalDateTime;
public interface FileService {
    String convertFileToBase64(File file) throws Exception;

    String decodeBase64(String base64) throws Exception;

}
