package com.viraloab.notificationService.utils;

import com.viraloab.notificationService.pojo.NotificationEventData;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GmailHelper {

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendMail(String subject, String message, String from, String to) {
        boolean isSendSuccess = false;
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(message, true);
            messageHelper.setFrom(from);
            mailSender.send(mimeMessage);
            isSendSuccess = true;
            log.info("[GmailService] Mail request processed successfully");
        } catch (Exception ex) {
            log.info("[GmailService] Error while execution request - {}", ex);
            isSendSuccess = false;
        }
        return isSendSuccess;
    }

    public String generateBody(NotificationEventData data) {
        String htmlContent = """
        <html>
        <head>
            <style>
                body {
                    font-family: Arial, sans-serif;
                    background-color: #f4f4f4;
                    padding: 20px;
                }
                .card {
                    background-color: #fff;
                    border-radius: 10px;
                    padding: 20px;
                    max-width: 600px;
                    margin: auto;
                    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
                }
                .card h2 {
                    color: #333;
                }
                .info {
                    margin: 10px 0;
                    font-size: 16px;
                }
                .label {
                    font-weight: bold;
                    color: #555;
                }
                .footer {
                    margin-top: 30px;
                    font-size: 14px;
                    color: #888;
                    text-align: center;
                }
            </style>
        </head>
        <body>
            <div class="card">
                <h2>📬 New Contact Request</h2>
                <div class="info"><span class="label">Name:</span> %s</div>
                <div class="info"><span class="label">Email:</span> %s</div>
                <div class="info"><span class="label">Company:</span> %s</div>
                <div class="info"><span class="label">Phone:</span> %d</div>
                <div class="info"><span class="label">Message:</span><br>%s</div>
                <div class="footer">This email was sent from your website contact form.</div>
            </div>
        </body>
        </html>
    """.formatted(data.getName(), data.getEmail(), data.getCompany(),
                data.getPhone(), data.getMessage());
        return htmlContent;
    }

    public String generateConfirmationBody(String clientName) {
        String htmlContent = """
        <html>
        <head>
            <style>
                body {
                    font-family: 'Segoe UI', sans-serif;
                    background-color: #f9f9f9;
                    padding: 20px;
                }
                .container {
                    max-width: 600px;
                    margin: auto;
                    background-color: #ffffff;
                    padding: 30px;
                    border-radius: 10px;
                    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
                }
                h2 {
                    color: #2c3e50;
                }
                p {
                    color: #555;
                    font-size: 16px;
                    line-height: 1.6;
                }
                .footer {
                    margin-top: 30px;
                    font-size: 13px;
                    color: #999;
                    text-align: center;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <h2>Hi %s,</h2>
                <p>
                    We’ve successfully received your message and one of our team members will get back to you as soon as possible.
                </p>
                <p>
                    We appreciate your interest in our services and look forward to assisting you.
                </p>
                <p>Warm regards,<br>
                <strong>VIRALOAB</strong></p>

                <div class="footer">
                    This is an automated message. Please do not reply directly to this email.
                </div>
            </div>
        </body>
        </html>
    """.formatted(clientName);
        return htmlContent;
    }

    public String generateErrorBody() {
        String htmlcontent = """
            <html>
            <head>
                <style>
                    .alert {
                        font-family: Arial, sans-serif;
                        background-color: #ffe5e5;
                        border: 1px solid #ff4d4d;
                        padding: 20px;
                        border-radius: 8px;
                        color: #d8000c;
                    }
                    .footer {
                        margin-top: 20px;
                        font-size: 13px;
                        color: #777;
                    }
                </style>
            </head>
            <body>
                <div class="alert">
                    <p>
                        The Notif mailer failed to send an email.<br>
                        Please check the <strong>database logs</strong> and investigate the issue immediately.
                    </p>
                    <div class="footer">
                        This is an automated alert from the Notification Service.
                    </div>
                </div>
            </body>
            </html>
        """;
        return htmlcontent;
    }
}
