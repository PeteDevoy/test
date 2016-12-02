package com.cn.pflow.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.cn.pflow.domain.Pflowarea;
import com.cn.pflow.service.PflowareaService;

@Controller
@RequestMapping("/Pflowarea")
public class PflowareaController {
	 @Resource
	 private PflowareaService pflowareaService;
	
	 @RequestMapping(value="selectAll", method=RequestMethod.GET)
	    public ModelAndView getPflowareaList(HttpServletRequest request,HttpServletResponse response){
	        //创建一个视图对象
	        ModelAndView mv = new ModelAndView("index");
	        //查询所有区域
	        List<Pflowarea> pflowareas = pflowareaService.getPflowareaList();
	        System.out.println(pflowareas);
	        //绑定数据
	        mv.addObject("pflowareas",pflowareas);
	        return mv;
	    }

}
