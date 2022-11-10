package com.umc.umcspring.controller;

import com.umc.umcspring.model.Board;
import com.umc.umcspring.service.BoardService;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("")
    public List<Board> getBoards() {
        return boardService.getBoards();
    }

    @PostMapping("")
    public Board createBoard(@RequestBody Board board){
        return boardService.createBoard(board);
    }



    @PutMapping("/{id}")
    public Board updateBoard(@PathVariable("id") Integer boardId,
                                             @RequestBody Board boardUpdate){
        return boardService.updateBoard(boardId, boardUpdate);
    }



    @DeleteMapping("/{id}")
    public void deleteBoard(@PathVariable("id") Integer boardId){
        boardService.deleteBoard(boardId);
    }


}
