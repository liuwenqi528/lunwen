// 张英才 添加 icon 方法
layui.define(["jquery"],function (exports) {
    exports('myIcon', function (iconUrl) {
        function CreatIcon(val) {
            this.iconSpan=document.createElement("span");
            this.iconSpan.setAttribute("data-icon",val);
            this.iconSpan.setAttribute("title",val);
            this.iconContent=document.createElement("i");
            this.iconContent.setAttribute("data-icon",val);
            this.iconContent.className="iconfont "+val;
            this.iconSpan.appendChild(this.iconContent);
        }
        CreatIcon.prototype.showIconName=function (showEle) {
            var self=this.iconSpan;
            self.onclick=function () {
                var selfPar=this.offsetParent.parentNode;
                selfPar.parentNode.removeChild(selfPar);
                document.getElementsByClassName(showEle)[0].value=self.getAttribute("data-icon");
            }
        };
        $("#choiceIcon").on("click",function () {
            $.ajax({
                url:iconUrl,
                success:function (data) {
                    if(data){
                        creatIconPage(getIcon(data))
                    }

                }
            });
            function getIcon(data) {
                var firstIcon=data.split(":before")[0];
                var fIcon=firstIcon.split("\n")[firstIcon.split("\n").length-1].substr(1);
                var iconContentArr=data.split(":before").slice(1);
                var iconArr=[];
                for(var i=0;i<iconContentArr.length;i++){
                    if(iconContentArr[i].split("\n")[iconContentArr[i].split("\n").length-1]){
                        iconArr.push(iconContentArr[i].split("\n")[iconContentArr[i].split("\n").length-1].substr(1))
                    }

                }
                iconArr.unshift(fIcon);
                return iconArr;
            }
            var iconParent=$("<div id='iconParent'></div>");
            var iconContent=$("<div id='iconContent'><h4 id='move'><span>图标列表</span> <span id='iconClose' class='layui-layer-setwin'><a class='layui-layer-ico layui-layer-close layui-layer-close1' href='javascript:;'></a> </span> </h4></div>");
            var iconContainer=$("<div id='iconContainer'></div>");
            function creatIconPage(iconData) {
                $.each(iconData,function (index,val) {
                    if(val.length<=30){
                        var icon=new CreatIcon(val);
                        icon.showIconName("iconVal");
                        iconContainer.append(icon.iconSpan);
                    }
                });
                iconContent.append(iconContainer);
                iconParent.append(iconContent);
                $("body").append(iconParent);
                //关闭 图标列表
                $("#iconClose").on("click",function () {
                    iconParent.remove();
                });

            }
        });


        

    });
});



