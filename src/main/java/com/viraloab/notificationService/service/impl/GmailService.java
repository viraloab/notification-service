package com.viraloab.notificationService.service.impl;

import com.viraloab.notificationService.pojo.NotificationEventData;
import com.viraloab.notificationService.service.MessageService;
import com.viraloab.notificationService.utils.GmailHelper;
import com.viraloab.notificationService.utils.VariableConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class GmailService implements MessageService {

    @Value("${gmail.service.host-email}")
    private String from;

    @Autowired
    private GmailHelper gmailHelper;

    @Override
    public void send(NotificationEventData data) {
        boolean isSendSuccess =
                gmailHelper.sendMail(
                        VariableConstants.GMAIL_SUBJECT1
                        , gmailHelper.generateBody(data)
                        , from, from
                );
        if (isSendSuccess) {
            sendConfirmationMessage(data);
        } else {
            sendErrorMessage();
        }
    }

    @Override
    public void sendConfirmationMessage(NotificationEventData data) {
        String message = gmailHelper.generateConfirmationBody(data.getName());
        boolean isSendSuccess = gmailHelper.sendMail(
                VariableConstants.GMAIL_SUBJECT2
                , message, from, data.getEmail());
    }

    @Override
    public void sendErrorMessage() {
        String message = gmailHelper.generateErrorBody();
        boolean isSendSuccess = gmailHelper.sendMail(
                VariableConstants.GMAIL_ERROR1
                , message, from, from);
    }
}
