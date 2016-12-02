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
<meta name="renderer" content="webkit">
<meta name="viewport"
	content="width=device-width,initial-scale=1 ,maximum-scale=1,user-scale=no">
<title>秒图</title>

<script src="<%=request.getContextPath()%>/js/jquery.min.js"></script>
<script src="<%=request.getContextPath()%>/js/echarts.js"></script>
</head>
<body>

<div style="margin-top:130px">
	<div id="main" style="width: 90%;height:400px;margin:0 auto ;"></div>
</div>

<script type="text/javascript">
	var myChart = echarts.init(document.getElementById('main'));

	option = {
	    tooltip : {
	        trigger: 'axis',
	        axisPointer : {            // 坐标轴指示器，坐标轴触发有效
	            type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
	        }
	    },
	    legend: {
	        data:['滞留','进','出']
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
	            name:'滞留',
	            type:'bar',
	            label: {
	                normal: {
	                    show: true,
	                    position: 'inside'
	                }
	            },
	            data:[100]
	        },
	        {
	            name:'进',
	            type:'bar',
	            stack: '总量',
	            label: {
	                normal: {
	                    show: true
	                }
	            },
	            data:[220]
	        },
	        {
	            name:'出',
	            type:'bar',
	            stack: '总量',
	            label: {
	                normal: {
	                    show: true
	                }
	            },
	            data:[120]
	        }
	    ]
	};
	
	myChart.setOption(option);
	
	function set(x,y,z){
		var hold = [x]; 
		var enter = [y];
		var exit = [z]; 
		 
		option.series[0].data = hold;//滞留
		option.series[1].data = enter;//进
		option.series[2].data = exit;//出
		myChart.setOption(option);
	}
	
	
	$(window).resize(function() {
		$(myChart).resize();
	});
</script>
</body>
</html>
