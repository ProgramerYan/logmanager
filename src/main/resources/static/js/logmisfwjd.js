$(document).ready(function() {
   initData();
});

function initView() {
    $.ajax({
        type: "GET",
        url : "/dofwjd",
        data : {},
        success : function(data) {
        },
        error : function() {
        }
    });
}

/**
 * 初始化节点数据
 */
function initData() {
    var rows = [];
    $.ajax({
        type: "GET",
        url : "/getAgent",
        data : {},
        success : function(data) {
            for(var i = 0;i<data.length;i++) {
                var num = i+1;
                rows.push({
                    id: num,
                    agentname: data[i]["agentname"],
                    agentaddress: data[i]["agentaddress"],
                    agentoperation: '<button type="button" onclick="info_btn()" class="btn btn-warning">详情</button>&nbsp<button type="button" onclick="delete_btn()" class="btn btn-danger">删除</button>'
                });
            }
            $("#agent_tbd").datagrid('loadData',rows);
        },
        error : function() {
            alert('节点加载失败')
        }
    });
}


/**
 * 详情按钮
 */
function info_btn() {

}

/**
 * 删除按钮
 */
function delete_btn() {
    $.messager.confirm('Confirm','确定要删除此节点?',function(r){
        if (r){
            var row = getCheckBoxSum();
            $.ajax({
                type: "POST",
                url : "/deleteAgent",
                data : {"agentaddress":row[0]["agentaddress"]},
                success : function(data) {
                    window.location.href="/dofwjd";
                },
                error : function () {
                    alert("节点删除失败")
                }
            });
        }
    });
}

/**
 * 添加节点
 */
function submit_agent() {
    var agentname = $("#agentname").val();
    var agentaddress = $("#agentaddress").val();
    $.ajax({
        type: "GET",
        url : "/addAgent",
        data : {"agentname":agentname,"agentaddress":agentaddress},
        success : function(data) {
            if(data) {
                window.location.href="/dofwjd";
            }
        },
        error : function () {
            alert("节点添加失败")
        }
    });
}

/**
 * 获得checkbox数量
 */
function getCheckBoxSum() {
    var rows =$("#agent_tbd").datagrid('getSelections');
    return rows;
}