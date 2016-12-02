<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="renderer" content="webkit">
<meta name="viewport"
	content="width=device-width,initial-scale=1 ,maximum-scale=1,user-scale=no">
<title>进出图</title>

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
	
<style type="text/css">
	#queryForm,#queryForm3{
		height:0;
		overflow: hidden;
	}
</style>
</head>
<body style="margin-top:-25px;">
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
						<div style="background: #e4e4e4; padding:3px;padding-left:10px">
							<!-- <a data-toggle="collapse" data-parent="#accordion"
								style="text-decoration:none" href=""> <font
								size="4" color="black" style="font-weight:bold;">人流量汇总图：</font>
							</a> -->
							<button id="inout" type="button" class="btn btn-info btn-xs">人流量汇总图</button>
							<button id="totalsum" type="button" class="btn btn-success btn-xs">流量时锋图</button>
							<button id="totalminus" type="button" class="btn btn-warning btn-xs">滞留量图</button>
						</div>
						<div id="searchDiv" class="panel-collapse collapse in">
							<div class="panel-body" style="background: #f5f5f5;">
								<form id="queryForm" class="form-horizontal" role="form">
									<!-- #section:elements.form -->
									<div class="form-group">
										<div>
											<div id="main" style="width: 90%;height:360px;margin:0 auto ;"></div>
										</div>
									</div>
								</form>
								<!-- +++++++++++++++++++++++++++++++++++++++++++++++ -->
								<form id="queryForm2" class="form-horizontal" role="form">
							         <!-- #section:elements.form -->
					          	 <div class="form-group">
					               <div>
					                 <div id="main1" style="width: 90%;height:360px;margin:0 auto ;"></div>
					               </div>
					             </div>
					           </form>
					           <!-- +++++++++++++++++++++++++++++++++++++++++ -->
					           <form id="queryForm3" class="form-horizontal" role="form">
							         <!-- #section:elements.form -->
					          <div class="form-group">
					               <div>
					                 <div id="main2" style="width: 90%;height:360px;margin:0 auto ;"></div>
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

<script type="text/javascript">
// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('main'));
		var markline=56;
		var markline1=66;

		// 指定图表的配置项和数据
		option = {
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : [ '流入量','流出量' ]
			},
			toolbox : {
				show : false,
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
		$.ajax({
			type : "post",
			/* async : false, */
			url : "${pageContext.request.contextPath}/user/showEnterExit",
			data : {},
			dataType : "json", //返回数据形式为json

		   success: function(charts) {
		      if (charts) {
		          //将返回的category和series对象赋值给options对象内的category和series
		          //因为xAxis是一个数组 这里需要是xAxis[i]的形式
		          option.xAxis[0].data = charts.legend;//X轴数据

		          option.series[0].data = charts.series;//y轴数据
		          option.series[1].data = charts.series1;//y轴数据
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
    tooltip : {
      trigger: 'axis'
    },
    legend: {
      data:['流量时峰']
    },
    toolbox: {
      show : false,
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
        name:'流量时峰',
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
		url : "${pageContext.request.contextPath}/user/showSum",
		data : {},
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
	          $("#subinfotit").text(subinfotit);
	          $("#subinfonum").text(subinfonum);
	          myChart1.setOption(option1);
	      }
	  },
	  error: function(errorMsg) {
	      alert("图表请求数据失败啦!");
	  } 
	});

// 基于准备好的dom，初始化echarts实例
  var myChart2 = echarts.init(document.getElementById('main2'));

  // 指定图表的配置项和数据
  option2 = {
    tooltip : {
      trigger: 'axis'
    },
    legend: {
      data:['滞留量']
    },
    toolbox: {
      show : false,
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
        name:'滞留量',
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
  }
  // 使用刚指定的配置项和数据显示图表。
  myChart2.setOption(option2);
  
  $.ajax({
		type : "post",
		async : false,
		url : "${pageContext.request.contextPath}/user/showDiffer",
		data : {},
		dataType : "json", //返回数据形式为json

	   success: function(charts) {
	   	  var subinfotit = "当前总滞留量：";
	   	  var subinfonum = charts.total;
	      if (charts) {
	          //将返回的category和series对象赋值给options对象内的category和series
	          //因为xAxis是一个数组 这里需要是xAxis[i]的形式
	          option2.xAxis[0].data = charts.legendDouble;
	          option2.series[0].data = charts.seriesDouble;
	          option2.series[0].markLine.data[0].yAxis=charts.markLineDouble;
	          $("#subinfotitZl").text(subinfotit);
	          $("#subinfonumZl").text(subinfonum);
	          myChart2.setOption(option2);
	      }
	  },
	  error: function(errorMsg) {
	      alert("图表请求数据失败啦!");
	  } 
	});
	
	$("#inout").click(function(){
		$("#queryForm").css({
		  "height":"auto",
		  "overflow":"hidden"
		});
		$("#queryForm2").css({
		  "height":"0",
		  "overflow":"hidden"
		});
		$("#queryForm3").css({
		  "height":"0",
		  "overflow":"hidden"
		});
	});
	$("#totalsum").click(function(){
		$("#queryForm2").css({
		  "height":"auto",
		  "overflow":"hidden"
		});
		$("#queryForm").css({
		  "height":"0",
		  "overflow":"hidden"
		});
		$("#queryForm3").css({
		  "height":"0",
		  "overflow":"hidden"
		});
	});
	$("#totalminus").click(function(){
		$("#queryForm3").css({
		  "height":"auto",
		  "overflow":"hidden"
		});
		$("#queryForm").css({
		  "height":"0",
		  "overflow":"hidden"
		});
		$("#queryForm2").css({
		  "height":"0",
		  "overflow":"hidden"
		});
	});
	
	var time = 60000;
	window.setInterval(dynamic, time);
	function dynamic(){
		$.ajax({
			type : "post",
			/* async : false, */
			url : "${pageContext.request.contextPath}/user/showEnterExit",
			data : {},
			dataType : "json", //返回数据形式为json

		   success: function(charts) {
		      if (charts) {
		          //将返回的category和series对象赋值给options对象内的category和series
		          //因为xAxis是一个数组 这里需要是xAxis[i]的形式
		          option.xAxis[0].data = charts.legend;//X轴数据

		          option.series[0].data = charts.series;//y轴数据
		          option.series[1].data = charts.series1;//y轴数据
		          myChart.setOption(option);
		      }
		  },
		  error: function(errorMsg) {
		      alert("图表请求数据失败!");
		  } 
		});
		////////////////////////
		$.ajax({
			type : "post",
			async : false,
			url : "${pageContext.request.contextPath}/user/showSum",
			data : {},
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
		          $("#subinfotit").text(subinfotit);
		          $("#subinfonum").text(subinfonum);
		          myChart1.setOption(option1);
		      }
		  },
		  error: function(errorMsg) {
		      alert("图表请求数据失败啦!");
		  } 
		});
		////////////////////////////
		$.ajax({
			type : "post",
			async : false,
			url : "${pageContext.request.contextPath}/user/showDiffer",
			data : {},
			dataType : "json", //返回数据形式为json
	
		   success: function(charts) {
		   	  var subinfotit = "当前总滞留量：";
		   	  var subinfonum = charts.total;
		      if (charts) {
		          //将返回的category和series对象赋值给options对象内的category和series
		          //因为xAxis是一个数组 这里需要是xAxis[i]的形式
		          option2.xAxis[0].data = charts.legendDouble;
		          option2.series[0].data = charts.seriesDouble;
		          option2.series[0].markLine.data[0].yAxis=charts.markLineDouble;
		          $("#subinfotitZl").text(subinfotit);
		          $("#subinfonumZl").text(subinfonum);
		          myChart2.setOption(option2);
		      }
		  },
		  error: function(errorMsg) {
		      alert("图表请求数据失败啦!");
		  } 
		});	
	} 
	
	
	$(window).resize(function() {
		$(myChart).resize();
		$(myChart1).resize();
		$(myChart2).resize();
	});
	
	</script>
</body>
</html>
