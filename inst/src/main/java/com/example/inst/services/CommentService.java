package com.example.inst.services;

import com.example.inst.dto.CommentDTO;
import com.example.inst.entity.Comment;
import com.example.inst.entity.Post;
import com.example.inst.entity.User;
import com.example.inst.exeptions.PostNotFoundException;
import com.example.inst.repository.CommentRepository;
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
public class CommentService {
    public static final Logger LOGGER = LoggerFactory.getLogger(CommentService.class);

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }


    public Comment savComment(Long postId, CommentDTO commentDTO, Principal principal){
        User user = getUserByPrincipal(principal);
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUserId(user.getId());
        comment.setUsername(user.getUsername());
        comment.setMessage(commentDTO.getMessage());

        LOGGER.info("Saving comment for Post: "+ post.getId());
        return commentRepository.save(comment);
    }


    public List<Comment> getAllCommentsForPost(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found"));

        List<Comment> comments = commentRepository.findAllByPost(post);
        return comments;

    }


    public void deleteComment(Long commentId){
        Optional<Comment> comment = commentRepository.findById(commentId);
        comment.ifPresent(commentRepository::delete);
    }

    private User getUserByPrincipal(Principal principal) {
        String username = principal.getName();
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
