options_singlebar = {
  title: {
    text: 'ECharts singlebar'
  },
  legend: {
	orient: 'vertical',
	right: 'right',
  },
  tooltip: { label: {show:true} },
  xAxis: {
    data: ["中国", "美国", "英国", "法国", "俄罗斯"]
  },
  yAxis: {},
  series: [
    {
      name: 'score',
      type: 'bar',
      data: []
    }
  ]
}

options_singleline = {
  title: {
    text: 'ECharts singleline'
  },
  legend: {
	orient: 'vertical',
	right: 'right',
  },
  tooltip: { label: {show:true} },
  xAxis: {
    data: ['2019', '2020', '2021']
  },
  yAxis: {},
  series: [
    {
      name: 'score',
      type: 'line',
      data: []
    }
  ]
}

options_multline = {
		title: { text: 'ECharts multiple line' },
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
	    tooltip: { label: {show:true} },
	    xAxis: { data: ['2019', '2020', '2021'], type: 'category' },
		yAxis: { },
		series: [
			{ type: 'line',data: [] }, { type: 'line',data: [] }, { type: 'line',data: [] }, { type: 'line',data: [] }, { type: 'line',data: []}
		]
}

options_multbar = {
	title: { text: 'ECharts multiple bar' },
	legend: {
		data: [
			{ name: '2019', icon: 'rect', },
			{ name: '2020', icon: 'rect', },
			{ name: '2021', icon: 'rect', },
		],
		orient: 'horizonal',
		right: 'right',
	},
	tooltip: { label: {show:true} },
	xAxis: {
		data: ['中国', '美国', '英国', '法国', '俄罗斯'], type: 'category'
	},
	yAxis:{},
	series: [
		{
			type: 'bar',
			data: []
		}, 
		{
			type: 'bar',
			data: []
		}, 
		{
			type: 'bar',
			data: []
		}, 
	]
}

options_yearcountryIndex = {
	title: { text: 'ECharts index by year and country' },
	tooltip: { label: {show: true} },
	xAxis: {
		data: [], type: 'category'
	},
	yAxis:{},
	series: []
}

options_yearIndex = {
	title: { text: 'ECharts index by year' },
	tooltip: { label: {show: true} },
	xAxis: {
		data: [], type: 'category'
	},
	yAxis:{},
	series: []
}

options_countryIndex = {
	title: { text: 'ECharts index by country' },
	tooltip: { label: {show: true} },
	xAxis: {
		data: [], type: 'category'
	},
	yAxis:{},
	series: []
}