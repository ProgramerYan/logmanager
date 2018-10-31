$(document).ready(function() {
    $('#service_tree').tree({
        type: "GET",
        url:"http://127.0.0.1:8080/testTree",
        loadFilter: function(rows){
            return convert(rows);
        },
    });
});

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


