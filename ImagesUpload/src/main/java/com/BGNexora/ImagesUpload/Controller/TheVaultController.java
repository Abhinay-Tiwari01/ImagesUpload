package com.BGNexora.ImagesUpload.Controller;

import com.BGNexora.ImagesUpload.DTOs.TheVaultCreateRequest;
import com.BGNexora.ImagesUpload.DTOs.TheVaultResponse;
import com.BGNexora.ImagesUpload.DTOs.TheVaultUpdateRequest;
import com.BGNexora.ImagesUpload.Entites.TheVaultEntity;
import com.BGNexora.ImagesUpload.Services.TheVaultServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TheVaultController {

    @Autowired
    TheVaultServices theVaultServices;

    @PostMapping("/create")
    public ResponseEntity<TheVaultResponse> createVault(@ModelAttribute TheVaultCreateRequest createRequest){
        TheVaultResponse response = null;
        try {
            response = theVaultServices.createVault(createRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<TheVaultResponse>> findAll(){
        List<TheVaultResponse> responseAll = theVaultServices.getAllVaultResponses();
        return ResponseEntity.status(HttpStatus.OK).body(responseAll);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<TheVaultResponse> findByid(@PathVariable int id) throws IOException
            {
                TheVaultResponse findResponse = null;
                try {
                    findResponse = theVaultServices.getVaultResponseById(id);
                    return ResponseEntity.status(HttpStatus.OK).body(findResponse);
                } catch (IOException e) {
//                    throw new RuntimeException(e);
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TheVaultResponse> updateVault(@PathVariable int id, @ModelAttribute TheVaultUpdateRequest updateRequest)throws IOException {
        TheVaultResponse updatedResponse = null;
        try {
            updatedResponse = theVaultServices.updateVault(id, updateRequest);
            return ResponseEntity.status(HttpStatus.OK).body(updatedResponse);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id)  {
        try {
                theVaultServices.deleteVaultById(id);
                return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAll() {
        try {
            theVaultServices.deleteAllVaults();
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable int id) {
        try {
            TheVaultEntity theVaultEntity = theVaultServices.downloadFile(id);
            return ResponseEntity.status(HttpStatus.OK).body(theVaultEntity);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found: " + e.getMessage());
        }
    }
}
