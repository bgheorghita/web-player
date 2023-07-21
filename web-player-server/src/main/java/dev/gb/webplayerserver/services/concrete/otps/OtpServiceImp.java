package dev.gb.webplayerserver.services.concrete.otps;

import dev.gb.webplayerserver.models.concrete.auth.Otp;
import dev.gb.webplayerserver.repositories.auth.OtpRepository;
import dev.gb.webplayerserver.utils.OtpGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OtpServiceImp implements OtpService {
    public static final int OTP_CODE_LENGTH = 6;
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
        String code = OtpGenerator.getCode(OTP_CODE_LENGTH);
        Otp otp = new Otp(code, emailIdentifier, LocalDateTime.now());
        return otpRepository.save(otp);
    }

    @Override
    public boolean validateOtp(String codeToValidate, String emailIdentifier) {
        Optional<Otp> otpOptional = findByUserEmail(emailIdentifier);
        if(otpOptional.isEmpty()){
            return false;
        }
        Otp otp = otpOptional.get();
        return otp.getCode().equals(codeToValidate) && !otp.isExpired();
    }

    @Override
    public void refreshOtp(String emailIdentifier) {
        Optional<Otp> oldOtp = findByUserEmail(emailIdentifier);
        if(oldOtp.isPresent()){
            generateOtp(emailIdentifier);
        }
    }
}
