
$(document).ready(function() {
    for(var i = 0;i<4;i++) {
        li_name = "<li>"+data[i]["groupname"]+"</li>";
        $("#news").append(li_name);
    }
    // $.ajax({
    //         type: "GET",
    //         url : "/getServiceGroup",
    //         data : {},
    //         success : function(data) {
    //             var li_name;
    //             for(var i = 0;i<data.length;i++) {
    //                 li_name = "<li>"+data[i]["groupname"]+"</li>";
    //                 $("#news").append(li_name);
    //             }
    //            // console.log(data[1]["groupname"])
    //         }
    //     });
});