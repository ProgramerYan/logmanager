$(document).ready(function() {
    initView();
});

/**
 * 初始下拉选项信息
 */
function initView() {
    var rows = [];
    $.ajax({
        type: "GET",
        url : "/getAgent",
        data : {},
        success : function(data) {
            for(var i = 0 ;i<data.length;i++) {
                rows.push({
                    id: i,
                    text: data[i]["agentname"]
                });
            }
            $("#log_select").combobox({
                prompt:'选择服务节点',
                data: rows,
                valueField: 'id',
                textField: 'text',
                onSelect: function(data){

                }
            });
        },
        error : function() {
            alert('失败：节点加载失败!')
        }
    });
}

/**
 * 日志查询
 */
function log_search() {
    var i=0;
    var hitsvalue=new Array();
    $.ajax({
        type: "POST",
        contentType: "application/json; charset=utf-8",
        url : "http://127.0.0.1:9200/logtest/_search",
        data: JSON.stringify(getJsonData()),
        dataType: "json",
        success : function(message) {
            //console.log(message);
            hitsvalue=(message.hits).hits;
            setInterval(function() {
                if(i<hitsvalue.length){
                    var str = $('#agentValidate').val() + (hitsvalue[i]._source).message;//
                    $('#agentValidate').val(str+"\n");//
                    var show=document.getElementById("agentValidate");
                    show.scrollTop = show.scrollHeight;
                    i=i+1;
                }
            }, 500);
        },
        error : function() {
            //alert('日志信息查询失败')
        }
    });
}
function getJsonData() {
    var json = {
        "size": 10000,
        "query": {
            "match_all": {}
        }
    };
    return json;
}