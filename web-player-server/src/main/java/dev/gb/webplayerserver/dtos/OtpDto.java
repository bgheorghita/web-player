package dev.gb.webplayerserver.dtos;

public class OtpDto {
    private String code;
    private String userEmail;

    private OtpDto(String code, String userEmail) {
        this.code = code;
        this.userEmail = userEmail;
    }

    public OtpDto withUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public OtpDto withCode(String code) {
        this.code = code;
        return this;
    }

    public OtpDto buildOtpDto() {
        return new OtpDto(userEmail, code);
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getCode() {
        return code;
    }
}
