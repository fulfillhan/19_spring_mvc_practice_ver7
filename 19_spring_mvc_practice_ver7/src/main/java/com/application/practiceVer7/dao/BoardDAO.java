package com.application.practiceVer7.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.application.practiceVer7.dto.BoardDTO;

@Mapper
public interface BoardDAO {

	public void insertBoard(BoardDTO boardDTO);

	public List<BoardDTO> getBoardList();

	public BoardDTO getBoardDetail(long boardId);

	public String getEncodedPasswd(long boardId);

	public void updateBoard(BoardDTO boardDTO);

	public void updateReadCnt(long boardId);

	public void deleteBoard(long boardId);

}
