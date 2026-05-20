package com.agrifarms.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    /**
     * Send a beautiful, responsive HTML-rich email containing a 6-digit verification OTP.
     */
    public void sendOtpEmail(String toEmail, String otpCode) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        try {
            helper.setFrom(fromEmail, "Agri Farms");
        } catch (java.io.UnsupportedEncodingException e) {
            helper.setFrom(fromEmail);
        }
        helper.setTo(toEmail);
        helper.setSubject("Verify your email with Agri Farms OTP");

        // Themed HTML template with premium organic green aesthetics
        String htmlBody = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "  <meta charset='utf-8'>"
                + "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                + "  <title>Agri Farms OTP Verification</title>"
                + "</head>"
                + "<body style=\"margin: 0; padding: 0; background-color: #f6f9f6; font-family: 'Inter', -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; color: #2d3748;\">"
                + "  <table width='100%' cellpadding='0' cellspacing='0' style='background-color: #f6f9f6; padding: 40px 20px;'>"
                + "    <tr>"
                + "      <td align='center'>"
                + "        <table width='100%' max-width='550px' style='background-color: #ffffff; border-radius: 16px; overflow: hidden; box-shadow: 0 4px 12px rgba(46, 125, 50, 0.05); max-width: 550px; border: 1px solid #e2ebd9;'>"
                + "          <!-- Header Banner -->"
                + "          <tr>"
                + "            <td style='background: linear-gradient(135deg, #1b5e20 0%, #2e7d32 100%); padding: 32px 40px; text-align: center;'>"
                + "              <h1 style='color: #ffffff; margin: 0; font-size: 26px; font-weight: 700; letter-spacing: 0.5px;'>Agri Farms</h1>"
                + "              <p style='color: #a5d6a7; margin: 6px 0 0 0; font-size: 14px; font-weight: 400;'>Your digital partner in farming</p>"
                + "            </td>"
                + "          </tr>"
                + "          <!-- Content Section -->"
                + "          <tr>"
                + "            <td style='padding: 40px 40px 30px 40px;'>"
                + "              <h2 style='color: #1b5e20; margin: 0 0 16px 0; font-size: 20px; font-weight: 600;'>Confirm Your Registration</h2>"
                + "              <p style='margin: 0 0 24px 0; font-size: 15px; line-height: 1.6; color: #4a5568;'>Thank you for choosing Agri Farms! To complete your registration and activate your account, please enter the one-time verification code below on the web app screen.</p>"
                + "              <!-- OTP Code Display -->"
                + "              <div style='background-color: #f1f8f1; border-radius: 12px; border: 1px dashed #a5d6a7; padding: 24px; text-align: center; margin-bottom: 24px;'>"
                + "                <span style='display: block; font-size: 13px; font-weight: 600; text-transform: uppercase; letter-spacing: 1px; color: #2e7d32; margin-bottom: 8px;'>Your 6-Digit OTP</span>"
                + "                <span style='font-family: monospace; font-size: 38px; font-weight: 700; letter-spacing: 6px; color: #1b5e20;'>" + otpCode + "</span>"
                + "              </div>"
                + "              <p style='margin: 0 0 24px 0; font-size: 13px; line-height: 1.5; color: #718096; text-align: center;'>This code is highly sensitive and will **expire in 5 minutes**. Do not share this OTP with anyone, including Agri Farms staff.</p>"
                + "              <hr style='border: none; border-top: 1px solid #edf2f7; margin: 24px 0;'>"
                + "              <p style='margin: 0; font-size: 12px; line-height: 1.5; color: #a0aec0;'>If you did not request this OTP or attempt to sign up on Agri Farms, please ignore this email or contact support if you suspect unauthorized access.</p>"
                + "            </td>"
                + "          </tr>"
                + "          <!-- Footer -->"
                + "          <tr>"
                + "            <td style='background-color: #fafdfa; padding: 24px 40px; text-align: center; border-top: 1px solid #f0f7f0;'>"
                + "              <p style='margin: 0; font-size: 12px; color: #718096;'>&copy; 2026 Agri Farms. All rights reserved.</p>"
                + "              <p style='margin: 4px 0 0 0; font-size: 11px; color: #a0aec0;'>Empowering agriculture through technology.</p>"
                + "            </td>"
                + "          </tr>"
                + "        </table>"
                + "      </td>"
                + "    </tr>"
                + "  </table>"
                + "</body>"
                + "</html>";

        helper.setText(htmlBody, true);
        mailSender.send(message);
        System.out.println("[SMTP] HTML OTP Email dispatched successfully to " + toEmail);
    }
}
