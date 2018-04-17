/**

 @Name：layui.tree 树组件
 @Author：贤心
 @License：MIT

 */


layui.define('jquery', function (exports) {
    "use strict";

    var $ = layui.$
        , hint = layui.hint();

    var enterSkin = 'layui-tree-enter', Tree = function (options) {
        this.options = options;
    };

    //图标
    var icon = {
        arrow: ['&#xe623;', '&#xe625;'] //箭头
        , checkbox: ['&#xe626;', '&#xe627;'] //复选框
        , radio: ['&#xe62b;', '&#xe62a;'] //单选框
        , branch: ['&#xe622;', '&#xe624;'] //父节点
        , leaf: '&#xe621;' //叶节点
    };

    //初始化
    Tree.prototype.init = function (elem) {
        var that = this;
        elem.addClass('layui-box layui-tree'); //添加tree样式
        if (that.options.skin) {
            elem.addClass('layui-tree-skin-' + that.options.skin);
        }
        that.tree(elem);
        that.on(elem);
    };

    //树节点解析
    Tree.prototype.tree = function (elem, children) {
        var that = this, options = that.options
        var nodes = children || options.nodes;

        layui.each(nodes, function (index, item) {
            var hasChild = item.children && item.children.length > 0;
            var ul = $('<ul class="' + (item.spread ? "layui-show" : "") + '"></ul>');
            var li = $(['<li ' + (item.spread ? 'data-spread="' + item.spread + '"' : '') + '>'
                //展开箭头
                , function () {
                    return hasChild ? '<i class="layui-icon layui-tree-spread">' + (
                        item.spread ? icon.arrow[1] : icon.arrow[0]
                    ) + '</i>' : '';
                }()

                //复选框/单选框
                , function () {
                    return options.check ? (
                        '<i class="layui-icon layui-tree-check" id="tree-' + item.id + '" data-parent-id="' + item.parentId + '">' + (
                            options.check === 'checkbox' ? icon.checkbox[0] : (
                                options.check === 'radio' ? icon.radio[0] : ''
                            )
                        ) + '</i>'
                    ) : '';
                }()

                //节点
                , function () {
                    //****自定义替换name 开始
                    var name = item.text;
                    if (that.options.replaceName) name = item[that.options.replaceName];
                    //****自定义替换name 结束

                    return '<a href="' + (item.href || 'javascript:;') + '" ' + (
                            options.target && item.href ? 'target=\"' + options.target + '\"' : ''
                        ) + '>'
                        + ('<i class="layui-icon layui-tree-' + (hasChild ? "branch" : "leaf") + '">' + (
                            hasChild ? (
                                item.spread ? icon.branch[1] : icon.branch[0]
                            ) : icon.leaf
                        ) + '</i>') //节点图标
                        + ('<cite>' + (name || '未命名') + '</cite></a>');
                }()

                , '</li>'].join(''));

            //如果有子节点，则递归继续生成树
            if (hasChild) {
                li.append(ul);
                that.tree(ul, item.children);
            }

            elem.append(li);

            //触发点击节点回调
            typeof options.click === 'function' && that.click(li, item);
            typeof options.clickCheckbox === 'function' && that.clickCheckbox(li, item);

            //伸展节点
            that.spread(li, item);

            that.defaultCheck(li, item);
            //选择节点
            that.check(li, item);

            //拖拽节点
            options.drag && that.drag(li, item);
        });
    };

    //点击节点回调
    Tree.prototype.click = function (elem, item) {
        var that = this, options = that.options;
        elem.children('a').on('click', function (e) {
            layui.stope(e);
            options.click(item, elem);
        });
    };//点击复选框回调
    Tree.prototype.clickCheckbox = function (elem, item) {
        var that = this, options = that.options;
        elem.children('.layui-tree-check').on('click', function (e) {
            layui.stope(e);
            options.clickCheckbox(item, elem);
        });
    };

    //伸展节点
    Tree.prototype.spread = function (elem, item) {
        var that = this, options = that.options;
        var arrow = elem.children('.layui-tree-spread')
        var ul = elem.children('ul'), a = elem.children('a');

        //执行伸展
        var open = function () {
            if (elem.data('spread')) {
                elem.data('spread', null)
                ul.removeClass('layui-show');
                arrow.html(icon.arrow[0]);
                a.find('.layui-icon').html(icon.branch[0]);
            } else {
                elem.data('spread', true);
                ul.addClass('layui-show');
                arrow.html(icon.arrow[1]);
                a.find('.layui-icon').html(icon.branch[1]);
            }
        };

        //如果没有子节点，则不执行
        if (!ul[0]) return;

        arrow.on('click', open);
        a.on('dblclick', open);
    }

    //*****自定义 选择节点
    Tree.prototype.defaultCheck = function (elem, item) {
        var that = this;
        var checkbox = elem.children('.layui-tree-check');
        var li = elem.find('ul li');

        if (item.checked) {
            checkbox.html(icon.checkbox[1]).addClass("checked");
            elem.data('checked', true)
        }
    }
    Tree.prototype.check = function (elem, item) {
        var that = this, options = that.options;
        var checkbox = elem.children('.layui-tree-check');
        var li = elem.find('ul li'), licheckbox = elem.find('ul li .layui-tree-check');

        //执行选择
        var check = function () {
            if (elem.data('checked')) {			//默认选中
                li.data('checked', null);
                elem.data('checked', null);
                item.checked = null;
                eachParent(item, null);
                eachChildren(item, null);
                checkbox.html(icon.checkbox[0]).removeClass("checked");
                licheckbox.html(icon.checkbox[0]).removeClass("checked");
            } else {							//默认不选中
                li.data('checked', true);
                elem.data('checked', true);
                item.checked = true;
                eachParent(item, true);
                eachChildren(item, true);
                checkbox.html(icon.checkbox[1]).addClass("checked");
                licheckbox.html(icon.checkbox[1]).addClass("checked");
            }
        };
        var eachParent = function (item, value) {
            var siblings = $('#tree-' + item.id).closest("li").siblings();
            var flag = true;
            $(siblings).each(function (index, node) {
                if ($(node).children(".layui-tree-check").hasClass('checked')) {
                    flag = false;
                }
            })
            if (flag) {
                if (value) {
                    var parent = $('#tree-' + item.parentId);
                    parent.html(icon.checkbox[(value ? 1 : 0)]);
                    parent.addClass("checked");

                    parent.parent("li").data('checked', value);

                    var parentNode = that.getParent(item);
                    if (parentNode != undefined) {
                        parentNode.checked = value;
                        eachParent(parentNode, value);
                    }
                }
            }
            flag = false;
        }

        var eachChildren = function (item, value) {
            layui.each(item.children, function (index, it) {
                it.checked = value;
                $('#tree-' + item.id).parent("li").data('checked', value);
                eachChildren(it, value);
            });
        }
//如果没有设置check，则不执行
        if (!options.check) return;
        checkbox.on('click', check);
    }
    Tree.prototype.getParent = function (node) {
        var that = this;
        var nodes = that.options.nodes;
        var parentNode;
        var findParentById = function (nodes, node) {
            $.each(nodes, function (index, item) {
                if (item.id == node.parentId) {
                    parentNode = item;
                    return false;
                } else {
                    if (item.children && item.children.length > 0) {
                        findParentById(item.children, node);
                    }
                }
            });
            return parentNode;
        }

        return findParentById(nodes, node);
    }
//****自定义 返回选中回调
    Tree.prototype.getChecked = function (node) {
        var that = this;
        var nodes = node || that.options.nodes;
        var checkedArr = [];
        layui.each(nodes, function (index, item) {
            if (item.checked != null && item.checked) {
                checkedArr.push(item);
            }
            if (item.children != null && item.children.length > 0) {
                checkedArr = checkedArr.concat(that.getChecked(item.children));
            }
        });
        return checkedArr;
    };

//通用事件
    Tree.prototype.on = function (elem) {
        var that = this, options = that.options;
        var dragStr = 'layui-tree-drag';

        //屏蔽选中文字
        elem.find('i').on('selectstart', function (e) {
            return false
        });

        //拖拽
        if (options.drag) {
            $(document).on('mousemove', function (e) {
                var move = that.move;
                if (move.from) {
                    var to = move.to, treeMove = $('<div class="layui-box ' + dragStr + '"></div>');
                    e.preventDefault();
                    $('.' + dragStr)[0] || $('body').append(treeMove);
                    var dragElem = $('.' + dragStr)[0] ? $('.' + dragStr) : treeMove;
                    (dragElem).addClass('layui-show').html(move.from.elem.children('a').html());
                    dragElem.css({
                        left: e.pageX + 10
                        , top: e.pageY + 10
                    })
                }
            }).on('mouseup', function () {
                var move = that.move;
                if (move.from) {
                    move.from.elem.children('a').removeClass(enterSkin);
                    move.to && move.to.elem.children('a').removeClass(enterSkin);
                    that.move = {};
                    $('.' + dragStr).remove();
                }
            });
        }
    };

//拖拽节点
    Tree.prototype.move = {};
    Tree.prototype.drag = function (elem, item) {
        var that = this, options = that.options;
        var a = elem.children('a'), mouseenter = function () {
            var othis = $(this), move = that.move;
            if (move.from) {
                move.to = {
                    item: item
                    , elem: elem
                };
                othis.addClass(enterSkin);
            }
        };
        a.on('mousedown', function () {
            var move = that.move
            move.from = {
                item: item
                , elem: elem
            };
        });
        a.on('mouseenter', mouseenter).on('mousemove', mouseenter)
            .on('mouseleave', function () {
                var othis = $(this), move = that.move;
                if (move.from) {
                    delete move.to;
                    othis.removeClass(enterSkin);
                }
            });
    };

//暴露接口
    exports('treeBox', function (options) {
        var tree = new Tree(options = options || {});
        var elem = $(options.elem);
        if (!elem[0]) {
            return hint.error('layui.tree 没有找到' + options.elem + '元素');
        }
        tree.init(elem);
        return tree;
    });
})
;
