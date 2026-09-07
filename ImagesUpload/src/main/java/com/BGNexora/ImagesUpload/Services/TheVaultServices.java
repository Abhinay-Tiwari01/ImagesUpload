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
import java.net.http.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TheVaultServices  {
    @Autowired
    private TheVaultRepo theVaultRepo;

    @Value("${storage.type}")
    private String storageType;

    public TheVaultResponse createVault(TheVaultCreateRequest createRequest) throws IOException {

        if(createRequest.getSubject() == null || createRequest.getSubject().isEmpty()){
            throw new IOException("Subject Cannot be Empty");
        }
        String fileName = null;
        byte[] fileData = null;
        String fileType = null;
        Long fileSize = null;

        if(createRequest.getFile() != null && !createRequest.getFile().isEmpty()){

            String originalFileName =createRequest.getFile().getOriginalFilename();
            String randomFileName = UUID.randomUUID().toString();

            fileName = randomFileName+"."+originalFileName;

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
                theVaultEntity.getId(),
                theVaultEntity.getSubject(),
                theVaultEntity.getMessage(),
                theVaultEntity.getFileName(),
                "/api/thevault/download/" + theVaultEntity.getId(),
                theVaultEntity.getFileType(),
                theVaultEntity.getFileSize(),
                theVaultEntity.getCreatedDate(),
                theVaultEntity.getUpdateDate()
        );
    }

    public List<TheVaultResponse> getAllVaultResponses(){
        return theVaultRepo.findAll().stream().map(
                theVaultEntity ->new TheVaultResponse(
                        theVaultEntity.getId(),
                        theVaultEntity.getSubject(),
                        theVaultEntity.getMessage(),
                        theVaultEntity.getFileName(),
                        "/api/thevault/download/" + theVaultEntity.getId(),
                        theVaultEntity.getFileType(),
                        theVaultEntity.getFileSize(),
                        theVaultEntity.getCreatedDate(),
                        theVaultEntity.getUpdateDate()
                )
        ).toList() ;
    }
    public TheVaultResponse getVaultResponseById(int id) throws IOException{
        TheVaultEntity theVaultEntity = theVaultRepo.findById(id).orElseThrow(() -> new RuntimeException("Vault not found"));
        return new TheVaultResponse(
                theVaultEntity.getId(),
                theVaultEntity.getSubject(),
                theVaultEntity.getMessage(),
                theVaultEntity.getFileName(),
                "/api/thevault/download/" + theVaultEntity.getId(),
                theVaultEntity.getFileType(),
                theVaultEntity.getFileSize(),
                theVaultEntity.getCreatedDate(),
                theVaultEntity.getUpdateDate()
        );
    }

    public TheVaultResponse updateVault(int id , TheVaultUpdateRequest updateRequest) throws IOException{
        TheVaultEntity theVaultEntity = theVaultRepo.findById(id).orElseThrow(() -> new RuntimeException("Vault not found"));

        if(updateRequest.getSubject() != null && !updateRequest.getSubject().isEmpty()){
            theVaultEntity.setSubject(updateRequest.getSubject());
        }
        if(updateRequest.getMessage() != null && !updateRequest.getMessage().isEmpty()){
            theVaultEntity.setMessage(updateRequest.getMessage());
        }

        if(updateRequest.getFile() != null && !updateRequest.getFile().isEmpty()){
            String originalFileName = updateRequest.getFile().getOriginalFilename();
            String randomFileName = UUID.randomUUID().toString();

            String fileName = randomFileName + "." + originalFileName;
            byte[] fileData = updateRequest.getFile().getBytes();
            String fileType = updateRequest.getFile().getContentType();
            Long fileSize = updateRequest.getFile().getSize();

            theVaultEntity.setFileName(fileName);
            theVaultEntity.setFileData(fileData);
            theVaultEntity.setFileType(fileType);
            theVaultEntity.setFileSize(fileSize);

            if("DATABASE".equalsIgnoreCase(storageType)){
                System.out.println("✓ File Stored in Database");
            }
            else if("LOCAL".equalsIgnoreCase(storageType))
            {
                /*
                String uniqueFileName = UUID.randomUUID() + "_" + fileName;
                Path path = Paths.get(uploadDir + uniqueFileName);
                Files.createDirectories(path.getParent());
                Files.write(path, fileData);
                theVaultEntity.setFileName(uniqueFileName);
                System.out.println("✓ File stored at: " + uploadDir + uniqueFileName);
                */
            }
        }

        TheVaultEntity updatedTheVaultEntity = theVaultRepo.save(theVaultEntity);

        return new TheVaultResponse(
                updatedTheVaultEntity.getId(),
                updatedTheVaultEntity.getSubject(),
                updatedTheVaultEntity.getMessage(),
                updatedTheVaultEntity.getFileName(),
                "/api/thevault/download/" + updatedTheVaultEntity.getId(),
                updatedTheVaultEntity.getFileType(),
                updatedTheVaultEntity.getFileSize(),
                updatedTheVaultEntity.getCreatedDate(),
                updatedTheVaultEntity.getUpdateDate()
        );
    }

    public String deleteVaultById(int id){
//        theVaultRepo.findById(id).orElseThrow(() -> new RuntimeException("Vault not found"));
        theVaultRepo.deleteById(id);
        return "Vault deleted successfully";
    }
    public String deleteAllVaults(){
        theVaultRepo.deleteAll();
        return "All Vaults deleted successfully";
    }

    public TheVaultEntity downloadFile(int id) {

            TheVaultEntity theVaultEntity = theVaultRepo.findById(id).orElseThrow(() -> new RuntimeException("Vault not found"));

            if (theVaultEntity.getFileData() == null ) {
                throw new RuntimeException("No file found for this vault");
            }
            return theVaultEntity;
    }

}
