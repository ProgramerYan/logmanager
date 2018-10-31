var nam;
$(document).ready(function() {
    init_colonyselect();
});

/**
 * 初始化集群下拉列表
 */
function init_colonyselect() {
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
                onLoadSuccess:function(data) {
                    $("#colony_select").combobox("setValue",data[0].text);
                    nam = data[0].text;
                    init_tabledata(data[0].text);
                },
                onSelect: function(data){
                    nam = data["text"];
                    init_tabledata(data["text"]);
                }
            });
        },
        error : function () {
            alert("集群添加失败")
        }
    });
}

/**
 * 动态加载表格
 * @param colonyname
 */
function init_tabledata(colonyname) {
    var rows = [];
    $.ajax({
        type: "GET",
        url : "/getColonyData",
        data : {"colonyname":colonyname},
        success : function(data) {
            for(var i = 0;i<data.length;i++) {
                var num = i+1;
                rows.push({
                    id: num,
                    agentname: data[i]["agentname"],
                    agentaddress: data[i]["agentaddress"],
                    agentoperation: '<button type="button" onclick="deleteagent_btn()" class="btn btn-danger">删除</button>'
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
 * 从集群中删除节点按钮
 */
function deleteagent_btn() {
    $.messager.confirm('Confirm','确定要从集群中删除此节点?',function(r){
        if (r){
            var row = getCheckBoxSum();
            $.ajax({
                type: "GET",
                url : "/deleteAgentInColony",
                data : {"colonyname":nam,"agentaddress":row[0]["agentaddress"]},
                success : function(data) {
                    init_tabledata(nam);
                },
                error : function () {
                    alert("节点删除失败")
                }
            });
        }
    });
}

/**
 * 初始化弹窗数据
 */
function add_colony() {

    var list = new Array();
    $.ajax({
        type: "GET",
        url : "/getAgent",
        data : {},
        success : function(data) {
            for(var i = 0;i<data.length;i++) {
                list[i] = data[i]["agentname"];
            }
            new Vue({
                el: "#addcolony",
                data:{
                    list: list
                },
            });
            $('#add_colony_dialog').modal('show');
        },
        error : function() {
            alert('节点获取失败')
        }
    });
}

/**
 * 提交按钮，创建一个集群
 */
function submit_colony(){
    var colonyname = $("#colonyname").val();
    obj = document.getElementsByName("colony");
    check_val = [];
    for(k in obj){
        if(obj[k].checked)
            check_val.push(obj[k].value);
    }
    $.ajax({
        type: "GET",
        url : "/addColony",
        traditional: true,
        data : {"colonyname":colonyname,"colony":check_val},
        success : function(data) {
            if(data) {
                window.location.href="/dofwjq";
                alert("成功:集群创建成功!");
            }else {
                alert("失败:集群名称已存在!");
            }
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