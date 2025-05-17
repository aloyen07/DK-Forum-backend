package com.dkforum.core.service.mail;

public class MailDetails {

    private String subject;
    private String content;
    private String[] recipients;

    private String attachment;


    public MailDetails subject(String subject){
        this.subject = subject;
        return this;
    }

    public MailDetails content(String content){
        this.content = content;
        return this;
    }

    public MailDetails recipient(String... recipient){
        this.recipients = recipient;
        return this;
    }

    public MailDetails attachment(String attachment){
        this.attachment = attachment;
        return this;
    }

    public String getAttachment() {
        return attachment;
    }

    public String getSubject() {
        return subject;
    }

    public String[] getRecipients() {
        return recipients;
    }

    public String getContent() {
        return content;
    }
}
