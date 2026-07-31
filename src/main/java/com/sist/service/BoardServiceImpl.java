package com.sist.service;
import java.util.*;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sist.mapper.*;
import com.sist.vo.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardMapper mapper; 

	@Override
	public List<BoardVO> boardListData(int start) {
		// TODO Auto-generated method stub
		return mapper.boardListData(start);
	}
	
	@Override
	public int boardRowCount() {
		// TODO Auto-generated method stub
		return mapper.boardRowCount();
	}
	
	@Override
	public void boardInsert(BoardVO vo) {
		// TODO Auto-generated method stub
	    mapper.boardInsert(vo);	
	}

	@Override
	public BoardVO boardDetailData(int no) {
		// TODO Auto-generated method stub
		mapper.boardHitIncrement(no);
		return mapper.boardDetailData(no);
	}

	@Override
	@Transactional
	public void boardReplyInsert(int pno,BoardVO vo) {
		// TODO Auto-generated method stub

		BoardVO pvo=mapper.boardParentInfoData(pno);

		mapper.boardStepIncrement(pvo.getGroup_id(), pvo.getGroup_step());	

		vo.setGroup_id(pvo.getGroup_id());
		vo.setGroup_step(pvo.getGroup_step()+1);
		vo.setGroup_tab(pvo.getGroup_tab()+1);
		vo.setRoot(pno);
		vo.setDepth(0);
		mapper.boardReplyInsert(vo);

		mapper.boardDepthIncrement(pno);
	}

	@Override
	@Transactional
	public boolean boardDelete(int no, String pwd) {
		// TODO Auto-generated method stub
		
		boolean bCheck=false;
		BoardVO vo=mapper.boardInfoData(no);
		String db_pwd=mapper.boardGetPassword(no);
		if(db_pwd.equals(pwd))
		{
			bCheck=true;
			if(vo.getDepth()==0)
			{
				mapper.boardDelete(no);
			}
			else
			{
			    BoardVO bvo=new BoardVO();
			    bvo.setContent("관리자 삭제한 게시물입니다");
			    bvo.setSubject("관리자 삭제한 게시물입니다");
			    bvo.setNo(no);
			    
			    mapper.boardMsgUpdate(bvo);
			}
			
			mapper.boardDepthDecrement(vo.getRoot());
		}
		
		return bCheck;
		
	}	   
   
}



