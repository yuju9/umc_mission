package com.umc.umcspring.service;

import com.umc.umcspring.handler.ApiRequestException;
import com.umc.umcspring.model.Board;
import com.umc.umcspring.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public List<Board> getBoards() {
        return boardRepository.findAll();
    }

    public Board createBoard(Board board) {
        return boardRepository.save(board);
    }

    public ResponseEntity<Board> updateBoard(@PathVariable Integer boardId, @RequestBody Board boardUpdate) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ApiRequestException(String.format("글이 존재하지 않습니다.")));

        board.setTitle(boardUpdate.getTitle());
        board.setContent(boardUpdate.getContent());

        Board updatedBoard = boardRepository.save(board);
        return ResponseEntity.ok(updatedBoard);
    }

    public ResponseEntity<Map<String, Boolean>> deleteBoard(int boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new ApiRequestException(String.format("해당되는 아이디(%d)의 게시물이 없습니다.", boardId)));

        boardRepository.delete(board);
        Map <String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
