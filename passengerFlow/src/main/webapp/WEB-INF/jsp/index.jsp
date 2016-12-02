<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	/* String myStartDate = request.getParameter("myStartDate");
	String myEndDate = request.getParameter("myEndtDate"); */
%>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta name="viewport"
	content="width=device-width,initial-scale=1 ,maximum-scale=1,user-scale=no">
<title>人群统计分析系统</title>

<link rel="stylesheet"
	href="<%=request.getContextPath()%>/css/bootstrap.min.css">
<link
	href="<%=request.getContextPath()%>/css/bootstrap-datetimepicker.min.css"
	rel="stylesheet" media="screen">
<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script src="<%=request.getContextPath()%>/js/echarts.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/bootstrap-datetimepicker.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/locales/bootstrap-datetimepicker.fr.js"
	charset="UTF-8"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/locales/bootstrap-datetimepicker.zh-CN.js"
	charset="UTF-8"></script>
</head>
<body style="background-color:#171717;padding-right:25px!important;">
<!--[if lte IE 9]>
<div class="alert alert-warning alert-dismissible" role="alert">
  <button type="button" class="close" data-dismiss="alert"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
  <strong>友情提示!</strong> 你使用的浏览器版本过低。 请 <a href="http://browsehappy.com/" target="_blank">升级浏览器</a>以获得更好的体验！
</div>
<![endif]-->

<div class="container-fluid" style="margin-top:30px">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12">
				<div class="panel-group accordion no-margin-bottom" id="accordion">
					<div class="panel panel-default">
						<div class="panel-heading no-padding" style="background: #e4e4e4;">
							<a data-toggle="collapse" data-parent="#accordion"
								style="text-decoration:none" href="#searchDiv"> <font
								size="4" color="black" style="font-weight:bold;">人流进出分时图：</font>
							</a>
						</div>
						<div id="searchDiv" class="panel-collapse collapse in">
							<div class="panel-body" style="background: #f5f5f5;">
								<form id="queryForm" class="form-horizontal" role="form">
									<!-- #section:elements.form -->
									<div class="form-group">
									   <div class="col-lg-4 col-sm-6">
											<label class="col-sm-4 control-label" for="displayName">区域</label>
											<div class="col-sm-8">
												<select id="area" class="chosen-select form-control"
													name="displayType">
													<c:forEach items="${pflowareas}" var="pflowarea">
														<option value="${pflowarea.id}">${pflowarea.name}</option>
													</c:forEach>
												</select>
											</div>
										</div>
									   <div class="col-lg-4 col-sm-6">
											<label class="col-sm-6 control-label no-padding-right"
												><h4 style="font-size:20px;">当日流入总数：</h4></label>
											<div class="col-sm-6">
												<h4><div id="totalin" class="input-group " style="margin-top:18px;font-size:20px;color:#C23531">
													
												</div></h4>
											</div>
										</div>
										<div class="col-lg-4 col-sm-6">
											<label class="col-sm-6 control-label no-padding-right"
												><h4 style="font-size:20px;">当日流出总数：</h4></label>
											<div class="col-sm-6">
												<h4><div id="totalout" class="input-group " style="margin-top:18px;font-size:20px;color:#C23531">
													
												</div></h4>
											</div>
										</div>
										
									</div>
									
									<div class="form-group">
										<div class="col-lg-4 col-sm-6">
											<label class="col-sm-4 control-label no-padding-right"
												>开始日期</label>
											<div class="col-sm-8">
												<div class="input-group date form_datetime ">
													<input id="startDate" placeholder="开始日期"
														class="form-control" type="text"
														value="=2007-7-17 14:00:00" readonly>
														
														<span class="input-group-addon">
															<span class="glyphicon glyphicon-th"></span>
														</span>
												</div>
											</div>
										</div>

										<div class="col-lg-4 col-sm-6">
											<label class="col-sm-4 control-label no-padding-right"
												>结束日期</label>
											<div class="col-sm-8">
												<div class="input-group date form_datetime ">
													<input id="endDate" placeholder="结束日期" class="form-control"
														type="text" value="" readonly>
														<span class="input-group-addon">
															<span class="glyphicon glyphicon-th"></span>
														</span>
												</div>
											</div>
										</div>

										<div class="col-lg-3 col-sm-6">
											<label class="col-sm-4 control-label" for="displayName">时间跨度</label>
											<div class="col-sm-8">
												<select id="timespan" class="chosen-select form-control"
													name="displayType">
													<option value="1" selected>1分钟</option>
													<option value="15">15分钟</option>
													<option value="30">30分钟</option>
													<option value="60">1小时</option>
													<option value="70">天（总和）</option>
													<option value="80">天（平均）</option>
													<option value="90">月（平均）</option>
												</select>
											</div>
										</div>


										<div class="col-lg-11 col-sm-11" style="margin-top:20px">
										
											<div style="float:right">
												<button id="searchButton" type="button"
													class="btn btn-primary btn-sm">查询</button>
												<button id="cancelButton" type="button"
													class="btn btn-default btn-sm">重置</button>
											</div>
										</div>

										<div style="margin-top:130px">
											<div id="main"
												style="width: 90%;height:400px;margin:0 auto ;"></div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<!-- --------------------------------------------------------------------- -->
<div class="container-fluid" style="margin-top:30px">
  <div class="row">
    <div class="col-lg-12 col-md-12 col-sm-12">
     <div class="panel-group accordion no-margin-bottom" id="accordion1">
      <div class="panel panel-default">
        <div class="panel-heading no-padding" style="background: #e4e4e4;">
                <a data-toggle="collapse" data-parent="#accordion1" style="text-decoration:none" href="#searchDiv1">
               <font size="4" color="black" id="tb_title" style="font-weight:bold;"> 人群吞吐流量图：</font>
              </a>
        </div>
        <div id="searchDiv1" class="panel-collapse collapse in">
          <div class="panel-body" style="background: #f5f5f5;">
            <form id="queryForm" class="form-horizontal" role="form">
            <!-- #section:elements.form -->
           	 <div class="form-group">
           	 	<div class="col-lg-4 col-sm-6">
					<label class="col-sm-4 control-label" for="displayName">区域</label>
					<div class="col-sm-8">
						<select id="areaDouble" class="chosen-select form-control"
							name="displayType">
							<c:forEach items="${pflowareas}" var="pflowarea">
								<option value="${pflowarea.id}">${pflowarea.name}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="col-lg-4 col-sm-6">
					<label class="col-sm-5 control-label no-padding-right"
						><h4><div id="subinfotit" style="font-size:20px;">当前总流量：</div></h4></label>
					<div class="col-sm-7">
						<h4><div id="subinfonum" class="input-group " style="margin-top:18px;font-size:20px;color:#C23531">
							0
						</div></h4>
					</div>
				</div>
				
				<!-- <div class="col-lg-4 col-sm-6">
					<label class="col-sm-5 control-label no-padding-right"
						><h4 style="font-size:20px;">设备编号：</h4></label>
					<div class="col-sm-7">
						<h4><div style="margin-top:18px;font-size:20px;">82031679</div></h4>
					</div>
				</div> -->
			 </div>
             <div class="form-group">
                 <div class="col-lg-4 col-sm-6" style="padding-top:20px!important">
                  <label  class="col-sm-4 control-label no-padding-right"  >开始日期</label>
                <div class="col-sm-8">
                <div class="input-group date form_datetime " >
                    <input id="startDateDouble" placeholder="开始日期"  class="form-control"  type="text" value="" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                </div>
                </div>
                </div>

                 <div class="col-lg-4 col-sm-6" style="padding-top:20px!important">
                  <label  class="col-sm-4 control-label no-padding-right" >结束日期</label>
                <div class="col-sm-8">
                <div class="input-group date form_datetime ">
                    <input id="endDateDouble" placeholder="结束日期"  class="form-control"  type="text" value="" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-th"></span></span>
                </div>
                </div>
                </div>

                   <div class="col-lg-4 col-sm-6" style="padding-top:20px!important">
                    <label class="col-sm-4 control-label" for="displayName">时间跨度</label>
                      <div class="col-sm-8">
                        <select class="chosen-select form-control" name="displayType" id="timespanDouble" >
                          <option value="1" selected>1分钟</option>
                          <option value="15">15分钟</option>
                          <option value="30">30分钟</option>
						  <option value="60">1小时</option>
						  <option value="70">天（总和）</option>
						  <option value="80">天（平均）</option>
						  <option value="90">月（平均）</option>
                        </select>
                      </div>
                    </div>  

                  <div class="col-lg-12 col-md-12 col-sm-12" style="margin-top:20px">
                      <!-- #section:elements.form -->
                       <div class="form-group">
                          <div class="col-lg-4 col-sm-6">
                            <label class="col-sm-4 control-label" for="name">警戒线 </label>
                            <div class="col-sm-8">
                              <input type="text" name="name" class="form-control" id="warnLineDouble">
                            </div>
                          </div>
                          <div class="col-lg-4 col-sm-6">
                          <label class="col-sm-4 control-label" for="displayName">场景选择</label>
                            <div class="col-sm-8">
                              <select class="chosen-select form-control" name="displayType" id="sceneDouble">
                                <option value="1" selected>时峰图</option>
                                <option value="2">滞留图</option>
                              </select>
                            </div>
                        </div> 
                      </div>
                  </div>
                  <div class="col-lg-12 col-sm-12" style="margin-top:40px">
                   <div style="float:right">
                     <button id="searchButtonDouble" type="button" class="btn btn-primary btn-sm" >
                      查询
                    </button>

                    </div>
                  </div>
                  <div  style="margin-top:190px;padding-top:80px">
                    <div id="main1" style="width: 90%;height:400px;margin:0 auto ;"></div>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div> 

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">友情提示</h4>
      </div>
      <div class="modal-body">
        	选择时间长度请不要超过一年！
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
    </div>
  </div>
</div>

<div class="modal fade" id="myModal1" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">友情提示</h4>
      </div>
      <div class="modal-body">
        	选择时间长度请不要小于时间跨度！
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
    </div>
  </div>
</div>

<script type="text/javascript">
		//时间格式
		function getNowFormatDate() {
			var date = new Date();
			var seperator1 = "-";
			var seperator2 = ":";
			var month = date.getMonth() + 1;
			var strDate = date.getDate();
			if (month >= 1 && month <= 9) {
				month = "0" + month;
			}
			if (strDate >= 0 && strDate <= 9) {
				strDate = "0" + strDate;
			}
			var currentdate = date.getFullYear() + seperator1 + month
					+ seperator1 + strDate + " " + (date.getHours()+1) + seperator2
					+ "00" + seperator2 + "00";
			return currentdate;
		}
		
		function getNowFormatDate1() {
			var date = new Date();
			var seperator1 = "-";
			var seperator2 = ":";
			var month = date.getMonth() + 1;
			var strDate = date.getDate();
			if (month >= 1 && month <= 9) {
				month = "0" + month;
			}
			if (strDate >= 0 && strDate <= 9) {
				strDate = "0" + strDate;
			}
			var currentdate = date.getFullYear() + seperator1 + month
					+ seperator1 + strDate + " " + "00" + seperator2
					+ "00" + seperator2 + "00";
			return currentdate;
		}

		$("#startDateDouble,#endDateDouble,#startDate,#endDate").datetimepicker({
			//language:  'fr',
			format : "yyyy-mm-dd hh:00:00",
			todayBtn : true,
			language : 'zh-CN',
			autoclose : true,
			pickerPosition : "bottom-left",
			todayHighlight : true,
			startView : 2,
			minView : 1

		});
		$("#endDateDouble,#endDate").datetimepicker('update',getNowFormatDate(new Date()));
		$("#startDateDouble,#startDate").datetimepicker('update',getNowFormatDate1(new Date()));
		
		//alert($("#timespan").val());


// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('main'));
		var markline=56;
		var markline1=66;

		// 指定图表的配置项和数据
		option = {
			title : {
				text : '人群流量汇总图'
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '流入量','流出量' ]
			},
			toolbox : {
				show : true,
				feature : {
					mark : {
						show : true
					},
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},

		calculable : true,
			xAxis : [ {
				type : 'category',
				data : []
			} ],
			yAxis : [ {
				type : 'value'
			} ],
			  dataZoom: [{
			        type: 'inside',
			        start: 0,
			        end: 100
			    }, {
			        start: 0,
			        end: 100,
			        handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
			        handleSize: '90%',
			        handleStyle: {
			            color: '#fff',
			            shadowBlur: 3,
			            shadowColor: 'rgba(0, 0, 0, 0.6)',
			            shadowOffsetX: 2,
			            shadowOffsetY: 2
			        }
			    }], 
			series : [ {
				name : '流入量',
				type : 'bar',
				data : [],
				markPoint : {
					data : [ {
						type : 'max',
						name : '最大值'
					}, {
						type : 'min',
						name : '最小值'
					} ]
				}
			},
			 {
				name : '流出量',
				type : 'bar',
				data : [],
				markPoint : {
					data : [ {
						type : 'max',
						name : '最大值'
					}, {
						type : 'min',
						name : '最小值'
					} ]
				}
			}]
		};
		
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
		
		$("#searchButton").click(function(){
			var startDateMs = new Date($("#startDate").val());
			var endDateMs = new Date($("#endDate").val());
			var timespanMs = $("#timespan").val()*60*1000;
			if(endDateMs-startDateMs > 31536000000){
				$('#myModal').modal("show");
				return;
			}else if((endDateMs-startDateMs) < timespanMs){
				$('#myModal1').modal("show");
				return;
			};
			
			$.ajax({
				type : "post",
				async : false,
				url : "${pageContext.request.contextPath}/page/io",
				data : {
					startDate : $("#startDate").val(),
					endDate : $("#endDate").val(),
					timespan : $("#timespan").val(),
					area: $("#area").val()
				},
				dataType : "json", //返回数据形式为json
				  success: function(charts) {
				      if (charts) {
				          //将返回的category和series对象赋值给options对象内的category和series
				          //因为xAxis是一个数组 这里需要是xAxis[i]的形式
				          option.xAxis[0].data = charts.legend;//X轴数据

				          option.series[0].data = charts.series;//y轴数据
				          option.series[1].data = charts.series1;//y轴数据
				          //option.series[0].markLine.data[0].yAxis = charts.markLine-30;//y轴数据
				          //option.series[1].markLine.data[0].yAxis = charts.markLine1;//y轴数据
						  $("#totalin")[0].innerHTML=charts.totalin;
		    	   		  $("#totalout")[0].innerHTML=charts.totalout;
				          myChart.setOption(option);
				      }
				  },
				  error: function(errorMsg) {
				      alert("图表请求数据失败!");
				  } 
		
			});
		});
		
		
		$.ajax({
			type : "post",
			/* async : false, */
			url : "${pageContext.request.contextPath}/page/io",
			data : {
				area: $("#area").val()
			},
			dataType : "json", //返回数据形式为json

		   success: function(charts) {
		      if (charts) {
		          //将返回的category和series对象赋值给options对象内的category和series
		          //因为xAxis是一个数组 这里需要是xAxis[i]的形式
		          option.xAxis[0].data = charts.legend;//X轴数据

		          option.series[0].data = charts.series;//y轴数据
		          option.series[1].data = charts.series1;//y轴数据
		           //option.series[0].markLine.data[0].yAxis = charts.markLine-30;//y轴数据
		           //option.series[1].markLine.data[0].yAxis = charts.markLine1;//y轴数据
		           $("#totalin")[0].innerHTML=charts.totalin;
		    	   $("#totalout")[0].innerHTML=charts.totalout;
					  
		          myChart.setOption(option);
		      }
		  },
		  error: function(errorMsg) {
		      alert("图表请求数据失败!");
		  } 
		});


  // 基于准备好的dom，初始化echarts实例
  var myChart1 = echarts.init(document.getElementById('main1'));

  // 指定图表的配置项和数据
  option1 = {
    title : {
      text: '人群流量时峰图',
    },
    tooltip : {
      trigger: 'axis'
    },
    legend: {
      data:['人群流量时峰']
    },
    toolbox: {
      show : true,
      feature : {
        mark : {show: true},
        dataView : {show: true, readOnly: false},
        magicType : {show: true, type: ['line', 'bar']},
        restore : {show: true},
        saveAsImage : {show: true}
      }
    },
    calculable : true,
    dataZoom: [{
        type: 'inside',
        start: 0,
        end: 100
    }, {
        start: 0,
        end: 100,
        handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
        handleSize: '80%',
        handleStyle: {
            color: '#fff',
            shadowBlur: 3,
            shadowColor: 'rgba(0, 0, 0, 0.6)',
            shadowOffsetX: 2,
            shadowOffsetY: 2
        }
    }],
    xAxis : [
      {	
        type : 'category',
        data : []
      }
    ],
    yAxis : [
      {
        type : 'value'
      }
    ],
    series : [
      {
        name:'人群流量时峰',
        type:'line',
        itemStyle:{
            normal:{
                color: "#2F4554" //图标颜色
            }
        }, 
        data:[],
        markPoint : {
          data : [
            {type : 'max', name: '最大值'},
            {type : 'min', name: '最小值'}
          ]
        },
        markLine : {
          lineStyle:{
	         normal:{
	             color: "#D53A35"
	         }
          },
          data : [
            {yAxis:200}
          ]
        }
      }
    ]
  };

  // 使用刚指定的配置项和数据显示图表。
  myChart1.setOption(option1);
  $.ajax({
		type : "post",
		async : false,
		url : "${pageContext.request.contextPath}/page/iosumdiff",
		data : {
			area: $("#areaDouble").val()
		},
		dataType : "json", //返回数据形式为json

	   success: function(charts) {
	   	  var subinfotit = "当前总流量：";
	   	  var subinfonum = charts.total;
	      if (charts) {
	          //将返回的category和series对象赋值给options对象内的category和series
	          //因为xAxis是一个数组 这里需要是xAxis[i]的形式
	          option1.xAxis[0].data = charts.legendDouble;
	          option1.series[0].data = charts.seriesDouble;
	          option1.series[0].markLine.data[0].yAxis=charts.markLineDouble;
	          //option1.title.subtext = subinfo;
	          $("#subinfotit").text(subinfotit);
	          $("#subinfonum").text(subinfonum);
	          myChart1.setOption(option1);
	      }
	  },
	  error: function(errorMsg) {
	      alert("图表请求数据失败啦!");
	  } 
	});
	
	$("#searchButtonDouble").click(function(){
		var startDateMs = new Date($("#startDateDouble").val());
		var endDateMs = new Date($("#endDateDouble").val());
		var timespanMs = $("#timespanDouble").val()*60*1000;
		if(endDateMs-startDateMs > 31536000000){
			//alert("选择时间不得超过一年");
			$('#myModal').modal("show");
			return;
		}else if((endDateMs-startDateMs) <= timespanMs){
			$('#myModal1').modal("show");
			return;
		};
		$.ajax({
			type : "post",
			async : false,
			url : "${pageContext.request.contextPath}/page/iosumdiff",
			data : {
				startDate : $("#startDateDouble").val(),//起始日期时间
				endDate : $("#endDateDouble").val(),//结束日期时间
				timespan : $("#timespanDouble").val(),//时间间隔
				warnLine: $("#warnLineDouble").val(),//警戒线值
				scene: $("#sceneDouble").val(),//场景选择 时峰 2滞留
				area: $("#areaDouble").val()
			},
			dataType : "json", //返回数据形式为json
	
		   success: function(charts) {
		   	  var subinfotit;
		   	  var subinfonum;
		   	  var zll = Array('滞留量');
		   	  if($("#sceneDouble").val() == "1" || $("#sceneDouble").val() == ""){
		   	  	option1.title.text = "人群流量时峰图";
		   	  	$("#tb_title").text("人群流量时峰图");
		   	  	subinfotit = "当前总流量：";
		   	  	subinfonum = charts.total;
		   	  }else{
		   	  	option1.title.text = "人群滞留量图";
		   	  	$("#tb_title").text("人群滞留量图");
		   	  	subinfotit = "当前总滞留量：";
		   	  	subinfonum = charts.total;
		   	  }
		      if (charts) {
		          //将返回的category和series对象赋值给options对象内的category和series
		          //因为xAxis是一个数组 这里需要是xAxis[i]的形式
		          option1.xAxis[0].data = charts.legendDouble;
		          option1.series[0].data = charts.seriesDouble;
		          option1.series[0].markLine.data[0].yAxis=charts.markLineDouble;
		          //option1.title.subtext = subinfo;
		          $("#subinfotit").text(subinfotit);
		          $("#subinfonum").text(subinfonum);
		          myChart1.setOption(option1);
		      }
		  },
		  error: function(errorMsg) {
		      alert("图表请求数据失败啦!");
		  } 
		});
	});
	
	$(window).resize(function() {
		$(myChart).resize();
		$(myChart1).resize();
	});

	</script>
</body>
</html>
