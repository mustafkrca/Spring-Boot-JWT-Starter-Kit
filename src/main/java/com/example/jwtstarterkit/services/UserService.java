package com.example.jwtstarterkit.services;

import com.example.jwtstarterkit.utils.LoggingUtil;
import com.example.jwtstarterkit.utils.MailSenderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private MailSenderUtil mailSenderUtil;

    public String healthCheck() {
        LoggingUtil.logRequest("Sistem Sağlığı Hk.", null, null, false, null);

        String to = "example@gmail.com";
        String subject = "Sistem Sağlığı Maili";
        String htmlContent = "<h1>Merhaba</h1><p>Sistem Sağlığı iyi durumda.</p>";

        mailSenderUtil.sendHtmlMail(to, subject, htmlContent);
        return "Mail gönderildi!";
    }
}
