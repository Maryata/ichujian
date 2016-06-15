<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>mokey后台管理系統</title>

<script src="../js/jquery/jquery-1.8.x.min.js"></script>
<script src="../js/Highcharts-4.0.3/js/highcharts.js"></script>
<script src="../js/Highcharts-4.0.3/js/highcharts-3d.js"></script>
<!--<script src="../js/Highcharts-4.0.3/js/themes/gray.js"></script>


 --><script type="text/javascript"> 
 var dataurl='<%=request.getContextPath() %>/basedata/zingchartDemo.action?method=getFlashChartData';
$(function () { 
	jQuery.get(dataurl,'',function(data){
		$('#container').highcharts(data);
	});
  }
)

$(function () {
    // Set up the chart
    var chart = new Highcharts.Chart({
        chart: {
            renderTo: 'container1',
            type: 'column',
            margin: 75,
            options3d: {
                enabled: true,
                alpha: 15,
                beta: 15,
                depth: 50,
                viewDistance: 25
            }
        },
        title: {
            text: 'Chart rotation demo'
        },
        subtitle: {
            text: 'Test options by dragging the sliders below'
        },
        plotOptions: {
            column: {
                depth: 25
            }
        },
        series: [{
            data: [29.9, 71.5, 106.4, 129.2, 144.0, 176.0, 135.6, 148.5, 216.4, 194.1, 95.6, 54.4]
        }]
    });
    

    // Activate the sliders
    $('#R0').on('change', function(){
        chart.options.chart.options3d.alpha = this.value;
        showValues();
        chart.redraw(false);
    });
    $('#R1').on('change', function(){
        chart.options.chart.options3d.beta = this.value;
        showValues();
        chart.redraw(false);
    });

    function showValues() {
        $('#R0-value').html(chart.options.chart.options3d.alpha);
        $('#R1-value').html(chart.options.chart.options3d.beta);
    }
    showValues();
});

$(function () {
    $('#container2').highcharts({
        chart: {
            type: 'pie',
            options3d: {
                enabled: true,
                alpha: 45,
                beta: 0
            }
        },
        title: {
            text: 'Browser market shares at a specific website, 2014'
        },
        tooltip: {
            pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
        },
        plotOptions: {
            pie: {
                allowPointSelect: true,
                cursor: 'pointer',
                depth: 35,
                dataLabels: {
                    enabled: true,
                    format: '{point.name}'
                }
            }
        },
        series: [{
            type: 'pie',
            name: 'Browser share',
            data: [
                ['Firefox',   45.0],
                ['IE',       26.8],
                {
                    name: 'Chrome',
                    y: 12.8,
                    sliced: true,
                    selected: true
                },
                ['Safari',    8.5],
                ['Opera',     6.2],
                ['Others',   0.7]
            ]
        }]
    });
});

$(function () {
    $('#container3').highcharts({
        title: {
            text: 'Monthly Average Temperature',
            x: -20 //center
        },
        subtitle: {
            text: 'Source: WorldClimate.com',
            x: -20
        },
        xAxis: {
            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        },
        yAxis: {
            title: {
                text: 'Temperature (°C)'
            },
            plotLines: [{
                value: 0,
                width: 1,
                color: '#808080'
            }]
        },
        tooltip: {
            valueSuffix: '°C'
        },
        legend: {
            layout: 'vertical',
            align: 'right',
            verticalAlign: 'middle',
            borderWidth: 0
        },
        series: [{
            name: 'Tokyo',
            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
        }, {
            name: 'New York',
            data: [-0.2, 0.8, 5.7, 11.3, 17.0, 22.0, 24.8, 24.1, 20.1, 14.1, 8.6, 2.5]
        }, {
            name: 'Berlin',
            data: [-0.9, 0.6, 3.5, 8.4, 13.5, 17.0, 18.6, 17.9, 14.3, 9.0, 3.9, 1.0]
        }, {
            name: 'London',
            data: [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3, 6.6, 4.8]
        }]
    });
});
				
$(function () {
    $('#container5').highcharts({"plotOptions":{"pie":{"allowPointSelect":true,"dataLabels":{"enabled":true,"format":"{point.name}"},"cursor":"pointer","depth":35,"showInLegend":true}},"series":[{"name":" ","data":[["一键",1006406],["二键",1042],["三键",500],["四键",0]],"type":"pie"}],"title":{"text":"2014-11到2014-12 1号键单击设置情况","style":{"font-style":"arial","font-size":"16px","font-color":"#000000","font-family":"微软雅黑"}},"chart":{"options3d":{"enabled":true,"alpha":45,"beta":0},"type":"pie"},"tooltip":{"pointFormat":"{series.name}: <b>{point.percentage:.1f}%<\/b>"}});
});

</script>
 
</head>

<body>

<div id="container"></div>

<div id="container1" style="min-width:700px;height:400px"></div>
﻿	<div id="sliders" style="min-width:310px;max-width: 800px;margin: 0 auto;">
	<table>
		<tr><td>Alpha Angle</td><td><input id="R0" type="range" min="0" max="45" value="15"/> <span id="R0-value" class="value"></span></td></tr>
		<tr><td>Beta Angle</td><td><input id="R1" type="range" min="0" max="45" value="15"/> <span id="R1-value" class="value"></span></td></tr>
	</table>
</div>
	
 <div id="container2" style="min-width:700px;height:400px"></div>

<div id="container3" style="min-width:700px;height:400px"></div>

<div id="container5" style="min-width:700px;height:400px"></div>
</body>
</html>
