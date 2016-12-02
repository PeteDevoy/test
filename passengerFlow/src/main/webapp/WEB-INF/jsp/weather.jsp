<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta name="viewport"content="width=device-width,initial-scale=1 ,maximum-scale=1,user-scale=no">
<title>气候</title>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.min.css"/>

<link href="<%=request.getContextPath()%>/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen"/>

<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/font-awesome.min.css"/>
<!-- page specific plugin styles -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/jquery-ui-1.10.3.full.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/ui.jqgrid.css" />
<!-- ace styles -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/ace.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/ace-rtl.min.css" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/assets/css/ace-skins.min.css" />  
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/echarts.js"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/js/bootstrap-datetimepicker.js"charset="UTF-8"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/js/locales/bootstrap-datetimepicker.fr.js"charset="UTF-8"></script>
<script type="text/javascript"src="<%=request.getContextPath()%>/js/locales/bootstrap-datetimepicker.zh-CN.js"charset="UTF-8"></script>
<!-- ace settings handler -->
<script src="<%=request.getContextPath()%>/assets/js/ace-extra.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/typeahead-bs2.min.js"></script>
<!-- page specific plugin scripts -->
<script src="<%=request.getContextPath()%>/assets/js/jqGrid/i18n/grid.locale-en.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/jqGrid/jquery.jqGrid.min.js"></script>
<!-- ace scripts -->
<script src="<%=request.getContextPath()%>/assets/js/ace-elements.min.js"></script>
<script src="<%=request.getContextPath()%>/assets/js/ace.min.js"></script>
</head>
<body style="background-color:#171717;padding-right:25px!important;">
	<!--[if lte IE 9]>
<div class="alert alert-warning alert-dismissible" role="alert">
  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
  <strong>友情提示!</strong> 你使用的浏览器版本过低。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>以获得更好的体验！
</div>
<![endif]-->

	<div class="container-fluid" style="margin-top:30px id="container-fluid">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12">
				<div class="panel-group accordion no-margin-bottom" id="accordion">
					<div class="panel panel-default">
						<div class="panel-heading no-padding" style="background: #e4e4e4;">
							<a data-toggle="collapse" data-parent="#accordion"
								style="text-decoration:none" href="#searchDiv"> <font
								size="4" color="black" style="font-weight:bold;">天气节假日因素客流量分析：</font>
							</a>
						</div>
						<div id="searchDiv" class="panel-collapse collapse in">
							<div class="panel-body" style="background: #f5f5f5;">
								<form id="queryForm" class="form-horizontal" role="form">
									<div class="">
										<div class="col-lg-4 col-sm-6" style="margin-bottom:15px;">
											<label class="col-sm-4 control-label no-padding-right">案场区域</label>
											<div class="col-sm-8">
												<select id="area" class="chosen-select form-control"
													name="displayType">
													<c:forEach items="${pflowarea}" var="pflowarea">
														<option value="${pflowarea.id}" >${pflowarea.name}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="col-lg-4 col-sm-6" style="margin-bottom:15px;">
											<label class="col-sm-4 control-label no-padding-right">开始日期</label>
											<div class="col-sm-8">
												<div class="input-group date form_datetime ">
													<input id="startDate" placeholder="开始日期"
														class="form-control" type="text"
														value="2016-10-01 00:00:00" readonly> <span
														class="input-group-addon"> <span
														class="glyphicon glyphicon-th"></span>
													</span>
												</div>
											</div>
										</div>

										<div class="col-lg-4 col-sm-6" style="margin-bottom:15px;">
											<label class="col-sm-4 control-label no-padding-right">结束日期</label>
											<div class="col-sm-8">
												<div class="input-group date form_datetime ">
													<input id="endDate" placeholder="结束日期" class="form-control"
														type="text" value="2016-10-31 23:00:00" readonly>
													<span class="input-group-addon"> <span
														class="glyphicon glyphicon-th"></span>
													</span>
												</div>
											</div>
										</div>
									</div>

									<div class="">
										<div class="col-lg-4 col-sm-6" style="margin-bottom:15px;">
											<label class="col-sm-4 control-label" for="">节假日</label>
											<div class="col-sm-8">
												<select id="Holiday" class="chosen-select form-control"
													name="displayType">
													<option value="">无</option>
													<c:forEach items="${HolidayResults}" var="HolidayResults">
														<option value="${HolidayResults.name}">${HolidayResults.name}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="col-lg-4 col-sm-6" style="margin-bottom:15px;">
											<label class="col-sm-4 control-label" for="">节日活动</label>
											<div class="col-sm-8">
												<select id="PflowActivity"
													class="chosen-select form-control" name="displayType">
													<option value="">无</option>
													<c:forEach items="${PflowActivity}" var="PflowActivity">
														<option value="${PflowActivity.name}">${PflowActivity.name}</option>
													</c:forEach>
												</select>
											</div>
										</div>

										<div class="col-lg-4 col-sm-6" style="margin-bottom:15px;">
											<label class="col-sm-4 control-label" for="">天气</label>
											<div class="col-sm-8">
												<select id="pflowweather" class="chosen-select form-control"
													name="displayType">
													<option value="">无</option>
													<c:forEach items="${OneDayWeatherInfWeather}"
														var="OneDayWeatherInfWeather">
														<option value="${OneDayWeatherInfWeather.weather}">${OneDayWeatherInfWeather.weather}</option>
													</c:forEach>
												</select>
											</div>
										</div>

									</div>

									<div class="">
										<div class="col-lg-4 col-sm-6" style="margin-bottom:15px;">
											<label class="col-sm-4 control-label" for="">温度</label>
											<div class="col-sm-8">
												<select id="WeatherInfTempadvise"
													class="chosen-select form-control" name="displayType">
													<option value="">无</option>
													<c:forEach items="${WeatherInfTempadvise}"
														var="WeatherInfTempadvise">
														<option value="${WeatherInfTempadvise.tempAdvise}">${WeatherInfTempadvise.tempAdvise}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="col-lg-4 col-sm-6" style="margin-bottom:15px;">
											<label class="col-sm-4 control-label" for="">PM2.5</label>
											<div class="col-sm-8">
												<select id="OneDayWeatherInfPmtwopointfive"
													class="chosen-select form-control" name="displayType">
													<option value="">无</option>
													<c:forEach items="${OneDayWeatherInfPmtwopointfive}"
														var="OneDayWeatherInfPmtwopointfive">
														<option
															value="${OneDayWeatherInfPmtwopointfive.pmtwopointfive}">${OneDayWeatherInfPmtwopointfive.pmtwopointfive}</option>
													</c:forEach>
												</select>
											</div>
										</div>
										<div class="col-lg-4 col-sm-6" style="margin-bottom:15px;">

											<label class="col-sm-4 control-label" for="displayName">客流范围</label>
											<div class="col-sm-3" style="width:29%">
												<input id="pflowStart" class="form-control" name="name"
													type="text">
											</div>
											<label class="col-sm-1 control-label" for="">~</label>
											<div class="col-sm-3" style="width:29%">
												<input id="pflowEnd" class="form-control" name="name"
													type="text">
											</div>
										</div>
										<div class="col-lg-12 col-sm-6">
											<div
												style="float:right;padding-right: 15px;margin-bottom:15px;">
												<button id="searchButton" class="btn btn-primary btn-sm"
													type="button"  onclick="queryPointAsset()">查询</button>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
						<div>
							<table id="grid-table"></table>

							<div id="grid-pager"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>


	<!-- <script type="text/javascript">
tvar $path_base = "/";//this will be used in gritter alerts containing images
</script> -->
	<script type="text/javascript">
/* 	
 	var area = $("#area").val();
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var Holiday = $("#Holiday").val();
	var PflowActivity = $("#PflowActivity").val();
	var pflowweather = $("#pflowweather").val();
	var WeatherInfTempadvise = $("#WeatherInfTempadvise").val();
	var OneDayWeatherInfPmtwopointfive = $("#OneDayWeatherInfPmtwopointfive").val();
	var pflowStart = $("#pflowStart").val();
	var pflowEnd = $("#pflowEnd").val();
	  */
	  
	  function FormatDate (strTime) {
		    var date = new Date(strTime);
		    return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" 00:00:00";
		};
	 
	  
		jQuery(function($) {
			var grid_selector = "#grid-table";
			var pager_selector = "#grid-pager";

jQuery(grid_selector).jqGrid({
			//direction: "rtl",

			url : "${pageContext.request.contextPath}/WeatherHoliday/weaHolidaySearch",
			//postData:{area:area,startDate:startDate,endDate:endDate,Holiday:Holiday,PflowActivity:PflowActivity,pflowweather:pflowweather,WeatherInfTempadvise:WeatherInfTempadvise,OneDayWeatherInfPmtwopointfive:OneDayWeatherInfPmtwopointfive,pflowStart:pflowStart,pflowEnd:pflowEnd},
			mtype : "post",
			styleUI : 'Bootstrap',//设置jqgrid的全局样式为bootstrap样式  
			datatype : "json",
			height : 250,
			colNames : [  '区域名', '日期', '当日总流入量','当日总流出量', '天气', '当日温度','天气','PM2.5', '节假日', '活动名','详细' ],
			colModel : [
	
					{name : 'aname',index : 'aname',editable : true,sorttype : "date"/* ,unformat: pickDate */},
					{name : 'date',index : 'date',editable : true/* ,edittype:"select",editoptions:{value:"FE:FedEx;IN:InTime;TN:TNT;AR:ARAMEX"} */},
					{name : 'totalenters',index : 'totalenters'/* ,  sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"} */}, 
					{name : 'totalexits',index : 'totalexits'/* ,  sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"} */},
					{name : 'tempadvise',index : 'tempadvise'/* ,  sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"} */}, 
					{name : 'tempertureofday',index : 'tempertureofday'/* ,  sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"} */},
					{name : 'weather',index : 'weather'/* ,  sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"} */}, 
					{name : 'pmtwopointfive',index : 'pmtwopointfive'/* ,  sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"} */}, 
					{name : 'holidayname',index : 'holidayname'/* ,  sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"} */},
					{name : 'activityname',index : 'activityname'/* ,  sortable:false,editable: true,edittype:"textarea", editoptions:{rows:"2",cols:"10"} */},
					{name : 'Edit',index : 'Edit'},
					],

			viewrecords : true,
			rowNum : 10,
			rowList : [ 10, 20, 30 ],
			pager : pager_selector,
			altRows : true,
			jsonReader : {
				otal : 'total',
				records : 'records',
				root : 'rows',
				repeatitems : true
			},
			//toppager: true,

			multiselect : true,
			//multikey: "ctrlKey",
			multiboxonly : true,

			loadComplete : function() {
				var table = this;
				setTimeout(function() {
					styleCheckbox(table);

					updateActionIcons(table);
					updatePagerIcons(table);
					enableTooltips(table);
				}, 0);
			},

			autowidth : true,
			gridComplete: function () {
				var ids = jQuery(grid_selector).jqGrid('getDataIDs');
				for (var i = 0; i < ids.length; i++) {
					var id = ids[i];
					var editBtn = "<a href='javascript:void(0)' style='color:#f60' class='detail'>详细信息</a>";
					jQuery(grid_selector).jqGrid('setRowData', ids[i], { Edit: editBtn });
				};
				$(".detail").click(function(){
					var area = $("#area").val();
					//当前列日期+00:00:00组成起始日期时间字符串
					var myStartDate = $(this).parent().parent().children(":nth-child(3)").html()+" "+"00:00:00";
					var dateStr = myStartDate.replace(/-/g,'/'); 
				    var date =  new Date(dateStr);//起始日期时间字符串转为时间--起始时间
				    var timeStr = date.getTime().toString();//时间转为时间戳字符串--起始时间时间戳字符串
				    var timeNum = parseInt(timeStr);//时间戳字符串转为时间戳
				    timeNum=timeNum+(1000*60*60*24);//时间戳时间增加一天
					var newDate = new Date(timeNum);//时间戳转为时间--结束时间
					var myEndDate = FormatDate(newDate);//时间格式化 
					window.location = "${pageContext.request.contextPath}/page/detail?myStartDate="+myStartDate+"&myEndDate="+myEndDate+"&area="+area;
				});


			}

		});

			//enable search/filter toolbar
			//jQuery(grid_selector).jqGrid('filterToolbar',{defaultSearch:true,stringResult:true})

			//switch element when editing inline
			function aceSwitch(cellvalue, options, cell) {
				setTimeout(function() {
					$(cell).find('input[type=checkbox]').wrap(
							'<label class="inline" />').addClass(
							'ace ace-switch ace-switch-5').after(
							'<span class="lbl"></span>');
				}, 0);
			}
			//enable datepicker
			function pickDate(cellvalue, options, cell) {
				setTimeout(function() {
					$(cell).find('input[type=text]').datepicker({
						format : 'yyyy-mm-dd',
						autoclose : true
					});
				}, 0);
			}

			//it causes some flicker when reloading or navigating grid
			//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
			//or go back to default browser checkbox styles for the grid
			function styleCheckbox(table) {
				/**
				 $(table).find('input:checkbox').addClass('ace')
				 .wrap('<label />')
				 .after('<span class="lbl align-top" />')
				 t
				 t
				 $('.ui-jqgrid-labels th[id*="_cb"]:first-child')
				 .find('input.cbox[type=checkbox]').addClass('ace')
				 .wrap('<label />').after('<span class="lbl align-top" />');
				 tt*/
			}

			//unlike navButtons icons, action icons in rows seem to be hard-coded
			//you can change them like this in here if you want
			function updateActionIcons(table) {
				/**
				 var replacement = 
				 {
				 t'ui-icon-pencil' : 'icon-pencil blue',
				 t'ui-icon-trash' : 'icon-trash red',
				 t'ui-icon-disk' : 'icon-ok green',
				 t'ui-icon-cancel' : 'icon-remove red'
				 };
				 $(table).find('.ui-pg-div span.ui-icon').each(function(){
				 tvar icon = $(this);
				 tvar $class = $.trim(icon.attr('class').replace('ui-icon', ''));
				 tif($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
				 })
				 */
			}

			//replace icons with FontAwesome icons like above
			function updatePagerIcons(table) {
				var replacement = {
					'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
					'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
					'ui-icon-seek-next' : 'icon-angle-right bigger-140',
					'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
				};
				$(
						'.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon')
						.each(
								function() {
									var icon = $(this);
									var $class = $.trim(icon.attr('class')
											.replace('ui-icon', ''));

									if ($class in replacement)
										icon.attr('class', 'ui-icon '
												+ replacement[$class]);
								})
			}

			function enableTooltips(table) {
				$('.navtable .ui-pg-button').tooltip({
					container : 'body'
				});
				$(table).find('.ui-pg-div').tooltip({
					container : 'body'
				});
			}

		});

		$(window).bind('resize', function() {
			$("#grid-table").setGridWidth($(window).width() * 0.95);
		});

		//时间格式
		/* function getNowFormatDate() {
		 var date = new Date();
		 var seperator1 = "-";
		 var seperator2 = ":";
		 var month = date.getMonth() + 1;
		 var strDate = date.getDate();
		 if (month >= 1 && month <= 9) {
		 tmonth = "0" + month;
		 }
		 if (strDate >= 0 && strDate <= 9) {
		 tstrDate = "0" + strDate;
		 }
		 var currentdate = date.getFullYear() + seperator1 + month
		 + seperator1 + strDate + " " + date.getHours() + seperator2
		 + "00" + seperator2 + "00";
		 return currentdate;
		 tt}
		 tt
		 ttfunction getNowFormatDate1() {
		 var date = new Date();
		 var seperator1 = "-";
		 var seperator2 = ":";
		 var month = date.getMonth() + 1;
		 var strDate = date.getDate();
		 if (month >= 1 && month <= 9) {
		 tmonth = "0" + month;
		 }
		 if (strDate >= 0 && strDate <= 9) {
		 tstrDate = "0" + strDate;
		 }
		 var currentdate = date.getFullYear() + seperator1 + month
		 + seperator1 + strDate + " " + "00" + seperator2
		 + "00" + seperator2 + "00";
		 return currentdate;
		 tt} */

		$("#startDate,#endDate").datetimepicker({
			//language:  'fr',
			format : "yyyy-mm-dd",
			todayBtn : true,
			language : 'zh-CN',
			autoclose : true,
			pickerPosition : "bottom-left",
			todayHighlight : true,
			startView : 2,
			minView : 2
		});
		/* $("#endDate").datetimepicker('update',getNowFormatDate(new Date()));
		 tt$("#startDate").datetimepicker('update',getNowFormatDate1(new Date())); */

		//////////////////////////////////////////////////////////////////////
		
		
		
		function queryPointAsset(){
				

			var area = $("#area").val();
			var startDate = $("#startDate").val();
			var endDate = $("#endDate").val();
			var Holiday = $("#Holiday").val();
			var PflowActivity = $("#PflowActivity").val();
			var pflowweather = $("#pflowweather").val();
			var WeatherInfTempadvise = $("#WeatherInfTempadvise").val();
			var OneDayWeatherInfPmtwopointfive = $("#OneDayWeatherInfPmtwopointfive").val();
			var pflowStart = $("#pflowStart").val();
			var pflowEnd = $("#pflowEnd").val();
			var url = "${pageContext.request.contextPath}/WeatherHoliday/weaHolidaySearch?"+ "area=" + area
				url	+= ("&startDate=" + startDate);
				url	+= ("&endDate=" + endDate);
				url	+= ("&Holiday=" + Holiday);
				url	+= ("&PflowActivity=" + PflowActivity);
				url	+= ("&pflowweather=" + pflowweather);
				url	+= ("&WeatherInfTempadvise=" + WeatherInfTempadvise);
				url	+= ("&OneDayWeatherInfPmtwopointfive=" + OneDayWeatherInfPmtwopointfive);
				url	+= ("&pflowStart=" + pflowStart);
				url	+= ("&pflowEnd=" + pflowEnd);
			$("#grid-table").jqGrid('setGridParam',{ 
		        url: url, 
		        page:1,
		        mtype:"post"
		    }).trigger("reloadGrid"); //重新载入 
		}
		 
		 
		 

	</script>
</body>
</html>
