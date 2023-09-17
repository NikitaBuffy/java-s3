package com.example.javas3.repository.comment;

import com.example.javas3.exception.CommentNotFoundException;
import com.example.javas3.model.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment getExistingComment(Long commentId) {
        return findById(commentId).orElseThrow(() -> {
            throw new CommentNotFoundException(String.format("Comment with id=%d was not found", commentId));
        });
    }
}
