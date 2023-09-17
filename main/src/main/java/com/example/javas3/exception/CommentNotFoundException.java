package com.example.javas3.exception;

import javax.persistence.EntityNotFoundException;

public class CommentNotFoundException extends EntityNotFoundException {

    public CommentNotFoundException(String message) {
        super(message);
    }
}
