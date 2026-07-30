package com.sist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sist.vo.GoodsVO;

public interface GoodsMapper {
	
	@Select("SELECT no, goods_name, goods_poster, goods_price, goods_discount, num "
			+ "FROM (SELECT no, goods_name, goods_poster, goods_price, goods_discount, rownum as num "
			+ "FROM (SELECT no, goods_name, goods_poster, goods_price, goods_discount "
			+ "FROM goods_all ORDER BY no ASC)) "
			+ "WHERE num BETWEEN #{start} AND #{end}")
	public List<GoodsVO> goodsListData(@Param("start") int start, @Param("end") int end);
	
	@Select("SELECT CEIL(COUNT(*)/12.0) FROM goods_all")
	public int goodsTotalPage();
	
	@Select("SELECT no, goods_name, goods_sub, goods_price, goods_discount, "
			+ "goods_first_price, goods_delivery, goods_poster "
			+ "FROM goods_all "
			+ "WHERE no=#{no}")
	public GoodsVO goodsDetailData(int no);
}