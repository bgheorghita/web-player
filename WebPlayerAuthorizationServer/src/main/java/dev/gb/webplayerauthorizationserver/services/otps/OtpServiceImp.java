package dev.gb.webplayerauthorizationserver.services.otps;

import dev.gb.webplayerauthorizationserver.models.otps.Otp;
import dev.gb.webplayerauthorizationserver.repositories.OtpRepository;
import dev.gb.webplayerauthorizationserver.utils.OtpGeneratorUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OtpServiceImp implements OtpService {
    @Value("${otp.code-length:6}")
    private int otpCodeLength;

    @Value("${otp.expires-in-seconds:300}")
     int otpExpiresInSeconds;
    private final OtpRepository otpRepository;

    public OtpServiceImp(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    @Override
    public Optional<Otp> findByUserEmail(String emailIdentifier) {
        return otpRepository.findByUserEmail(emailIdentifier);
    }

    @Override
    public Otp generateOtp(String emailIdentifier) {
        String code = OtpGeneratorUtil.getCode(otpCodeLength);
        Otp otp = new Otp();
        otp.setCode(code);
        otp.setUserEmail(emailIdentifier);
        otp.setGeneratedAt(LocalDateTime.now());
        return otpRepository.save(otp);
    }

    @Override
    public boolean validateOtp(String emailIdentifier, String codeToValidate) {
        Optional<Otp> otpOptional = findByUserEmail(emailIdentifier);
        if(otpOptional.isEmpty()){
            return false;
        }
        Otp otp = otpOptional.get();

        LocalDateTime expirationDate = otp.getGeneratedAt().plusSeconds(otpExpiresInSeconds);
        boolean isCodeExpired = LocalDateTime.now().isAfter(expirationDate);

        return otp.getCode().equals(codeToValidate) && !isCodeExpired;
    }

    @Override
    public void refreshOtp(String emailIdentifier) {
        Optional<Otp> oldOtp = findByUserEmail(emailIdentifier);
        if(oldOtp.isPresent()){
            generateOtp(emailIdentifier);
        }
    }
}
