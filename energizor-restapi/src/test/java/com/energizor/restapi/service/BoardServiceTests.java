package com.energizor.restapi.service;

import com.energizor.restapi.board.dto.BoardDTO;
import com.energizor.restapi.board.dto.InterestBoardDTO;
import com.energizor.restapi.board.service.BoardService;
import com.energizor.restapi.users.dto.UserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

//    @Test
//    public void testRegister() {
//        BoardDTO boardDTO=BoardDTO.builder()
//                .boardCode(8).build();
//
//        UserDTO userDTO=UserDTO.builder()
//                .userCode(22).build();
//
//        int boardCode= boardDTO.getBoardCode();
//        int userCode= userDTO.getUserCode();
//
//        InterestBoardDTO response=boardService.registerInterestBoard(boardCode,userCode);
//        System.out.println("response : "+response);
//    }

//    @Test
//    public void testInterestBoardRegister() {
//        InterestBoardDTO interestBoardDTO=new InterestBoardDTO()
//    }
}
