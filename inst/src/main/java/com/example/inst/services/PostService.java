package com.example.inst.services;

import com.example.inst.dto.PostDTO;
import com.example.inst.entity.ImageModel;
import com.example.inst.entity.Post;
import com.example.inst.entity.User;
import com.example.inst.exeptions.PostNotFoundException;
import com.example.inst.repository.ImageRepository;
import com.example.inst.repository.PostRepository;
import com.example.inst.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    public static final Logger LOGGER = LoggerFactory.getLogger(PostService.class);

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    @Autowired
    public PostService(PostRepository postRepository, UserRepository userRepository, ImageRepository imageRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }

    public Post createPost(PostDTO postDTO, Principal principal) {
        User user = getUserByPrincipal(principal);
        Post post = new Post();
        post.setUser(user);
        post.setCaption(postDTO.getCaption());
        post.setLocation(postDTO.getLocation());
        post.setLocation(postDTO.getLocation());
        post.setTitle(postDTO.getTitle());
        post.setLikes(0);

        LOGGER.info("Saving post for user: " + user.getUsername());
        return postRepository.save(post);
    }


    public List<Post> getAllPosts() {
        return postRepository.findAllByOrderByCreatedDate();
    }


    public Post getPostById(Long id, Principal principal) {
        User user = getUserByPrincipal(principal);
        return postRepository.findPostByIdAndUser(id, user)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));
    }


    public List<Post> getAllPostsForUser(Principal principal) {
        User user = getUserByPrincipal(principal);
        return postRepository.findAllByUserOrderByCreatedDateDesc(user);
    }


    public Post likePost(Long postId, String username) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        Optional<String> userLiked = post.getLikedUsers()
                .stream().filter(u -> u.equals(username)).findAny();

        if (userLiked.isPresent()) {
            post.setLikes(post.getLikes() - 1);
            post.getLikedUsers().remove(username);
        } else {
            post.setLikes(post.getLikes() + 1);
            post.getLikedUsers().add(username);
        }

        return postRepository.save(post);
    }


    public void deletePost(Long postId, Principal principal){
        Post post = getPostById(postId, principal);
        Optional<ImageModel> imageModel = imageRepository.findByPostId(postId);
        postRepository.delete(post);
        imageModel.ifPresent(imageRepository::delete);

    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
