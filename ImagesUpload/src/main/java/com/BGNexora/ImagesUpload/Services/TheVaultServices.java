package com.BGNexora.ImagesUpload.Services;

import com.BGNexora.ImagesUpload.DTOs.TheVaultCreateRequest;
import com.BGNexora.ImagesUpload.DTOs.TheVaultResponse;
import com.BGNexora.ImagesUpload.DTOs.TheVaultUpdateRequest;
import com.BGNexora.ImagesUpload.Entites.TheVaultEntity;
import com.BGNexora.ImagesUpload.Repository.TheVaultRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TheVaultServices  {
    @Autowired
    private TheVaultRepo theVaultRepo;

    @Value("${storage.type}")
    private String storageType;

    public TheVaultResponse createVault(TheVaultCreateRequest createRequest) throws IOException {

        if(createRequest.getSubject() == null | createRequest.getSubject().isEmpty()){
            throw new IOException("Subject Cannot be Empty");
        }
        String fileName = null;
        byte[] fileData = null;
        String fileType = null;
        Long fileSize = null;

        if(createRequest.getFile() != null && createRequest.getFile().isEmpty()){
            fileName = createRequest.getFile().getOriginalFilename();
            fileData = createRequest.getFile().getBytes();
            fileType = createRequest.getFile().getContentType();
            fileSize = createRequest.getFile().getSize();
            if("DATABASE".equalsIgnoreCase(storageType)){
                System.out.println("File Stored in Database");
            }
            else if("LOCAL".equalsIgnoreCase(storageType))
            {
                 /*
                String uniqueFileName = UUID.randomUUID() + "_" + fileName;
                Path path = Paths.get(uploadDir + uniqueFileName);
                Files.createDirectories(path.getParent());
                Files.write(path, fileData);
                fileName = uniqueFileName;
                System.out.println("✓ File stored at: " + uploadDir + uniqueFileName);
                */
            }
        }
        TheVaultEntity theVault = new TheVaultEntity();
        theVault.setSubject(createRequest.getSubject());
        theVault.setMessage(createRequest.getMessage());
        theVault.setFileName(fileName);
        theVault.setFileData(fileData);
        theVault.setFileType(fileType);
        theVault.setFileSize(fileSize);
        TheVaultEntity theVaultEntity = theVaultRepo.save(theVault);
        return new TheVaultResponse(
                theVault.getId(),
                theVault.getSubject(),
                theVault.getMessage(),
                theVault.getFileName(),
                "/api/thevault/download/" + theVault.getId(),
                theVault.getFileType(),
                theVault.getFileSize(),
                theVault.getCreatedDate(),
                theVault.getUpdateDate()
        );
    }
}
