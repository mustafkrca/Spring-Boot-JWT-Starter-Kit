package com.example.jwtstarterkit.dtos;

public class EmailDetailDTO {
    private String to;
    private String subject;
    private String htmlContent;

    public EmailDetailDTO(String to, String subject, String htmlContent) {
        this.to = to;
        this.subject = subject;
        this.htmlContent = htmlContent;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getHtmlContent() {
        return htmlContent;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }
}
