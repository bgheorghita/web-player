package dev.gb.webplayerserver.services.concrete.otps.validators;

import dev.gb.webplayerserver.exceptions.InvalidOtpException;
import dev.gb.webplayerserver.services.concrete.otps.OtpService;
import org.springframework.stereotype.Service;

@Service
public class OtpValidatorServiceImp implements OtpValidatorService {
    private final OtpService otpService;

    public OtpValidatorServiceImp(OtpService otpService) {
        this.otpService = otpService;
    }

    @Override
    public void validate(String code, String userEmail) {
        boolean validOtp = otpService.validateOtp(code, userEmail);
        if(!validOtp){
            throw new InvalidOtpException("The OTP is not valid.");
        }
    }
}
