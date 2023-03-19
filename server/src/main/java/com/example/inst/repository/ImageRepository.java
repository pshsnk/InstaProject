package com.example.inst.repository;


import com.example.inst.entity.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageModel, Long> {

    Optional<ImageModel> findByUserId(Long UserId);

    Optional<ImageModel> findByPostId(Long PostId);



}
