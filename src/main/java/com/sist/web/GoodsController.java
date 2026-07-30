package com.sist.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sist.service.GoodsService;
import com.sist.vo.GoodsVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class GoodsController {
	private final GoodsService gService;

    @GetMapping("goods/main.do")
    public String main_main(String page, Model model, HttpServletRequest request) {
    	if (page==null) page = "1";
    	int curpage=Integer.parseInt(page);
    	final int ROWSIZE=12;
    	int start=(ROWSIZE*curpage)-(ROWSIZE-1);
    	int end=ROWSIZE*curpage;
    	List<GoodsVO> list = gService.goodsListData(start, end);
    	int totalpage=gService.goodsTotalPage();
    	
    	final int BLOCK = 10;
    	int startPage=((curpage-1)/BLOCK*BLOCK)+1;
    	int endPage=((curpage-1)/BLOCK*BLOCK)+BLOCK;
    	if(endPage>totalpage) endPage=totalpage;
    	
    	model.addAttribute("list", list);
    	model.addAttribute("curpage", curpage);
    	model.addAttribute("totalpage", totalpage);
    	model.addAttribute("startPage", startPage);
    	model.addAttribute("endPage", endPage);
    	
    	model.addAttribute("main_jsp", "../goods/list.jsp");
    	
    	List<GoodsVO> cList=new ArrayList<GoodsVO>();
    	Cookie[] cookies=request.getCookies();
    	if(cookies!=null) {
    		for(int i=cookies.length-1;i>=0;i--) {
    			if(cookies[i].getName().startsWith("goods_")) {
    				String no=cookies[i].getValue();
    				
    				GoodsVO vo=gService.goodsDetailData(Integer.parseInt(no));
    				
    				cList.add(vo);
    			}
    		}
    	}
    	
    	model.addAttribute("cList",cList);
    	model.addAttribute("size",cList.size());
    	
        return "main/main"; 
    }
    
    @GetMapping("goods/detail_before.do")
	public String goods_detail_before(int no, HttpServletResponse response, RedirectAttributes ra) {
		
		Cookie cookie=new Cookie("goods_"+no, String.valueOf(no));
		cookie.setPath("/");
		cookie.setMaxAge(60*60*24);
		response.addCookie(cookie);
		ra.addAttribute("no", no);
		
		return"redirect:../goods/detail.do";
	}
	
	@GetMapping("goods/detail.do")
	public String goods_detail(int no, Model model, HttpServletResponse response) {
		
		GoodsVO vo = gService.goodsDetailData(no);
	    model.addAttribute("vo", vo);
		model.addAttribute("main_jsp", "../goods/detail.jsp");
		
		return "main/main";
	}
}
