package com.cn.pflow.controller;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.pflow.domain.HolidayResults;
import com.cn.pflow.domain.PflowActivity;
import com.cn.pflow.domain.Pflowarea;
import com.cn.pflow.domain.WeatherInf;
import com.cn.pflow.service.WeatherHolidayService;

@Controller
@RequestMapping("/WeatherHoliday")
public class WeatherHolidayController {

	@Resource
	private WeatherHolidayService weatherHolidayService;

	@RequestMapping("/weainfo")

	public ModelAndView wearerae(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mv = new ModelAndView("weather");
		List<Pflowarea> pflowarea = weatherHolidayService.getPflowArea();
		List<HolidayResults> HolidayResults = weatherHolidayService.getHolidayResults();
		List<PflowActivity> PflowActivity = weatherHolidayService.getPflowActivity();
		List<WeatherInf> OneDayWeatherInfWeather = weatherHolidayService.getWeatherInfWeather();
		List<WeatherInf> WeatherInfTempadvise = weatherHolidayService.getWeatherInfTempadvise();
		List<WeatherInf> OneDayWeatherInfPmtwopointfive = weatherHolidayService.getOneDayWeatherInfPmtwopointfive();

		mv.addObject("pflowarea", pflowarea);
		mv.addObject("HolidayResults", HolidayResults);
		mv.addObject("PflowActivity", PflowActivity);
		mv.addObject("OneDayWeatherInfWeather", OneDayWeatherInfWeather);
		mv.addObject("WeatherInfTempadvise", WeatherInfTempadvise);
		mv.addObject("OneDayWeatherInfPmtwopointfive", OneDayWeatherInfPmtwopointfive);
		return mv;
	}

	@RequestMapping("/weaHolidaySearch")
	@ResponseBody
	public Map<String, Object> weaTemp(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
		int oneRecord = Integer.valueOf(request.getParameter("rows"));// 每页显示个数
		int pageNo = Integer.valueOf(request.getParameter("page"));// 当前页码
		String area = request.getParameter("area");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String Holiday = (request.getParameter("Holiday") == null) ? null
				: new String(request.getParameter("Holiday").getBytes("ISO8859-1"), "UTF-8");
		String PflowActivity = (request.getParameter("PflowActivity") == null) ? null
				: new String(request.getParameter("PflowActivity").getBytes("ISO8859-1"), "UTF-8");
		String pflowweather = (request.getParameter("pflowweather") == null) ? null
				: new String(request.getParameter("pflowweather").getBytes("ISO8859-1"), "UTF-8");
		String WeatherInfTempadvise = (request.getParameter("WeatherInfTempadvise") == null) ? null
				: new String(request.getParameter("WeatherInfTempadvise").getBytes("ISO8859-1"), "UTF-8");
		String OneDayWeatherInfPmtwopointfive = (request.getParameter("OneDayWeatherInfPmtwopointfive") == null) ? null
				: new String(request.getParameter("OneDayWeatherInfPmtwopointfive").getBytes("ISO8859-1"), "UTF-8");
		String pflowStart = request.getParameter("pflowStart");
		String pflowEnd = request.getParameter("pflowEnd");

		// System.out.println(area+startDate+endDate+Holiday+PflowActivity+pflowweather+WeatherInfTempadvise+new
		// String(WeatherInfTempadvise.getBytes("ISO8859-1"),"UTF-8")
		// +OneDayWeatherInfPmtwopointfive+pflowStart+pflowEnd);

		Map<String, Object> conditions = new HashMap<>();

		if (area != null && !area.equals(""))
			conditions.put("area", area);

		if (startDate != null && !startDate.equals(""))
			conditions.put("startDate", startDate);

		if (endDate != null && !endDate.equals(""))
			conditions.put("endDate", endDate);

		if (Holiday != null && !Holiday.equals(""))
			conditions.put("Holiday", Holiday);

		if (PflowActivity != null && !PflowActivity.equals(""))
			conditions.put("PflowActivity", PflowActivity);

		if (pflowweather != null && !pflowweather.equals(""))
			conditions.put("pflowweather", pflowweather);

		if (WeatherInfTempadvise != null && !WeatherInfTempadvise.equals(""))
			conditions.put("WeatherInfTempadvise", WeatherInfTempadvise);

		if (OneDayWeatherInfPmtwopointfive != null && !OneDayWeatherInfPmtwopointfive.equals(""))
			conditions.put("OneDayWeatherInfPmtwopointfive", OneDayWeatherInfPmtwopointfive);

		if (pflowStart != null && !pflowStart.equals(""))
			conditions.put("pflowStart", pflowStart);
		if (pflowEnd != null && !pflowEnd.equals(""))
			conditions.put("pflowEnd", pflowEnd);

		Map<String, Object> charts = weatherHolidayService.getweatherholiday(oneRecord, pageNo, conditions);

		System.out.println(charts.toString());

		return charts;
	}

}
