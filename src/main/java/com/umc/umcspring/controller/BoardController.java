package com.umc.umcspring.controller;

import com.umc.umcspring.model.Board;
import com.umc.umcspring.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board")
    public List<Board> getBoards() {
        return boardService.getBoards();
    }

    @PostMapping("/board")
    public Board createBoard(@RequestBody Board board){
        return boardService.createBoard(board);
    }

    @PutMapping("/board/{id}")
    public ResponseEntity<Board> updateBoard(@PathVariable("id") Integer boardId,
                                             @RequestBody Board boardUpdate){
        return boardService.updateBoard(boardId, boardUpdate);
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteBoard(@PathVariable("id") Integer boardId){
        return boardService.deleteBoard(boardId);
    }


}
