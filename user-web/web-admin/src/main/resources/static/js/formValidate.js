/*
* author Qimeng Duan
* descrption:用于验证form表单的正则
* date:2018-01-24
* */
//验证密码的正则表达式
function verifyPassWord(value){
    if(!new RegExp('^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$').test(value))
        return '密码只能为由英文+数字组成，且长度为6-20位';
}
//验证不能包含特殊字符，且长度不能大于16位的正则表达式,type为验证类型
function verifyNickName(value,type){
    if(!new RegExp('^[a-zA-Z\\d\\_\u2E80-\u9FFF]{0,16}$').test(value))
        return type+'不能包含特殊字符，且长度不能大于16位!';
}
//验证身份证的正则表达式(当前仅仅验证了身份证的一般格式，如果需要严格的验证，可替换正则)
function verifyCard(value){
    if(!new RegExp('(^\\d{15}$)|(^\\d{18}$)|(^\\d{17}(\\d|X|x)$)').test(value))
        return '身份证格式错误!';
}
//验证真实姓名的正则表达式(不能同时存在汉字和英文、不能存在特殊字符、可为英文、可为汉字。可为英文加点)
function verifyRealName(value){
    if(value!=null && value!=""){
        if(!new RegExp('^([\u4e00-\u9fa5]{1,20}|[a-zA-Z\\.\\s]{2,25})$').test(value))
            return '请输入正确的姓名';
    }
}
//验证输入必须为数字
function isNumber(value){
    if(!new RegExp('^[0-9]+$').test(value)){
        return '不能为空，且只能输入数字，且要大于等于0！';
    }
}