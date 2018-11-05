var colonyname;
var servicename;
var serviceversion;
$(document).ready(function() {
    $('#win').window('close');
    $('#warninginfo_alter').hide();
    $('#info_alter').hide();
    initSelectData();
    init_Service_Data();
});

/**
 * 初始化下拉列表
 */
function initSelectData() {

    $('#service_trees').tree({
        type: "GET",
        url:"http://10.72.179.188:8080/getTree",
        loadFilter: function(rows){
            return convert(rows);
        },
        onClick:function (node) {
            load_FW_Data(node.text);
        },
        onContextMenu: function(e, node) { //给结点添加右键菜单
            e.preventDefault();
            $('#menu_right').menu('show',{
                left:e.pageX,
                top:e.pageY,
                onClick:function (name) {
                    switch (name.text) {
                        case "新建文件":
                            node_name = node.text;
                            $('#add_service_group_child_dialog').modal('show');
                            break;
                        case "删除文件":
                            node_name = node.text;
                            delete_file_btn();
                            break;
                        case "注册服务":
                            node_name = node.text;
                            insert_service_btn();
                            break;
                    }
                }
            });
        }
    });
}

/**
 * 处理树形菜单中的父\子节点
 * @param rows
 * @returns {Array}
 */
function convert(rows){
    function exists(rows, parentid){
        for(var i=0; i<rows.length; i++){
            if (rows[i].id == parentid) return true;
        }
        return false;
    }

    var nodes = [];
    // get the top level nodes
    for(var i=0; i<rows.length; i++){
        var row = rows[i];
        if (!exists(rows, row.parentid)){
            nodes.push({
                id:row.id,
                text:row.name
            });
        }
    }

    var toDo = [];
    for(var i=0; i<nodes.length; i++){
        toDo.push(nodes[i]);
    }
    while(toDo.length){
        var node = toDo.shift();	// the parent node
        // get the children nodes
        for(var i=0; i<rows.length; i++){
            var row = rows[i];
            if (row.parentid == node.id){
                var child = {id:row.id,text:row.name};
                if (node.children){
                    node.children.push(child);
                } else {
                    node.children = [child];
                }
                toDo.push(child);
            }
        }
    }
    return nodes;
}

function init_Service_Data() {
    var rows = [];
    $.ajax({
        type: "GET",
        url : "/getService",
        data : {},
        success : function(data) {
            for(var i = 0;i<data.length;i++) {
                var num = i+1;
                if(data[i]["servicestatus"] == "运行") {
                    rows.push({
                        id: num,
                        servicename: data[i]["servicename"],
                        serviceversion: data[i]["serviceversion"],
                        colonyname:data[i]["colonyname"],
                        servicestatus:data[i]["servicestatus"],
                        serviceoperation: '<button type="button" onclick="serviceinfo_btn()" class="btn btn-primary">详情</button>&nbsp</button>&nbsp<button type="button" onclick="servicestop_btn()" class="btn btn-warning">停止</button>'
                    });
                }else {
                    rows.push({
                        id: num,
                        servicename: data[i]["servicename"],
                        serviceversion: data[i]["serviceversion"],
                        colonyname:data[i]["colonyname"],
                        servicestatus:data[i]["servicestatus"],
                        serviceoperation: '<button type="button" onclick="servicestart_btn()" class="btn btn-success">发布</button>&nbsp<button type="button" onclick="deleteservice_btn()" class="btn btn-danger">删除</button>'
                    });
                }
            }
            $("#applyservice_tbd").datagrid('loadData',rows);
        },
        error : function() {
            warning_alterData("失败：服务加载失败!");
        }
    });
}

/**
 * 加载服务数据表格
 * @param servicename
 */
function load_FW_Data(nodename) {
    var rows = [];
        $.ajax({
            type: "GET",
            url : "/getServiceByGroupname",
            data : {"nodename":nodename},
            success : function(data) {
                for(var i = 0;i<data.length;i++) {
                    var num = i+1;
                    if(data[i]["servicestatus"] == "运行") {
                        rows.push({
                            id: num,
                            servicename: data[i]["servicename"],
                            serviceversion: data[i]["serviceversion"],
                            colonyname:data[i]["colonyname"],
                            servicestatus:data[i]["servicestatus"],
                            serviceoperation: '<button type="button" onclick="serviceinfo_btn()" class="btn btn-primary">详情</button>&nbsp</button>&nbsp<button type="button" onclick="servicestop_btn()" class="btn btn-warning">停止</button>'
                        });
                    }else {
                        rows.push({
                            id: num,
                            servicename: data[i]["servicename"],
                            serviceversion: data[i]["serviceversion"],
                            colonyname:data[i]["colonyname"],
                            servicestatus:data[i]["servicestatus"],
                            serviceoperation: '<button type="button" onclick="servicestart_btn()" class="btn btn-success">发布</button>&nbsp<button type="button" onclick="deleteservice_btn()" class="btn btn-danger">删除</button>'
                        });
                    }
                }
                $("#applyservice_tbd").datagrid('loadData',rows);
            },
            error : function() {
                warning_alterData("失败：服务加载失败!");
            }
        });
}

/**
 * 获取服务详情
 */
function serviceinfo_btn() {
    $('#info_alter').hide();
    var row = getCheckBoxSum();
        $.ajax({
            type: "GET",
            url : "/getServiceInfo",
            data : {"servicename":row[0]["servicename"],"serviceversion":row[0]["serviceversion"]},
            success : function(data) {
                document.getElementById("win").innerHTML = '<object type="text/html" data="http://127.0.0.1:'+data[0]["serviceport"]+'/swagger-ui.html#/" width="100%" height="100%"></object>';
                $('#win').window('open');
            },
            error : function () {
                alert("失败：服务详情加载失败!");
            }
        });
}


/**
 * 服务发布按钮
 */
function servicestart_btn() {
    $('#info_alter').hide();
    $('#warninginfo_alter').hide();
    var row = getCheckBoxSum();
    servicename = row[0]["servicename"];
    serviceversion = row[0]["serviceversion"];
    var rows = [];
    $.ajax({
        type: "GET",
        url : "/getColonyName",
        data : {},
        success : function(data) {
            for(var i = 0;i<data.length;i++) {
                var num = i+1;
                rows.push({
                    id: num,
                    text: data[i]
                });
            }
            $("#colony_select").combobox({
                prompt:'选择集群',
                data: rows,
                valueField: 'id',
                textField: 'text',
                onSelect: function(data){
                    colonyname = data["text"];
                }
            });
            $('#fabu_service_dialog').modal('show');
        },
        error : function() {
            alert('失败：注册服务加载失败!')
        }
    });
}

/**
 * 发布提交按钮
 */
function submit_btn() {
    var maxsrc = $("#maxsrc").val();
    $('#fabu_service_dialog').modal('hide');
    $('#load_dialog').modal('show');
    $.ajax({
        type: "GET",
        url : "/updateService",
        traditional: true,
        data : {"servicename":servicename,"serviceversion":serviceversion,"colonyname":colonyname,"maxsrc":maxsrc},
        success : function(data) {
            $.ajax({
                type: "GET",
                url : "/upFile",
                traditional: true,
                data : {"filepath":data,"colonyname":colonyname},
                success : function(data) {

                    if(data) {
                        $('#load_dialog').modal('hide');
                        info_alterData("成功:发布成功!");
                        $.ajax({
                            type: "GET",
                            url : "/startService",
                            traditional: true,
                            data : {"servicename":servicename,"serviceversion":serviceversion},
                            success : function(data) {

                            }
                        });
                    }
                },
                error : function() {
                    alert('失败：文件上传失败!')
                }
            });
        },
        error : function() {
            alert('失败：服务信息数据出错!')
        }
    });
}

/**
 * 服务停止按钮
 */
function servicestop_btn() {
    var row = getCheckBoxSum();
    $('#info_alter').hide();
    $('#warninginfo_alter').hide();
    $('#stop_load_dialog').modal('show');
    $.ajax({
        type: "GET",
        url : "/stopService",
        data : {"servicename":row[0]["servicename"],"serviceversion":row[0]["serviceversion"]},
        success : function(data) {
            if(data) {
                window.location.href="/doyyfw";
            }else {
                warning_alterData("注意:服务停止失败！");
                $('#stop_load_dialog').modal('hide');
            }
        },
        error : function () {
            warning_alterData("注意:服务停止失败！");
        }
    });
}

/**
 * 删除服务按钮
 */
function deleteservice_btn() {
    $('#info_alter').hide();
    $('#warninginfo_alter').hide();
    var row = getCheckBoxSum();
        $.messager.confirm('Confirm','确定要删除此服务?',function(r){
            if (r){
                $.ajax({
                    type: "GET",
                    url : "/deleteService",
                    data : {"servicename":row[0]["servicename"],"serviceversion":row[0]["serviceversion"]},
                    success : function(data) {
                        if(data) {
                            window.location.href="/doyyfw";
                        }
                    },
                    error : function () {
                        warning_alterData("注意:删除失败！");
                    }
                });
            }
        });
}

/**
 * 成功弹窗信息
 * @param context
 */
function info_alterData(context) {
    $('#info_alter').hide();
    new Vue({
        el: "#info_alter",
        data:{
            context:context
        },
    })
    $('#info_alter').show();

}

/**
 * 警告弹窗信息
 * @param context
 */
function warning_alterData(context) {
    $('#warninginfo_alter').hide();
    new Vue({
        el: "#warninginfo_alter",
        data:{
            context:context
        },
    })
    $('#warninginfo_alter').show();

}

function close() {
    alert("1")
}

/**
 * 获得checkbox数量
 */
function getCheckBoxSum() {
    var rows =$("#applyservice_tbd").datagrid('getSelections');
    return rows;
}
