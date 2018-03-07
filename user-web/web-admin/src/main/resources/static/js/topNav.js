function topNav(strData) {
    var data;
    if (typeof(strData) == "string") {
        var data = JSON.parse(strData); //部分用户解析出来的是字符串，转换一下
    } else {
        data = strData;
    }
    var ulHtml = '';
    for (var i = 0; i < data.length; i++) {
        ulHtml += '<li class="layui-nav-item">';
        //获取data[i]下总共有几级菜单、如果超过3级，则topNav有两级，否则只有一级、
        var count = conutLayer(data[i], 1);
        if (count > 3 && data[i].children != undefined && data[i].children.length > 0) {
            ulHtml += '<a href="javascript:;">';
            if (data[i].icon != undefined && data[i].icon != '') {
                if (data[i].icon.indexOf("icon-") != -1) {
                    ulHtml += '<i class="iconfont ' + data[i].icon + '" data-icon="' + data[i].icon + '"></i>';
                } else {
                    ulHtml += '<i class="layui-icon" data-icon="' + data[i].icon + '">' + data[i].icon + '</i>';
                }
            }
            ulHtml += '<cite>' + data[i].text + '</cite>';
            ulHtml += '<span class="layui-nav-more"></span>';
            ulHtml += '</a>';
            ulHtml += '<dl class="layui-nav-child">';
            for (var j = 0; j < data[i].children.length; j++) {
                ulHtml += '<dd><a href="javascript:;" class="loadChild">';
                if (data[i].children[j].icon != undefined && data[i].children[j].icon != '') {
                    if (data[i].children[j].icon.indexOf("icon-") != -1) {
                        ulHtml += '<i class="iconfont ' + data[i].children[j].icon + '" data-icon="' + data[i].children[j].icon + '"></i>';
                    } else {
                        ulHtml += '<i class="layui-icon" data-icon="' + data[i].children[j].icon + '">' + data[i].children[j].icon + '</i>';
                    }
                }
                ulHtml += '<cite>' + data[i].children[j].text + '</cite></a></dd>';
            }
            ulHtml += "</dl>";
        }
        else {
            ulHtml += '<a href="javascript:;" class="loadChild">';
            if (data[i].icon != undefined && data[i].icon != '') {
                if (data[i].icon.indexOf("icon-") != -1) {
                    ulHtml += '<i class="iconfont ' + data[i].icon + '" data-icon="' + data[i].icon + '"></i>';
                } else {
                    ulHtml += '<i class="layui-icon" data-icon="' + data[i].icon + '">' + data[i].icon + '</i>';
                }
            }
            ulHtml += '<cite>' + data[i].text + '</cite></a>';
        }
        ulHtml += '</li>';
    }
    return ulHtml;
}

function conutLayer(data, count) {
    if (data.children != undefined && data.children.length > 0) {
        count++;
        $(data.children).each(function () {
            count = conutLayer($(this)[0], count);
        })
    }
    return count;
}