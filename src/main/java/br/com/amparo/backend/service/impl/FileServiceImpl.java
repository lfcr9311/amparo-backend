package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.service.FileService;

import java.io.File;
import java.nio.file.Files;
import java.util.Base64;

public class FileServiceImpl implements FileService {
    @Override
    public String convertFileToBase64(File file) throws Exception {
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public String decodeBase64(String base64) throws Exception {
        try {
            return new String(Base64.getDecoder().decode(base64));
        } catch (Exception e) {
            return null;
        }
    }
}