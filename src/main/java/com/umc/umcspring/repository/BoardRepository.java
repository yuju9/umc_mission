package com.umc.umcspring.repository;

import com.umc.umcspring.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {

//    public static List<Board> boards;
//
//    static {
//        boards = new ArrayList<>();
//        boards.add(new Board(1, "A", "B"));
//        boards.add(new Board(2, "C", "D"));
//        boards.add(new Board(3, "E", "F"));
//        boards.add(new Board(4, "G", "H"));
//        boards.add(new Board(5, "I", "J"));
//    }
//
//    public static List<Board> getBoards(){
//        return boards;
//    }
//
//    public static Board createBoard(Board board) {
//        boards.add(board);
//        return board;
//    }
//
//    public static void updateBoard(Board board) {
//        boards.stream()
//                .filter(item->item.getBoardId().equals(board.getBoardId()))
//                .findAny()
//                .orElse(new Board(0, "", ""))
//                .setContent(board.getContent());
//    }

}
