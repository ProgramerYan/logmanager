var child_select;
var node_name;
$(document).ready(function() {
    $('#warninginfo_alter').hide();
    $('#info_alter').hide();
    initServiceData();
    initTree();
});

/**
 * 初始化树形菜单
 */
function initTree() {
    $('#service_tree').tree({
        type: "GET",
        url:"http://10.72.179.188:8080/getTree",
        loadFilter: function(rows){
            return convert(rows);
        },
        onClick:function (node) {
            initServiceByGroupData(node.text);
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
 * 新建文件菜单按钮
 */
function creat_file_btn() {
    $('#info_alter').hide();
    $('#warninginfo_alter').hide();
    var filename = $("#filename").val();
    $.ajax({
        type: "GET",
        url : "/addGroupChild",
        data : {"fathername":node_name,"filename":filename},
        success : function(data) {
            if(data) {
                window.location.href="/dozcfw";
            }else {
                warning_alterData("失败:文件名已存在!");
            }
        }
    });
}

/**
 * 删除文件菜单按钮
 */
function delete_file_btn() {
    $.ajax({
        type: "GET",
        url : "/deleteServiceGroup",
        data : {"nodename":node_name},
        success : function(data) {
            if(data) {
                window.location.href="/dozcfw";
            }
        }
    });
}

/**
 * 注册服务菜单按钮
 */
function insert_service_btn() {
    $('#deploy_service_dialog').modal('show');
}

/**
 * 注册服务提交按钮
 */
function add_service_btn() {
    $('#info_alter').hide();
    $('#warninginfo_alter').hide();
    info_val = [];
    info_val.push($("#servicename").val());
    info_val.push($("#serviceversion").val());
    info_val.push($("#serviceport").val());
    info_val.push($("#descrption").val());
    info_val.push($("#filepath").val());
    info_val.push(node_name);

    $.ajax({
        type: "GET",
        url : "/addService",
        traditional: true,
        data : {"serviceinfo":info_val},
        success : function(data) {
            window.location.href="/dozcfw";
        }
    });
}

/**
 * 添加群组信息提交按钮
 */
function addGroup() {
    $('#info_alter').hide();
    $('#warninginfo_alter').hide();
    var filename = $("#groupname").val();
    $.ajax({
        type: "GET",
        url : "/addServiceGroup",
        data : {"filename":filename},
        success : function(data) {
            if(data) {
                window.location.href="/dozcfw";
            }else {
                warning_alterData("失败:服务群组已存在!");
            }
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

/**
 * 初始化服务表信息
 */
function initServiceData() {
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
                    servicename: data[i]["servicename"],
                    serviceversion: data[i]["serviceversion"],
                    descrption:data[i]["descrption"],
                    serviceoperation: '<button type="button" onclick="deleteservice_btn()" class="btn btn-danger">删除</button>'
                });
            }
            $("#service_tbd").datagrid('loadData',rows);
        },
        error : function() {
            warning_alterData("失败：服务加载失败!");
        }
    });
}

/**
 * 根据群组名称，加载服务表信息
 */
function initServiceByGroupData(nodename) {
    var rows = [];
    $.ajax({
        type: "GET",
        url : "/getServiceByGroupname",
        data : {"nodename":nodename},
        success : function(data) {
            for(var i = 0;i<data.length;i++) {
                var num = i+1;
                rows.push({
                    id: num,
                    servicename: data[i]["servicename"],
                    serviceversion: data[i]["serviceversion"],
                    descrption:data[i]["descrption"],
                    serviceoperation: '<button type="button" onclick="deleteservice_btn()" class="btn btn-danger">删除</button>'
                });
            }
            $("#service_tbd").datagrid('loadData',rows);
        }
    });
}

//
// /**
//  * 添加群组子类提交按钮
//  */
// function add_service_child() {
//     var service_select = document.getElementById("groupname_select");
//     var service_options = service_select.options;
//     var service_index = service_select.selectedIndex;
//     var secondname = $("#secondname").val();
//     $.ajax({
//         type: "GET",
//         url : "/addGroupChild",
//         data : {"groupname":service_options[service_index].text,"secondname":secondname},
//         success : function(data) {
//                 window.location.href="/dozcfw";
//         }
//     });
// }


/**
 * 表格中 删除单个服务信息按钮
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
                        window.location.href="/dozcfw";
                    } else {
                        warning_alterData("失败：服务可能正在运行，请联系管理员删除!");
                    }

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

/**
 * 获得checkbox数量
 */
function getCheckBoxSum() {
    var rows =$("#service_tbd").datagrid('getSelections');
    return rows;
}
