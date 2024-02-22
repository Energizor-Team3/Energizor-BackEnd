package com.energizor.restapi.board.service;

import com.energizor.restapi.board.dto.*;
import com.energizor.restapi.board.entity.Board;
import com.energizor.restapi.board.entity.InterestBoard;
import com.energizor.restapi.board.entity.TemporaryBoard;
import com.energizor.restapi.users.dto.UserDTO;
import com.energizor.restapi.users.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {


    PageResultDTO<BoardDTO,Board> findAllList(PageRequestDTO pageRequestDTO);

    Object findDetailBoard(int boardCode);

    BoardDTO register(BoardDTO boardDTO, UserDTO principal, MultipartFile[] uploadFiles);

    String update(BoardDTO boardDTO,UserDTO principal);


    BoardDTO delete(int boardCode,UserDTO principal);

    List<BoardCommentDTO> findComment(int boardCode);

    BoardCommentDTO registerComment(BoardCommentDTO boardCommentDTO,UserDTO user);

    BoardCommentDTO updateComment(int commentCode,UserDTO user);

    BoardCommentDTO deleteComment(int commentCode,UserDTO user);

    InterestBoardDTO registerInterestBoard(int boardCode, int ownerCode);

    boolean deleteInterestBoard(int interestCode,int ownerCode);


    PageResultDTO<InterestBoardDTO,InterestBoard> findInterestBoardList(PageRequestDTO pageRequestDTO);

    InterestBoardDTO findDetailInterestBoard(int interestCode);

    PageResultDTO findTemporaryBoardList(PageRequestDTO pageRequestDTO);

    TemporaryBoardDTO findDetailTemporaryBoard(int temporaryCode);

    boolean deleteTemporaryBoard(int temporaryCode,UserDTO user);

    default InterestBoardDTO interestEntityToDto(InterestBoard interestBoard) {

        InterestBoardDTO interestBoardDTO=InterestBoardDTO.builder()
                .interestCode(interestBoard.getInterestCode())
                .boardCode(interestBoard.getBoard().getBoardCode())
                .userCode(interestBoard.getBoard().getUser().getUserCode())
                .userName(interestBoard.getBoard().getUser().getUserName())
                .teamName(interestBoard.getBoard().getUser().getTeam().getTeamName())
                .deptName(interestBoard.getBoard().getUser().getTeam().getDept().getDeptName())
                .title(interestBoard.getBoard().getTitle())
                .content(interestBoard.getBoard().getContent())
                .viewCount(interestBoard.getBoard().getViewCount())
                .registerDate(interestBoard.getRegisterDate())
                .updateDate(interestBoard.getUpdateDate())
                .deleteDate(interestBoard.getDeleteDate())
                .build();

        return interestBoardDTO;
    }

    default InterestBoardDTO interestEntityToDto2(InterestBoard interestBoard, Board board, User user) {

        InterestBoardDTO interestBoardDTO = InterestBoardDTO.builder()
                .interestCode(interestBoard.getInterestCode())
                .boardCode(board.getBoardCode())
                .userCode(user.getUserCode())
                .userName(user.getUserName())
                .title(board.getTitle())
                .content(board.getTitle())
                .viewCount(board.getViewCount())
                .teamName(user.getTeam().getTeamName())
                .deptName(user.getTeam().getDept().getDeptName())
                .registerDate(board.getRegisterDate())
                .updateDate(board.getUpdateDate())
                .deleteDate(board.getDeleteDate())
                .build();

        return interestBoardDTO;
    }

    default TemporaryBoardDTO temporaryEntityToDto(TemporaryBoard temporaryBoard) {
        TemporaryBoardDTO temporaryBoardDTO=TemporaryBoardDTO.builder()
                .temporaryCode(temporaryBoard.getTemporaryCode())
                .title(temporaryBoard.getTitle())
                .content(temporaryBoard.getContent())
                .viewCount(temporaryBoard.getViewCount())
                .registerDate(temporaryBoard.getRegisterDate())
                .updateDate(temporaryBoard.getUpdateDate())
                .deleteDate(temporaryBoard.getDeleteDate())
                .userCode(temporaryBoard.getUser().getUserCode())
                .userName(temporaryBoard.getUser().getUserName())
                .teamName(temporaryBoard.getUser().getTeam().getTeamName())
                .deptName(temporaryBoard.getUser().getTeam().getDept().getDeptName())
                .build();

        return temporaryBoardDTO;
    }


    boolean addViews(int boardCode);
}
