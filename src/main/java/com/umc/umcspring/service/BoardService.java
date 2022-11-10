package com.umc.umcspring.service;

import com.umc.umcspring.handler.ApiRequestException;
import com.umc.umcspring.model.Board;
import com.umc.umcspring.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
@Transactional
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public List<Board> getBoards() {
        return boardRepository.findAll();
    }

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }


    public Board updateBoard(@PathVariable Integer boardId, @RequestBody Board boardUpdate) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ApiRequestException(String.format("글이 존재하지 않습니다.")));

        board.setTitle(boardUpdate.getTitle());
        board.setContent(boardUpdate.getContent());

        return boardRepository.save(board);
    }


    public void deleteBoard(int boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ApiRequestException(String.format("해당되는 아이디(%d)의 게시물이 없습니다.", boardId)));

        boardRepository.delete(board);
    }

}
