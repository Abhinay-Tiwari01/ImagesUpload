package com.BGNexora.ImagesUpload.Repository;

import com.BGNexora.ImagesUpload.Entites.TheVaultEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TheVaultRepo extends JpaRepository<TheVaultEntity,Integer>
{
    List<TheVaultEntity> findBySubjectContaining(String subject);
    List<TheVaultEntity> findByFileTypeContaining(String fileType);
}
