/*
* author:Qimeng Duan
* description:用于日期时间的解析
* date:2018-01-24
* */
//解析日期范围
function parseDatetoSplit(data) {
    var arr = [];
    var timeRange,date;
    timeRange = data.field.timeRange;
    date = data.field.date;
    if (timeRange) {
        arr = timeRange.split('至');
        data.field.beginTime = $.trim(arr[0]);
        data.field.endTime = $.trim(arr[1]);
    }else if(date){
        arr = date.split('至');
        data.field.beginTime = $.trim(arr[0])+' 00:00:00';
        data.field.endTime = $.trim(arr[1])+' 23:59:59';
    }
}

//解析日期返回字符串
function parseDateToStr(flag) {
    var beginTime, endTime;
    if (flag == 0) {//获取今日的日志信息
        beginTime = formatDateToStr(new Date());
        endTime = formatDateToStr(new Date());
        return beginTime + " 至 " + endTime;
    } else if (flag == 1) {//获取昨天的日志信息
        var param = new Date().getTime() - 24 * 60 * 60 * 1000;
        var date = new Date(param);
        beginTime = formatDateToStr(date);
        endTime = formatDateToStr(date);
        return beginTime + " 至 " + endTime;
    } else if (flag == 2) {//获取本月日期
        var date = new Date();
        var begin = new Date(date.setDate(1));//获取本月第一天
        var end = new Date(date.getFullYear(), date.getMonth() + 1, 0);//获取本月最后一天的日期
        beginTime = formatDateToStr(begin);
        endTime = formatDateToStr(end);
        return beginTime + " 至 " + endTime;
    } else if (flag == 3) {
        beginTime = new Date().getFullYear() + "-01-01";
        endTime = new Date().getFullYear() + "-12-31";
        return beginTime + " 至 " + endTime;
    } else {
        return null;
    }
}

//将日期格式化为字符串
function formatDateToStr(date) {
    var year, month, day;
    year = date.getFullYear();
    month = date.getMonth() + 1;
    day = date.getDate();
    if ((month + "").length < 2) {
        month = "0" + month;
    }
    if ((day + "").length < 2) {
        day = "0" + day;
    }
    return year + "-" + month + "-" + day;
}