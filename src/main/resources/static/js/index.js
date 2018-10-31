$(document).ready(function() {
    getAgentData();
    getServerData();
    initServerSelect();
});

/**
 * 下拉列表渲染
 */
function initServerSelect() {
    var rows = [];
    $.ajax({
        type: "GET",
        url : "/getService",
        data : {},
        success : function(data) {
            for(var i = 0;i<data.length;i++) {
                var num = i+1;
                rows.push({
                    id: num,
                    text: data[i]["servicename"]
                });
            }
            $("#server_select").combobox({
                prompt:'选择服务',
                data: rows,
                valueField: 'id',
                textField: 'text',
                onSelect: function(data){
                    //addSsrzTab(data["text"],'/dossrzlog',data["text"]);
                    deployServer(data["text"]);
                }
            });
        },
        error : function () {
            alert("节点添加失败")
        }
    });
}

/**
 * 获取节点信息
 */
function getAgentData() {
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
                    agentport: data[i]["agentport"],
                    agentstatus: data[i]["agentstatus"]
                });
            }
            $("#tbd").datagrid('loadData',rows);
        },
        error : function() {
            alert('节点加载失败')
        }
    });
}

/**
 * 添加节点操作
 */
function addForm() {
    var agentname = $("#agentname").val();
    var agentaddress = $("#agentaddress").val();
    var agentport = $("#agentport").val();
    $('#add_agent_dialog').window('close');
    $.ajax({
        type: "GET",
        url : "/addAgent",
        data : {"agentname":agentname,"agentaddress":agentaddress,"agentport":agentport},
        success : function(data) {
            getAgentData();
            alert("节点添加成功")
        },
        error : function () {
            alert("节点添加失败")
        }
    });
}

/**
 * 启动节点操作
 */
function startAgent() {
    var rows = getCheckBoxSum();
    if(rows.length != 0) {
        for(var i=0;i<rows.length;i++) {
            if(rows[i]["agentstatus"] == "未启动") {
                isStartAgent(rows[i]["agentaddress"],rows[i]["agentport"]);
            }
        }
    }else {
        alert("注意:请选择节点操作");
    }
}

/**
 * 正式启动节点
 */
function isStartAgent(agentaddress,agentport) {
    $.ajax({
        type: "GET",
        url : "/startSingleAgent",
        data : {"agentaddress":agentaddress,"agentport":agentport},
        success : function(data) {
            getAgentData();
        },
        error : function() {
            alert("节点启动失败");
        }
    });
}

/**
 * 停止节点操作
 */
function stopAgent() {
    var rows = getCheckBoxSum();
    if(rows.length != 0) {
        for(var i=0;i<rows.length;i++) {
            if(rows[i]["agentstatus"] == "已启动") {
                isAgentStop(rows[i]["agentaddress"],rows[i]["agentport"]);
            }
        }
    }else {
        alert("注意:请选择节点操作");
    }
}

/**
 * 正式关闭代理节点
 */
function isAgentStop(agentaddress,agentport) {
    $.ajax({
        type: "GET",
        url : "/stopAgent",
        data : {"agentaddress":agentaddress,"agentport":agentport},
        success : function(data) {
            getAgentData();
        },
        error : function() {
            alert("节点关闭失败");
        }
    });
}

/**
 * 删除节点操作
 */
function deleteAgent() {
    $.messager.confirm('Confirm','确定要执行删除操作?',function(r){
        if (r){
            var rows = getCheckBoxSum();
            if(rows.length != 0) {
                for(var i=0;i<rows.length;i++) {
                    if(rows[i]["agentstatus"] == "已启动") {
                        alert("注意:请先关闭已启动的节点");
                    }else {
                        $.ajax({
                            type: "POST",
                            url : "/deleteAgent",
                            data : {"agentaddress":rows[i]["agentaddress"]},
                            success : function(data) {
                                getAgentData();
                                alert("节点删除成功")
                            },
                            error : function() {
                                alert("节点删除失败");
                            }
                        });
                    }
                }
            }else {
                alert("注意:请选择节点操作");
            }
        }
    });
}

/**
 * 获得服务信息
 */
function getServerData() {
    var rows = [];
    $.ajax({
        type: "GET",
        url : "/getServer",
        data : {},
        success : function(data) {
            for(var i = 0;i<data.length;i++) {
                var num = i+1;
                rows.push({
                    id: num,
                    servername: data[i]["servername"],
                    serveraddress: data[i]["serveraddress"],
                    serverstatus: data[i]["serverstatus"]
                });
            }
            $("#server_tbd").datagrid('loadData',rows);
        },
        error : function() {
            alert('服务加载失败')
        }
    });
}

/**
 * 添加设备
 */
function addServerForm() {
    var servername = $("#servername").val();
    var serveraddress = $("#serveraddress").val();
    $('#add_server_dialog').window('close');
    $.ajax({
        type: "GET",
        url : "/addServer",
        data : {"servername":servername,"serveraddress":serveraddress},
        success : function(data) {
            getServerData();
            alert("设备添加成功")
        },
        error : function () {
            alert("设备添加失败")
        }
    });
}

/**
 * 部署服务
 */
function deployServer(servicename) {
    addTab("服务部署", "/dodeploy");
}


/**
 * 删除服务
 */
function deleteServer() {
    $.messager.confirm('Confirm','确定要执行删除操作?',function(r){
        if (r){
            var rows = getServerCheckBoxSum();
            if(rows.length != 0) {
                for(var i=0;i<rows.length;i++) {
                    $.ajax({
                        type: "GET",
                        url : "/deleteServer",
                        data : {"serveraddress":rows[i]["serveraddress"]},
                        success : function(data) {
                            getServerData();
                            alert("服务删除成功")
                        },
                        error : function() {
                            alert("服务删除失败");
                        }
                    });
                }
            }else {
                alert("注意:请选择服务操作");
            }
        }
    });
}

/**
 * 服务详细信息
 */
//var server_name;
function serverInfo() {
    var rows = getServerCheckBoxSum();
    if (rows.length != 0 && rows.length < 2){
        $.ajax({
            type: "GET",
            url: "/setSession",
            data: {"servername":rows[0]["servername"]},
            success: function (data) {
                addTab(rows[0]["servername"], "/doserverinfo");
            },
            error: function () {
                alert('节点加载失败')
            }
        });

    }else {
        alert("注意:最多选择一个节点")
    }
}

/**
 * 添加服务
 */
function addServiceForm() {
    var servicename = $("#servicename").val();
    var descrption = $("#descrption").val();
    var version = $("#version").val();
    var serviceaddress = $("#serviceaddress").val();
    $('#add_serveraddress_dialog').window('close');
    $.ajax({
        type: "GET",
        url : "/addService",
        data : {"servicename":servicename,"descrption":descrption,"version":version,"serviceaddress":serviceaddress},
        success : function(data) {
            alert("服务添加成功")
        },
        error : function () {
            alert("服务添加失败")
        }
    });
}

/**
 * 重置添加服务表单
 */
function resServiceForm() {
    $('#add_service_form').form('clear');
}

/**
 * 刷新服务
 */
function resServer() {
    getServerData();
}

/**
 * tbd重置表单
 */
function restForm() {
    $('#add_agent_form').form('clear');
}
/**
 * server_tbd重置表单
 */
function resServertForm() {
    $('#add_server_form').form('clear');
}

/**
 *获得所选行
 * @param target
 * @returns {number}
 */
function getRowIndex(target){
    var tr = $(target).closest('tr.datagrid-row');
    return parseInt(tr.attr('datagrid-row-index'));
}

/**
 * 获得tbd checkbox数量
 */
function getCheckBoxSum() {
    var rows =$("#tbd").datagrid('getSelections');
    return rows;
}

/**
 * 获得server_tbd checkbox数量
 */
function getServerCheckBoxSum() {
    var rows =$("#server_tbd").datagrid('getSelections');
    return rows;
}
/**
 * 添加标签方法
 * @param title
 * @param url
 */
function addTab(title, url){
    if ($('#centertable').tabs('exists', title)){
        $('#centertable').tabs('select', title);
    } else {
        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%; height:100%; position: absolute;"></iframe>';
        $('#centertable').tabs('add',{
            title:title,
            content:content,
            closable:true
        });
    }
}
