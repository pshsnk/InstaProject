package com.example.instaprjct.repository;

import com.example.instaprjct.entity.ImageModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageModel, Long> {

    Optional<ImageModel> findByUserId(Long UserId);

    Optional<ImageModel> findByPostId(Long PostId);



}
