package br.com.amparo.backend.service.impl;

import br.com.amparo.backend.domain.entity.Doctor;
import br.com.amparo.backend.exception.ApiErrorException;
import br.com.amparo.backend.service.DoctorService;
import br.com.amparo.backend.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static br.com.amparo.backend.exception.ApiError.CRM_JA_EXISTE;
import static br.com.amparo.backend.exception.ApiError.EMAIL_JA_EXISTE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final UserService userService;

    @Override
    @Transactional
    public Doctor saveNewDoctor(Doctor doctorRegistrationData) {
        if (userService.alreadyExistsEmail(doctorRegistrationData.getEmail())) {
            throw new ApiErrorException(EMAIL_JA_EXISTE.getMessage(), BAD_REQUEST);
        }
        if (alreadyExistsCrm(doctorRegistrationData.getCrm())) {
            throw new ApiErrorException(CRM_JA_EXISTE.getMessage(), BAD_REQUEST);
        }

        return doctorRegistrationData;
    }

    @Override
    public boolean alreadyExistsCrm(String crm) {
        return false;
    }
}
