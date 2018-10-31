
$(document).ready(function() {
    initView();
});

/**
 * 初始化图表下拉选项信息
 */
function initView() {
    var zxtb_rows = [];
    var zztb_rows = [];
    var bztb_rows = [];
    $.ajax({
        type: "GET",
        url : "/getTable",
        data : {},
        success : function(data) {
            for(var i = 0 ;i<data.length;i++) {
                if(data[i]["tabletype"] == "折线图") {
                    zxtb_rows.push({
                        id: i,
                        text: data[i]["tablename"]
                    });
                }
            }
            for(var j = 0;j<data.length;j++) {
                if(data[j]["tabletype"] == "柱状图") {
                    zztb_rows.push({
                        id: j,
                        text: data[j]["tablename"]
                    });
                }
            }
            for(var k = 0;k<data.length;k++) {
                if(data[k]["tabletype"] == "饼状图") {
                    bztb_rows.push({
                        id: k,
                        text: data[k]["tablename"]
                    });
                }
            }
            $("#zxtb_select").combobox({
                prompt:'选择表名',
                data: zxtb_rows,
                valueField: 'id',
                textField: 'text',
                onLoadSuccess:function(data) {
                    $("#zxtb_select").combobox("setValue",data[0].text);
                    init_ZXTB_Chart(data[0].text);
                },
                onSelect: function(data){
                    init_ZXTB_Chart();
                }
            });

            $("#zztb_select").combobox({
                prompt:'选择表名',
                data: zztb_rows,
                valueField: 'id',
                textField: 'text',
                onLoadSuccess:function(data) {
                    $("#zztb_select").combobox("setValue",data[0].text);
                    init_ZZTB_Chart(data[0].text);
                },
                onSelect: function(data){
                    init_ZZTB_Chart("");
                }
            });

            $("#bztb_select").combobox({
                prompt:'选择表名',
                data: bztb_rows,
                valueField: 'id',
                textField: 'text',
                onLoadSuccess:function(data) {
                    $("#bztb_select").combobox("setValue",data[0].text);
                    init_BZTB_Chart(data[0].text);
                },
                onSelect: function(data){
                    init_BZTB_Chart();
                }
            });

        },
        error : function() {
            alert('失败：图表信息加载失败!')
        }
    });
}

/**
 * 折线图
 * @param colony
 */
function init_ZXTB_Chart(colony) {
    var list = new Array();
    for(var i = 0;i<6;i++) {
        var amount = Math.floor(Math.random() * 100);
        list[i] = amount;
    }
    var mainview = echarts.init(document.getElementById("chart_zxtb"));
    mainview.setOption({
        xAxis: {
            axisTick: {show: false},
            axisLine:{
                lineStyle:{
                    color:'#337ab7',
                    width:1,
                }
            } ,
            type: 'category',
            data: ['一月','二月','三月','四月',"五月","六月"]
        },
        label: {
            show: true,
            position: 'top',
            color:'#337ab7',

        },
        yAxis: {
            axisLine: {
                show: false,
                lineStyle:{
                    color:'#337ab7'
                }
            },
            axisTick: {show: false},
            type: 'value'
        },
        series: [{
            itemStyle : {
                normal : {
                    lineStyle:{
                        color:'#337ab7'
                    }
                }
            },
            data: list,
            type: 'line'
        }]
    });
}

/**
 * 柱状图
 * @param colony
 */
function init_ZZTB_Chart(colony) {
    var list = new Array();
    for(var i = 0;i<24;i++) {
        var amount = Math.floor(Math.random() * 100);
        list[i] = amount;
    }
    var mainview = echarts.init(document.getElementById("chart_zztb"));
    mainview.setOption({
        //定义一个标题
        color:"#337ab7",
        legend:{
            data:['日志数量']
        },
        //X轴设置
        xAxis:{
            data:['0','1','2','3','4','5','6','7','8','9','10','0','11','12','13','14','15','16','17','18','19','20','21','22','23']
        },
        yAxis:{
        },
        series:[{
            name:'日志数量',
            type:'bar',
            data:list
        }]
    });
}

/**
 * 饼状图
 * @param colony
 */
function init_BZTB_Chart(colony) {
    var list = new Array();
    for(var i = 0;i<24;i++) {
        var amount = Math.floor(Math.random() * 100);
        list[i] = amount;
    }
    var mainview = echarts.init(document.getElementById("chart_bztb"));
    mainview.setOption({
        tooltip: {
            trigger: 'item',
            formatter: "{a} <br/>{b}: {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            x: 'left',
            data:['异常日志','错误日志','正常日志']
        },
        series: [
            {
                name:'错误占比',
                type:'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                    normal: {
                        show: false,
                        position: 'center'
                    },
                    emphasis: {
                        show: true,
                        textStyle: {
                            fontSize: '30',
                            fontWeight: 'bold'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        show: false
                    }
                },
                data:[
                    {value:335, name:'异常日志'},
                    {value:234, name:'错误日志'},
                    {value:1548, name:'正常日志'}
                ]
            }
        ]
    });
}

/**
 * 初始化弹窗集群下拉列表数据
 */
function addTable() {
    var select = document.getElementById("colony_select");
    $.ajax({
        type: "GET",
        url : "/getColonyName",
        data : {},
        success : function(data) {
            for(var i = 0;i<data.length;i++) {
                var opt = document.createElement("option");
                opt.innerHTML = data[i];
                select.appendChild(opt);
            }
            $('#add_table_dialog').modal('show');
        },
        error : function() {
            alert('失败：集群信息加载失败!')
        }
    });
}

function submit_btn() {
    var table_select = document.getElementById("table_select");
    var colony_select = document.getElementById("colony_select");
    var table_options = table_select.options;
    var colony_options = colony_select.options;
    var table_index = table_select.selectedIndex;
    var colony_index = colony_select.selectedIndex;
    $.ajax({
        type: "GET",
        url : "/addTable",
        data : {"tablename":$("#tablename").val(),"colonyname":colony_options[colony_index].text,"tabletype":table_options[table_index].text,"descrption":$("#descrption").val()},
        success : function(data) {
            window.location.href="/dorztb";
        },
        error : function() {
            alert('失败：图表创建失败!')
        }
    });
}

function deleteTable() {
    var select = document.getElementById("tablename_select");
    $.ajax({
        type: "GET",
        url : "/getTable",
        data : {},
        success : function(data) {
            for(var i = 0;i<data.length;i++) {
                var opt = document.createElement("option");
                opt.innerHTML = data[i]["tablename"];
                select.appendChild(opt);
                $('#delete_charttable_dialog').modal('show');
            }
        },
        error : function() {
            alert('失败：图表信息加载失败!')
        }
    });
}

function delete_btn() {
    var select = document.getElementById("tablename_select");
    var options = select.options;
    var index = select.selectedIndex;
    $.ajax({
        type: "GET",
        url : "/deleteTable",
        data : {"tablename":options[index].text},
        success : function(data) {
            window.location.href="/dorztb";
            alert('成功：图表信息删除成功!')
        },
        error : function() {
            alert('失败：图表信息删除失败!')
        }
    });
}