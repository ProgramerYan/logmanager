$(document).ready(function() {
    initSysCount();
    initView();
});

/**
 * 初始化系统数目
 */
function initSysCount() {
    $.ajax({
        type: "GET",
        url : "/getInfoCounts",
        data : {},
        success : function(data) {
            new Vue({
                el: "#sysinfo",
                data:{
                    agentcount:data[0]["agentcount"],
                    servicecount:data[0]["servicecount"]
                },
            })
        },
        error : function() {
            alert('失败：系统数目加载失败!')
        }
    });
}
/**
 * 初始化界面数据
 */
function initView() {
    var list = new Array();
    var width = new Array();
    for(var i = 0;i<4;i++) {
        var amount = Math.floor(Math.random() * 100);
        list[i] = "服务器"+i;
        width[i] = "width:"+amount+"%";
    }
    new Vue({
        el: "#root",
        data:{
            width:width,
            context:"",
            list: list
        },
    })
}

