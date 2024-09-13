package org.example.expert.domain.comment.service;

import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.comment.repository.CommentRepository;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentAdminServiceTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private TodoRepository todoRepository;
    @InjectMocks
    private CommentAdminService commentAdminService;

    @Test
    public void delete_comment_of_another_user() {
        // given
        long commentId = 1L;

        // Use doNothing for void methods
        doNothing().when(commentRepository).deleteById(anyLong());

        // when
        commentAdminService.deleteComment(commentId);

        // then
        verify(commentRepository, times(1)).deleteById(commentId);
    }


}
