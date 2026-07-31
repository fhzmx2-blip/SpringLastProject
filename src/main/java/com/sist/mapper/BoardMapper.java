package com.sist.mapper;
import java.util.*;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sist.vo.*;

public interface BoardMapper {
  @Select("SELECT no,subject,name,"
  		+ "TO_CHAR(regdate,'yyyy-mm-dd') as dbday,hit,group_tab "
		+ "FROM springReplyBoard "
  		+ "ORDER BY group_id DESC , group_step ASC "
		+ "OFFSET #{start} ROWS FETCH NEXT 10 ROWS ONLY")
  public List<BoardVO> boardListData(int start);
  
  @Select("SELECT COUNT(*) FROM springReplyBoard")
  public int boardRowCount();
  
  @Insert("INSERT INTO springReplyBoard(no,name,subject,content,pwd,group_id) "
		 +"VALUES(srb_no_seq.nextval,#{name},#{subject},"
		 +"#{content},#{pwd},"
		 +"(SELECT NVL(MAX(group_id)+1,1) FROM springReplyBoard))")
  public void boardInsert(BoardVO vo);
  
  @Update("UPDATE springReplyBoard SET "
		 +"hit=hit+1 "
		 +"WHERE no=#{no}")
  public void boardHitIncrement(int no);
  
  @Select("SELECT no,name,subject,content,TO_CHAR(regdate,'yyyy-mm-dd') as dbday,hit "
		 +"FROM springReplyBoard "
		 +"WHERE no=#{no}")
  public BoardVO boardDetailData(int no);

  @Select("SELECT group_id,group_step,group_tab "
		 +"FROM springReplyBoard "
		 +"WHERE no=#{no}")
  public BoardVO boardParentInfoData(int no);

  @Update("UPDATE springReplyBoard SET "
		 +"group_step=group_step+1 "
		 +"WHERE group_id=#{group_id} AND group_step>#{group_step}")
  public void boardStepIncrement(@Param("group_id") int group_id,
		  @Param("group_step") int group_step);

  @Insert("INSERT INTO springReplyBoard(no,name,subject,content,pwd,group_id,group_step,group_tab,root,depth) "
			 +"VALUES(srb_no_seq.nextval,#{name},#{subject},"
			 +"#{content},#{pwd},"
			 +"#{group_id},#{group_step},#{group_tab},#{root},#{depth})")
  public void boardReplyInsert(BoardVO vo);

  @Update("UPDATE springReplyBoard SET "
		 +"depth=depth+1 "
		 +"WHERE no=#{no}")
  public void boardDepthIncrement(int no);
 
  @Select("SELECT root,depth FROM springReplyBoard "
		 +"WHERE no=#{no}")
  public BoardVO boardInfoData(int no);

  @Select("SELECT pwd FROM springReplyBoard "
		 +"WHERE no=#{no}")
  public String boardGetPassword(int no);
  
  @Update("UPDATE springReplyBoard SET "
		 +"subject=#{subject},content=#{content} "
		 +"WHERE no=#{no}")
  public void boardMsgUpdate(BoardVO vo);
  
  @Delete("DELETE FROM springReplyBoard "
		 +"WHERE no=#{no}")
  public void boardDelete(int no);
  
 
  @Update("UPDATE springReplyBoard SET "
		 +"depth=depth-1 "
		 +"WHERE no=#{no}")
  public void boardDepthDecrement(int no);
}