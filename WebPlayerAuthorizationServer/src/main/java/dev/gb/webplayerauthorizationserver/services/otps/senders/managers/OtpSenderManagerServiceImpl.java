package dev.gb.webplayerauthorizationserver.services.otps.senders.managers;

import dev.gb.webplayerauthorizationserver.exceptions.ServiceUnavailableException;
import dev.gb.webplayerauthorizationserver.services.otps.senders.OtpSenderService;
import dev.gb.webplayerauthorizationserver.utils.ObfuscatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class OtpSenderManagerServiceImpl implements OtpSenderManagerService {
    private final static Logger logger = LoggerFactory.getLogger(OtpSenderManagerServiceImpl.class);
    private final OtpSenderService otpSenderService;
    @Value("${otp.is-activated:false}")
    private boolean isOtpServiceActivated;

    public OtpSenderManagerServiceImpl(OtpSenderService otpSenderService) {
        this.otpSenderService = otpSenderService;
    }

    @Override
    public void sendOtp(String emailIdentifier) {
        if(isOtpServiceNotActivated()) {
            String obfuscatedEmailAddress = ObfuscatorUtil.obfuscateEmailAddress(emailIdentifier);
            logger.warn("Otp service is not activated! Email with OTP was not sent to " + obfuscatedEmailAddress);
            throw new ServiceUnavailableException("OTP could not be sent through email.");
        }

        otpSenderService.sendOtp(emailIdentifier);
    }

    private boolean isOtpServiceNotActivated(){
        return !isOtpServiceActivated;
    }
}
