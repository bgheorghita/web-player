package dev.gb.webplayerauthorizationserver.services.otps.validators;

import dev.gb.webplayerauthorizationserver.exceptions.InvalidOtpException;
import dev.gb.webplayerauthorizationserver.services.otps.OtpService;
import org.springframework.stereotype.Service;

@Service
public class OtpValidatorServiceImp implements OtpValidatorService {
    private final OtpService otpService;

    public OtpValidatorServiceImp(OtpService otpService) {
        this.otpService = otpService;
    }

    @Override
    public void validate(String userEmail, String code) {
        boolean validOtp = otpService.validateOtp(userEmail, code);
        if(!validOtp){
            throw new InvalidOtpException("The OTP is not valid.");
        }
    }
}
