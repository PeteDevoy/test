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
<title>进出饼图</title>

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
	#queryForm2,#queryForm3{
		height:0;
		overflow: hidden;
	}
</style>
</head>
<body>

<div class="container-fluid" style="margin-top:30px">
		<div class="row">
			<div class="col-lg-12 col-md-12 col-sm-12">
				<div class="panel-group accordion no-margin-bottom" id="accordion">
					<div class="panel panel-default">
						<div id="searchDiv" class="panel-collapse collapse in">
							<div class="panel-body" style="background: #f5f5f5;">
								<form id="queryForm" class="form-horizontal" role="form">
									<div class="form-group">
										<div>
											<div id="main" style="width: 90%;height:400px;margin:0 auto ;"></div>
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


		// 指定图表的配置项和数据
		option = {
			    tooltip: {
			        trigger: 'item',
			        formatter: "{a} <br/>{b}: {c} ({d}%)"
			    },
			    legend: {
			        orient: 'vertical',
			        x: 'left',
			        data:['进入量','流出量']
			    },
			    series: [
			        {
			            name:'访问来源',
			            type:'pie',
			            selectedMode: 'single',
			            radius: [0, '60%'],

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
			                        color: '2c343c'
			                    },
			                    smooth: 0.2,
			                    length: 50,
			                    length2: 20
			                }
			            },
			            data:[
			                {value:335, name:'进入量', selected:true},
			                {value:679, name:'流出量'},
			          
			            ]
			        },
			      
			    ]

		};
		
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
		
	$(window).resize(function() {
		$(myChart).resize();

	});
	
	</script>
</body>
</html>
