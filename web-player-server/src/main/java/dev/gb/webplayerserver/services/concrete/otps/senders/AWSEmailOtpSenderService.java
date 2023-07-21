package dev.gb.webplayerserver.services.concrete.otps.senders;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import dev.gb.webplayerserver.exceptions.ServiceUnavailable;
import dev.gb.webplayerserver.models.concrete.auth.Email;
import dev.gb.webplayerserver.models.concrete.auth.Otp;
import dev.gb.webplayerserver.services.concrete.otps.OtpService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AWSEmailOtpSenderService implements EmailOtpSenderService {
    private final Logger logger = LoggerFactory.getLogger(AWSEmailOtpSenderService.class);
    private final OtpService otpService;

    @Value("${otp.email.sender}")
    private String otpEmailSender;

    public AWSEmailOtpSenderService(OtpService otpService) {
        this.otpService = otpService;
    }

    @Override
    public void sendOtp(String emailIdentifier) {
        String accessKey = System.getenv("AWS_ACCESS_KEY");
        String secretKey = System.getenv("AWS_SECRET_KEY");
        Email email = createEmail(emailIdentifier);

        try {
            AmazonSimpleEmailService client =
                    AmazonSimpleEmailServiceClientBuilder.standard()
                            .withRegion(Regions.US_EAST_1)
                            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                            .build();

            SendEmailRequest request = new SendEmailRequest()
                    .withDestination(
                            new Destination()
                                    .withToAddresses(emailIdentifier))
                    .withMessage(new Message()
                            .withBody(new Body()
                                    .withHtml(new Content()
                                            .withCharset("UTF-8").withData(email.htmlBody())))
                            .withSubject(new Content()
                                    .withCharset("UTF-8").withData(email.subject())))
                    .withSource(email.senderEmail());

            client.sendEmail(request);

            logger.debug("OTP to '" + emailIdentifier + "' has been sent.");
        } catch (Exception ex) {
            logger.debug("OTP to '" + emailIdentifier + "' has not been sent. Cause: " + ex.getMessage());
            throw new ServiceUnavailable("An error has occurred while trying to send the verification OTP through email.");
        }
    }

    private Email createEmail(String email) {
        return new Email(
                createAndReturnSenderEmail(),
                createAndReturnEmailSubject(),
                createAndReturnHtmlBody(email)
        );
    }

    private String createAndReturnSenderEmail(){
        return otpEmailSender;
    }

    private String createAndReturnEmailSubject(){
        return "WEB-PLAYER Registration";
    }

    private String createAndReturnHtmlBody(String email){
        Otp otp = otpService.generateOtp(email);
        return  "<h1>One Time Password</h1>" +
                "<h3>This is your One Time Password: " + otp.getCode() + "</h3>" +
                "<p>It will expire in " + (Otp.EXPIRES_IN_SECONDS / 60) + " minutes.</p>";
    }
}
