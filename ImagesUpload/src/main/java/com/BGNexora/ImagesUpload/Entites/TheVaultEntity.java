package com.BGNexora.ImagesUpload.Entites;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "TheVault")
public class TheVaultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false)
    private String subject;
    @Lob
    @Column(name = "message")
    private String message;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_type")
    private String fileType;
    @Column(name = "file_size")
    private long fileSize;

    @Lob
    @Column(name = "file_data")
    private byte[] fileData;

//    For local file storage approach
//    @Column(name = "file_path")
//    private String filePath;

    @Column(name = "create_date")
    private LocalDateTime createdDate;
    @Column(name = "update_date")
    private LocalDateTime updateDate;

    public TheVaultEntity() {

    }

    @PrePersist
    protected void onCreate(){
        createdDate = LocalDateTime.now();
        updateDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        updateDate = LocalDateTime.now();
    }

    public TheVaultEntity(String subject, String message, String fileName, String fileType, long fileSize, byte[] fileData, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.subject = subject;
        this.message = message;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.fileData = fileData;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
        this.updateDate = updateDate;
    }
}
