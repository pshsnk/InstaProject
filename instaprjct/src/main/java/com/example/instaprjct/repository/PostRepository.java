package com.example.instaprjct.repository;

import com.example.instaprjct.entity.Post;
import com.example.instaprjct.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUserOrderByCreatedDateDesc(User user);

    List<Post> findAllByOrderByCreatedDate();

    Optional<Post> findPostByIdAndUser(Long id, User user);
}
