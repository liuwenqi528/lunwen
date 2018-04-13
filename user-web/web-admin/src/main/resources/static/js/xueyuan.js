$(function ($) {
    $.ajaxSetup({
        complete: function (XMLHttpRequest, textStatus) {
            var loginStatus = XMLHttpRequest.getResponseHeader("loginStatus");
            if (loginStatus == "0") {
                var responseJSON = XMLHttpRequest.responseJSON;
                layer.msg(responseJSON.loginMessage);
                window.top.location.reload(true);
                return false;
            }
            var systemStatus = XMLHttpRequest.getResponseHeader("systemStatus");
            if (systemStatus == "0") {
                window.top.location.href = basePath + "/error/500.html";
                return false;
            }
        }
    });

});

//打开layer层
function openLayer(title, path,btn) {
    return parent.layer.open({
        type: 2 //此处以iframe举例
        , title: title
        , area: ["80%", "75%"]
        , anim: 1
        , shade: 0.1
        , content: path
        , shadeClose: false
        , resize: false
        , btn: btn||['立即提交','重置']
        , yes: function (index, layero) {
            layero.find('iframe')[0].contentWindow.saveForm();
        }
        , btn2: function (index, layero) {
            layero.find('iframe')[0].contentWindow.resetForm();
            return false;
        }
        , zIndex: layer.zIndex //重点1
        , success: function (layero, index) {
            parent.layer.setTop(layero); //重点2
        }
    });
}

//不带拖动的treegrid
function createTreegrid(selector, url) {
    var rowID;
    var selectID = '';
    return selector.treegrid({
        url: url,
        idField: 'id',//设置主键
        treeField: 'text',//设置显示树的字段
        fitColumns: true,//自适应宽度，设置的width像素会转化成百分比自动设置
        fit: true,//自适应父容器
        // width:'100%',
        // height:'100%',
        autoRowHeight: false,
        selectOnCheck: true,
        // singleSelect:false,
        nowrap: true,
        border: false,
        onClickRow: function (row) {
            //判断是否选中行和此单元格所在行相等。如果相等。取消选中。
            if (selectID == row.id) {
                $(this).treegrid('unselectAll');
                selectID = "";//清空选中行的标记
            } else {
                //记录选中行的id
                selectID = row.id;
            }
        }
    });
}

//添加、修改、删除完成后重新加载treegrid
function reloadTree(selector, data, index) {
    layer.msg(data.retInfo);
    if (data.retCode == '0000') {
        if (index) {
            layer.close(index);
        }
        $(selector).treegrid('reload');
        $(selector).treegrid('unselectAll');
    }
}

//layui table
function initTable(table, obj) {
    var initTable;
    // layui.use(['table'], function () {
    //     var table = layui.table;
    initTable = table.render({
        elem: obj.elem
        , url: obj.data ? "" : obj.url
        , data: obj.data
        , cols: obj.cols
        , request: {
            limitName: 'rows' //每页数据量的参数名，默认：limit
        },
        response: {
            statusName: 'retCode' //数据状态的字段名称，默认：code
            , statusCode: '0000' //成功的状态码，默认：0
            , msgName: 'retInfo' //状态信息的字段名称，默认：msg
            , countName: 'total' //数据总数的字段名称，默认：count
            , dataName: 'rows' //数据列表的字段名称，默认：data
        }
        , text: {
            none: '暂无相关数据' //默认：无数据。注：该属性为 layui 2.2.5 开始新增
        }
        , height: obj.height || 'full-161'
        , page: obj.page == false ? false : true
        , limits: obj.limits || [5, 10, 15, 20]
        , done: obj.done
    })
    // })
    return initTable;
}