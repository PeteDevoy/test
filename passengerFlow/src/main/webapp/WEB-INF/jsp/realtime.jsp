<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="renderer" content="webkit">
<meta name="viewport" content="width=device-width,initial-scale=1 ,maximum-scale=1,user-scale=no">
<title>实时图</title>

<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/echarts.js"></script>
<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=basePath%>/dwr/interface/RealTimeReverse.js'></script>
</head>


<body onload="InitReverseAjax()">

<div style="margin-top:0px">
	<div id="main" style="width: 100%;height:250px;margin:0 auto ;"></div>
</div>
<div style="margin-top:0px">
	<div id="main2" style="width: 100%;height:250px;margin:0 auto ;"></div>
</div>

<script type="text/javascript">
	function InitReverseAjax()
	{
		dwr.engine.setActiveReverseAjax(true);
		dwr.engine.setNotifyServerOnPageUnload(true);
		RealTimeReverse.onPageLoad(<%=request.getParameter("eqptId")%>);
	}

	var myChart = echarts.init(document.getElementById('main'));

	option = {
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        },
	        formatter: function(params) {
	        	var det='滞留' + ' : ' + (params[0].value+params[1].value) + '<br/>'+
	             '吞吐' + ' : ' + (params[2].value+params[3].value )+'<br/>';
			 return det ;}
	    },
	    legend: {
	        data:['合理滞留','警戒滞留','合理吞吐量','警戒吞吐量']
	    },
	    grid: {
	        left: '3%',
	        right: '4%',
	        bottom: '3%',
	        containLabel: true
	    },
	    xAxis : [
	        {
	            type : 'value'
	          
	        }
	    ],
	    yAxis : [
	        {
	            type : 'category',
	            axisTick : {show: false},
	            data : []
	        }
	    ],
	    series : [
			{
			    name:'合理滞留',
			    barWidth:'45',
			    type:'bar',
			    stack: '滞留',
			    itemStyle: {
	                normal: {
	                    color: function(params) {
	                        // build a color map as your need.
	                        var colorList = [
	                          ' #80dbb0'];
	                        return colorList[params.dataIndex];
	                    }
	                }
	            },

			    label: {
			        normal: {
			            show: true,
			            position: 'inside'
			        }
			    },
			    data:[]
			},
	        {
	            name:'警戒滞留',
	            barWidth:'45',
	            type:'bar',
	            stack: '滞留',
	            itemStyle: {
	                normal: {
	                    color: function(params) {
	                        // build a color map as your need.
	                        var colorList = [
	                          '#963443'];
	                        return colorList[params.dataIndex];
	                    }
	                }
	            },
	            label: {
	                normal: {
	                    show: true,
	                    position: 'inside'
	                }
	            },
	            markPoint : {
	                symbolOffset : ['2%',0],
	                silent : true,
	                data : [
	                    {name : '滞留', value : 0, xAxis: 0, yAxis: 2}
	                ]
	            },
	            data:[]
	        },
	        {
	            name:'合理吞吐量',
	            barWidth:'45',
	            type:'bar',
	            stack: '总量',
	            itemStyle: {
	                normal: {
	                    color: function(params) {
	                        // build a color map as your need.
	                        var colorList = [
	                          '#69abc5'];
	                        return colorList[params.dataIndex];
	                    }
	                }
	            },
	            label: {
	                normal: {
	                    show: true
	                }
	            },
	            data:[]
	        },
	        {
	            name:'警戒吞吐量',
	            barWidth:'45',
	            type:'bar',
	            stack: '总量',
	            itemStyle: {
	                normal: {
	                    color: function(params) {
	                        // build a color map as your need.
	                        var colorList = [
	                          '#963443'];
	                        return colorList[params.dataIndex];
	                    }
	                }
	            },
	            label: {
	                normal: {
	                    show: true
	                }
	            },
	            markPoint : {
	                symbolOffset : ['2%',0],
	                silent : true,
	                data : [
	                    {name : '吞吐量', value : 0, xAxis: 0, yAxis: 1}
	                ]
	            },
	            data:[]
	        }
	    ]
	};
	
	myChart.setOption(option);
	

	// 基于准备好的dom，初始化echarts实例
	var myChart2 = echarts.init(document.getElementById('main2'));


	// 指定图表的配置项和数据
	option2 = {
		    tooltip: {
		        trigger: 'item',
		        formatter: "{a} <br/>{b}: {c} ({d}%)"
		    },
		    
		    series: [
		        {
		            type:'pie',
		            selectedMode: 'single',
		            radius: [0, '45%'],
		            color:['#d46aa8', '#9282cb'],

		            label: {
		                normal: {
		                	formatter: '{b} : {c} ({d}%)',
		                    textStyle: {
		                        color: '#2c343c'
		                      
		                    } 
		                }
		            },
		            labelLine: {
		                normal: {
		                       lineStyle: {
		                        color: '#2c343c'
		                    },
		                    smooth: 0.2,
		                    length: 20,
		                    length2: 10
		                }
		            },
		            data:[
		                {value:0, name:'流入量',selected:true},
		                {value:0, name:'流出量'},
		          
		            ]
		        },
		    
		    ]

	};
	
	// 使用刚指定的配置项和数据显示图表。
	myChart2.setOption(option2);
	
	
	function set(hd,hds,ee,ees,er,et){
		var hold = [hd]; 
		var holds = [hds]; 
		var enterexit =[ee];
		var enterexits = [ees]; 
		
		var enter = [er];
		var exit = [et];
		
		
		//柱状 
		option.series[0].data = hold;//滞留
		option.series[1].data = holds;//滞留
		option.series[2].data = enterexit;//吞吐
		option.series[3].data = enterexits;//吞吐
		option.series[1].markPoint.data[0].value = (hd+hds);
		option.series[1].markPoint.data[0].xAxis = (hd+hds);
		option.series[3].markPoint.data[0].value = (ee + ees);
		option.series[3].markPoint.data[0].xAxis = (ee + ees);
		

		
		//饼状
		option2.series[0].data[0].value = enter;//进
		option2.series[0].data[0].name = '流入量';
		option2.series[0].data[1].value = exit;//出
		option2.series[0].data[1].name = '流出量';
		
		myChart.setOption(option);
		myChart2.setOption(option2);
	}
	
	
	$(window).resize(function() {
		$(myChart).resize();
		$(myChart2).resize();
	});
</script>
</body>
</html>
