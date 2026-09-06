package com.BGNexora.ImagesUpload.DTOs;

import org.springframework.web.multipart.MultipartFile;
//to server with file
public class TheVaultCreateRequest {
    private String subject;
    private String message;
    private MultipartFile file;

    public TheVaultCreateRequest(String subject, String message, MultipartFile file) {
        this.subject = subject;
        this.message = message;
        this.file = file;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }
}
