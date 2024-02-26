package com.application.practiceVer7.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.application.practiceVer7.dao.BoardDAO;
import com.application.practiceVer7.dto.BoardDTO;

@Service
public class BoardServiceImple implements BoardService {

	@Autowired
	private BoardDAO boardDAO;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void insertBoard(BoardDTO boardDTO) {
	//	passwordEncoder.encode(boardDTO.getPasswd());
		boardDTO.setPasswd(	passwordEncoder.encode(boardDTO.getPasswd()));
		boardDAO.insertBoard(boardDTO);
	}

	@Override
	public List<BoardDTO> getBoardList() {
		
		return boardDAO.getBoardList();
	}

	@Override
	public BoardDTO getBoardDetail(long boardId) {
		//조회수 증가
		boardDAO.updateReadCnt(boardId);
		return boardDAO.getBoardDetail(boardId);
	}

	@Override
	public boolean isAuthenticated(BoardDTO boardDTO) {
		boolean isCheck=false;
		
		//암호화된 비번 가져오기
		String  getEncodedPasswd = boardDAO.getEncodedPasswd(boardDTO.getBoardId());
		//passwordEncoder.matches(boardDTO.getPasswd(), getEncodedPasswd);
		if(passwordEncoder.matches(boardDTO.getPasswd(), getEncodedPasswd)) {
			isCheck = true;
		}
		return isCheck;
	}

	@Override
	public void updateBoard(BoardDTO boardDTO) {
		boardDAO.updateBoard(boardDTO);
	}

	@Override
	public void deleteBoard(long boardId) {
		boardDAO.deleteBoard(boardId);
	}
	
	
}
