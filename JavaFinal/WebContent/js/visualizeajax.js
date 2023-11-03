/**
 * 
 */

var vissetting = function() {
		var chartDom = document.getElementById("visual");
		var myChart = echarts.init(chartDom);
		myChart.clear();
		/* 注意这里要 clear 否则生成的图表可能会有上一个图表的残余 */
		
		var toShow = function(cur) {
			if(cur == 'CN') return "中国";
			else if(cur == 'US') return "美国";
			else if(cur == 'GB') return "英国";
			else if(cur == 'FR') return "法国";
			else if(cur == 'RU') return "俄罗斯";
			else return "Invalid";
		}
		
		var countryNull = ($("#country").val() == "null" || $("#country").val() == "");
		var yearNull = ($("#year").val() == "null" || $("#year").val() == "");
		var valueNull = ($("#name").val() == "null" || $("#name").val() == "");
		
		var colorSet = function(params) {
				                        var colorList = ['#C73866','#FE676E','#FD8F52', '#FFBD71','#FFDCA2'];
				                        return colorList[params.dataIndex % 5]
		};
		var options, restype = 0;
		if(!valueNull && !yearNull && countryNull) {
			options = options_singlebar;
			restype = 1;
		}
		if(!valueNull && yearNull && !countryNull) {
			options = options_singleline;
			restype = 2;
		}
		if(!valueNull && yearNull && countryNull) {
			if(preference_bar) {
				options = options_multbar;
				restype = 3;
			}
			else {
				options = options_multline;
				restype = 4;
			}
		}
		if(valueNull && yearNull && !countryNull) {
			restype = 5;
			options = options_countryIndex;
		}
		if(valueNull && countryNull && !yearNull) {
			restype = 6;
			options = options_yearIndex;
		}
		if(valueNull && !yearNull &&!countryNull) {
			restype = 7;
			options = options_yearcountryIndex;
		}
		myChart.setOption(options);
		var dt = $('#thisForm').serialize() + "&type=" + restype
		$.ajax({
			type:"POST",
			url: "AutoVis",
			data :dt,
			dataType:"json",
			success: function (result) {
				if(restype == 1) {
					myChart.setOption( {
						title: {
							text: $("#year").val() + "年各国家" + $("#name").val() + "得分统计",
						},
						legend: {
						  orient: 'vertical',
						  right: 'right',
						},
						series : [ {
							data: result.scores,
							itemStyle: {
				                normal: {
				                    color: colorSet,
				                }
				            },
						}]
					})
				}
				else if(restype == 2) {
					myChart.setOption( {
						title: {
							text: toShow($("#country").val()) + "各年份" + $("#name").val() + "得分统计",
						},
						 legend: {
						 orient: 'vertical',
						 right: 'right',
						},
						series : [ {
							data: result.scores,
						}]
					})
				}
				else if(restype == 3) {
					myChart.setOption( {
						title: {
							text: $("#name").val() + "按国家的得分统计",
						},
						series : [
							{ name: '2019', data: result.scores[0] },
							{ name: '2020', data: result.scores[1] },
							{ name: '2021', data: result.scores[2] },
							]
					})
				}
				else if(restype == 4) {
					myChart.setOption( {
						title: {
							text: $("#name").val() + "按年份的得分统计",
						},
						series : [
							{ name: '中国', data: result.scores[0] },
							{ name: '美国', data: result.scores[1] },
							{ name: '英国', data: result.scores[2] },
							{ name: '法国',  data: result.scores[3] },
							{ name: '俄罗斯', data: result.scores[4] },
							]
					})
				}
				else if(restype == 5) {
					var xAxis_cur = result.names;
					var values_cur = result.scores;
					options.xAxis.data = xAxis_cur;
					myChart.setOption(options);
					myChart.setOption({
						title: {
							text: toShow($("#country").val()) + "各年份各指标得分统计",
						},
					 legend: {
						data: [
							{ name: '2019', icon: 'rect', },
							{ name: '2020', icon: 'rect', },
							{ name: '2021', icon: 'rect', },
						],
						orient: 'horizonal',
						right: 'right',
					},
					 series: [
					    {
					      name: '2019',
					      type: 'bar',
					      data: values_cur[0],
					    },
					    {
					      name: '2020',
					      type: 'bar',
					      data: values_cur[1],
					    },
					    {
					      name: '2021',
					      type: 'bar',
					      data: values_cur[2],
					    },
					  ]
					})
				}
				else if(restype == 6) {
					var xAxis_cur = result.names;
					var values_cur = result.scores;
					options.xAxis.data = xAxis_cur;
					myChart.setOption(options);
					myChart.setOption({
						title: {
							text: $("#year").val() + "年各国家各指标得分统计",
						},
					 legend: {
						data: [
							{ name: '中国', icon: 'rect', },
							{ name: '美国', icon: 'rect', },
							{ name: '英国', icon: 'rect', },
							{ name: '法国', icon: 'rect', },
							{ name: '俄罗斯', icon: 'rect',}
						],
						orient: 'vertical',
						right: 'right',
					},
					 series: [
					    {
					      name: '中国',
					      type: 'bar',
					      data: values_cur[0],
					    },
					    {
					      name: '美国',
					      type: 'bar',
					      data: values_cur[1],
					    },
					    {
					      name: '英国',
					      type: 'bar',
					      data: values_cur[2],
					    },,
					    {
					      name: '法国',
					      type: 'bar',
					      data: values_cur[3],
					    },
					    {
					      name: '俄罗斯',
					      type: 'bar',
					      data: values_cur[4],
					    },
					  ]
					})
				}
				else if(restype == 7) {
					var xAxis_cur = result.names;
					var values_cur = result.scores;
					options.xAxis.data = xAxis_cur;
					myChart.setOption(options);
					myChart.setOption({
						title: {
							text: $("#year").val() + "年" + toShow($("#country").val()) + "各指标得分统计",
						},
						 legend: {
							orient: 'vertical',
							right: 'right',
						 },
						 series: [
						    {
						      name: 'score',
						      type: 'bar',
						      data: values_cur,
						      itemStyle: {
				                normal: {
				                    color: colorSet,
				                }
				            },
						    }
						  ]
					})
				}
				else alert("failed");
			},
			error: function(errMsg){
				alert("bad chart!");
				myChart.hideLoading();
			}
		});
	};
var Test = function() {
	var countryNull = ($("#country").val() == "null" || $("#country").val() == "");
	var yearNull = ($("#year").val() == "null" || $("#year").val() == "");
	var valueNull = ($("#name").val() == "null" || $("#name").val() == "");
		
	var submit_succ = (!valueNull) || (!yearNull) || (!countryNull);
	if((!valueNull) && (!yearNull) && (!countryNull)) submit_succ = false;
	if((!yearNull) || (!countryNull)) clicked_submit = false;
	var recreate_succ = countryNull && yearNull && (!valueNull) && (clicked_submit);
	
	if(recreate_succ) $("#recreate").css("visibility", "visible");
	if(!recreate_succ) $("#recreate").css("visibility", "hidden");
	if(submit_succ) $("#thissubmit").css("visibility", "visible");
	if(!submit_succ) $("#thissubmit").css("visibility", "hidden");
};