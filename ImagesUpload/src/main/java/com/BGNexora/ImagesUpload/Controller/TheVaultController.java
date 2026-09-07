package com.BGNexora.ImagesUpload.Controller;

import com.BGNexora.ImagesUpload.DTOs.TheVaultCreateRequest;
import com.BGNexora.ImagesUpload.DTOs.TheVaultResponse;
import com.BGNexora.ImagesUpload.Services.TheVaultServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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

}
