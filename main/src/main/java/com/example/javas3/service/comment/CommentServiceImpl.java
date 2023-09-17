package com.example.javas3.service.comment;

import com.example.javas3.dto.comment.CommentDto;
import com.example.javas3.dto.comment.CommentMapper;
import com.example.javas3.dto.comment.NewCommentDto;
import com.example.javas3.model.comment.Comment;
import com.example.javas3.model.comment.CommentSort;
import com.example.javas3.model.user.User;
import com.example.javas3.repository.comment.CommentRepository;
import com.example.javas3.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;

    @Override
    public CommentDto addComment(Long userId, NewCommentDto newCommentDto) {
        User user = userService.getExistingUser(userId);
        List<String> photoUrls = new ArrayList<>();

        if (newCommentDto.getPhotos() != null) {
            // Добавление фото в сервисе Upload
        }

        Comment comment = CommentMapper.toComment(newCommentDto);
        comment.setAuthor(user);
        comment.setCreated(LocalDateTime.now());
        comment.setPhotos(photoUrls);

        Comment addedComment = commentRepository.save(comment);
        log.info("Added new comment: comment = {}", addedComment);

        return CommentMapper.toCommentDto(addedComment);
    }

    @Override
    public void deleteComment(Long commentId) {
        Comment comment = getExistingComment(commentId);
        commentRepository.delete(comment);
        log.info("Deleted comment with ID = {}", comment.getId());
    }

    @Override
    public CommentDto getComment(Long commentId) {
        return CommentMapper.toCommentDto(getExistingComment(commentId));
    }

    @Override
    public List<CommentDto> getComments(String sort, int from, int size) {
        Pageable page = createPageRequest(from, size, CommentSort.valueOf(sort));

        return commentRepository.findAll(page)
                .getContent()
                .stream()
                .map(CommentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public Comment getExistingComment(Long commentId) {
        return commentRepository.getExistingComment(commentId);
    }

    private PageRequest createPageRequest(int from, int size, CommentSort sort) {
        return PageRequest.of(from / size, size, sort.getSortValue());
    }
}
