package com.energizor.restapi.board.service;

import com.energizor.restapi.board.dto.*;
import com.energizor.restapi.board.entity.Board;
import com.energizor.restapi.board.entity.InterestBoard;
import com.energizor.restapi.board.entity.User;
import com.energizor.restapi.common.Criteria;
import org.springframework.data.domain.Page;

public interface BoardService {


    Page<BoardDTO> findAllList(Criteria cri, int boardTypeCode);

    Object findDetailBoard(int boardCode);

    BoardDTO register(BoardDTO boardDTO);

    String update(BoardDTO boardDTO);


    BoardDTO delete(int boardCode);

    BoardCommentDTO findComment(int boardCode);

    BoardCommentDTO registerComment(BoardCommentDTO boardCommentDTO);

    String updateComment(BoardCommentDTO boardCommentDTO);

    BoardCommentDTO deleteComment(int commentCode);

    InterestBoardDTO registerInterestBoard(int boardCode, int userCode);

    InterestBoardDTO deleteInterestBoard(int interestCode);


    PageResultDTO<InterestBoardDTO,Object []> findInterestBoardList(PageRequestDTO pageRequestDTO);

    InterestBoardDTO findDetailInterestBoard(int interestCode);

    default InterestBoardDTO interestEnntityToDto(InterestBoard interestBoard, Board board, User user,Long replyCount) {

        InterestBoardDTO interestBoardDTO=InterestBoardDTO.builder()
                .interestCode(interestBoard.getInterestCode())
                .boardCode(board.getBoardCode())
                .userCode(user.getUserCode())
                .userName((board.getUser().getUserName()))
                .teamName(board.getUser().getTeam().getTeamName())
                .deptName(board.getUser().getTeam().getDepartment().getDeptName())
                .title(board.getTitle())
                .viewCount(board.getViewCount())
                .registerDate(board.getRegisterDate())
                .updateDate(board.getUpdateDate())
                .deleteDate(board.getDeleteDate())
                .build();

        return interestBoardDTO;
    }


}
