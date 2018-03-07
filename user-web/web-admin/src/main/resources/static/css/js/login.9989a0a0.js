!function (a) {
    function b() {
    }

    function c(a) {
        g = [a]
    }

    function d(a, b, c) {
        return a && a.apply && a.apply(b.context || b, c)
    }

    function e(a) {
        return /\?/.test(a) ? "&" : "?"
    }

    function f(f) {
        function n(a) {
            X++ || (Y(), S && (A[U] = {s: [a]}), O && (a = O.apply(f, [a])), d(L, f, [a, v, f]), d(N, f, [f, v]))
        }

        function F(a) {
            X++ || (Y(), S && a != w && (A[U] = a), d(M, f, [f, a]), d(N, f, [f, a]))
        }

        f = a.extend({}, C, f);
        var G, H, I, J, K, L = f.success, M = f.error, N = f.complete, O = f.dataFilter, P = f.callbackParameter,
            Q = f.callback, R = f.cache, S = f.pageCache, T = f.charset, U = f.url, V = f.data, W = f.timeout, X = 0,
            Y = b;
        return y && y(function (a) {
            a.done(L).fail(M), L = a.resolve, M = a.reject
        }).promise(f), f.abort = function () {
            !X++ && Y()
        }, d(f.beforeSend, f, [f]) === !1 || X ? f : (U = U || j, V = V ? "string" == typeof V ? V : a.param(V, f.traditional) : j, U += V ? e(U) + V : j, P && (U += e(U) + encodeURIComponent(P) + "=?"), !R && !S && (U += e(U) + "_" + (new Date).getTime() + "="), U = U.replace(/=\?(&|$)/, "=" + Q + "$1"), S && (G = A[U]) ? G.s ? n(G.s[0]) : F(G) : (x[Q] = c, I = a(u)[0], I.id = m + B++, T && (I[i] = T), D && D.version() < 11.6 ? (J = a(u)[0]).text = "document.getElementById('" + I.id + "')." + p + "()" : I[h] = h, E && (I.htmlFor = I.id, I.event = o), I[q] = I[p] = I[r] = function (a) {
            if (!I[s] || !/i/.test(I[s])) {
                try {
                    I[o] && I[o]()
                } catch (b) {
                }
                a = g, g = 0, a ? n(a[0]) : F(k)
            }
        }, I.src = U, Y = function (a) {
            K && clearTimeout(K), I[r] = I[q] = I[p] = null, z[t](I), J && z[t](J)
        }, z[l](I, H = z.firstChild), J && z[l](J, H), K = W > 0 && setTimeout(function () {
            F(w)
        }, W)), f)
    }

    var g, h = "async", i = "charset", j = "", k = "error", l = "insertBefore", m = "_jqjsp", n = "on", o = n + "click",
        p = n + k, q = n + "load", r = n + "readystatechange", s = "readyState", t = "removeChild", u = "<script>",
        v = "success", w = "timeout", x = window, y = a.Deferred, z = a("head")[0] || document.documentElement, A = {},
        B = 0, C = {callback: m, url: location.href}, D = x.opera,
        E = !!a("<div>").html("<!--[if IE]><i><![endif]-->").find("i").length;
    f.setup = function (b) {
        a.extend(C, b)
    }, a.jsonp = f
}(jQuery), function (a) {
    var b = {
        container: a(".js-speedbox"),
        panelTemplate: '            <div class="m-speed">                <div class="speed-hd">网络测速</div>                <div class="speed-bd">                </div>            </div>',
        panel: void 0,
        ispTemplate: '            <a href="javascript:;" class="item">                <div class="name"></div>                <div class="status">连接中...</div>            </a>',
        isps: {},
        knownAddress: {
            t: ["电信", "https://tp.qiye.163.com/cte/ttest"],
            c: ["联通", "https://cp.qiye.163.com/cte/ctest"],
            h: ["华东互通", "https://hzp.qiye.163.com/cte/hzp"]
        },
        entry: {t: "tentry.qiye.163.com", c: "centry.qiye.163.com", h: "hentry.qiye.163.com"},
        availableAddress: [],
        recordStartTime: {},
        duration: {},
        dnsReady: !1,
        selectManually: void 0,
        scripts: [],
        reset: function () {
            if (this.panel) this.panel.find(".speed-bd").empty(); else {
                this.panel = a(this.panelTemplate), this.panel.hide().click(function (a) {
                    a.stopPropagation()
                }), this.container.empty(), this.panel.appendTo(this.container);
                var b = this.panel;
                a(document.body).click(function () {
                    b.hide()
                })
            }
            this.availableAddress = [], this.recordStartTime = {}, this.duration = {}
        },
        clickIsp: function (b) {
            b.preventDefault();
            var c = a(b.currentTarget);
            this.panel.find(".item").removeClass("item-selected"), c.addClass("item-selected"), this.setBestIsp(c.attr("name")), this.selectManually = c.attr("name"), this.panel.hide()
        },
        setBestIsp: function (b) {
            a(".js-operator").val(b), a(".js-testspeed").text("服务器：" + this.knownAddress[b][0])
        },
        showPanel: function (c) {
            this.reset(), c = c.split(",");
            for (var d = 0; d < c.length; d++) {
                var e = c[d], f = this.knownAddress[e], g = a(this.ispTemplate);
                this.availableAddress.push(e), g.attr("name", e).find(".name").text(f[0]), e === this.selectManually && g.addClass("item-selected"), this.isps[e] = g, this.panel.find(".speed-bd").append(g)
            }
            this.panel.find(".item").click(function (a) {
                b.clickIsp(a)
            });
            this.container;
            this.panel.show(), this.detectNetwork()
        },
        detectNetwork: function () {
            this.scripts && a.each(this.scripts, function (a, b) {
                b.remove()
            });
            for (var b = 0; b < this.availableAddress.length; b++) {
                var c = this.availableAddress[b], d = new Date;
                this.recordStartTime[c] = new Date;
                var e = a("<script></script>").attr("type", "text/javascript").attr("src", this.knownAddress[c][1] + "?" + d.getTime());
                this.scripts.push(e), e.appendTo(document.body)
            }
        },
        detectNetworkCB: function (a) {
            var b;
            switch (a) {
                case 0:
                    b = "t";
                    break;
                case 1:
                    b = "c";
                    break;
                case 2:
                    b = "h"
            }
            var c = (new Date).getTime() - this.recordStartTime[b].getTime();
            this.duration[b] = c, this.isps[b].find(".status").text(c + "ms");
            var d = [];
            for (var b in this.duration) this.duration.hasOwnProperty(b) && d.push(this.duration[b]);
            var e = Math.min.apply(Math, d);
            for (var b in this.duration) if (this.duration.hasOwnProperty(b) && this.duration[b] === e) {
                this.isps[b].find(".status").addClass("best"), this.selectManually || (this.panel.find(".item").removeClass("item-selected"), this.isps[b].addClass("item-selected"), this.setBestIsp(b));
                break
            }
        }
    };
    window.addresses = "t,c,h", window.fSpeedTest = function (a) {
        b.detectNetworkCB(a)
    }, window.fSpd = function (a) {
        b.detectNetworkCB(2)
    }, a(".js-testspeed").click(function (a) {
        a.preventDefault(), a.stopPropagation(), b.showPanel(window.addresses)
    })
}(jQuery), Function.prototype.bind || (Function.prototype.bind = function (a) {
    if ("function" != typeof this) throw new TypeError("Function.prototype.bind - what is trying to be bound is not callable");
    var b = Array.prototype.slice.call(arguments, 1), c = this, d = function () {
    }, e = function () {
        return c.apply(this instanceof d ? this : a || this, b.concat(Array.prototype.slice.call(arguments)))
    };
    return d.prototype = this.prototype, e.prototype = new d, e
}), function () {
    var a = {
        "DOMAIN.NOTSTARTWITHMAIL.DOT": "解析域名错误，域名必须以mail.开头",
        "DOMAIN.NOTEXIST": "找不到该域名信息，请联系客服或经销商",
        "PAGE.NOTFOUND": "您访问的页面找不到，可在这里登录邮箱",
        "PAGE.SYSTEM.ERR": "您访问的页面发生错误，可在这里登录邮箱",
        "ERR.SYSTEM": "系统错误，请联系客服",
        "ERR.PARAM": "参数错误",
        "ERR.IM.LOGIN.IPDENY": "登录失败，您的IP在黑名单中，请联系管理员",
        "ERR.LOGIN.IPDENY": "登录失败，您的IP在黑名单中，请联系管理员",
        "ERR.LOGIN.PASSERR": "邮箱帐号和密码不匹配",
        "ERR.LOGIN.ILLEGALACCOUNT": "这是一个邮件列表，无法登录邮箱",
        "ERR.LOGIN.USERNOTEXIST": "该帐号不存在，请您确认正确的域名和帐号",
        "ERR.LOGIN.SYSTEMBUSY": "系统繁忙，请您稍候再试",
        "ERR.LOGIN.DOMAINEXPED": "该企业邮箱已过期，请联系管理员或客服",
        "ERR.LOGIN.DOMAINSTATUS": "该企业邮箱已被禁用，请联系客服",
        "ERR.LOGIN.DOMAINNOTEXIST": "该域名未开通企业邮箱，请联系经销商或客服",
        "ERR.LOGIN.USERSTATUS": "该帐号已被禁用或删除，请联系管理员",
        "ERR.LOGIN.USEREXP": "该帐号已过期，请联系管理员",
        "ERR.SESSIONNULL": "当前会话已失效，请您重新登录",
        "ERR.REQ.VERIFYCODE": "密码输入错误次数过多，请稍候再试",
        "ERR.ILLEGAL": "非法操作",
        EXP_AUTH_USER_STATUS_SUSPENDED: "该帐号已被禁用，请联系管理员或客服",
        EXP_AUTH_USER_STATUS_LOCKED: "该帐号已被禁用，请联系管理员或客服",
        EXP_AUTH_COOKIE_NOT_FOUND: "浏览器连接异常，请更换浏览器并重新登录",
        EXP_AUTH_COOKIE_TIMEOUT: "登录信息超时，请重新登录",
        EXP_AUTH_USER_NOT_FOUND: "该帐号不存在，请联系管理员或客服",
        EXP_AUTH_USER_FORBIDDEN: "该帐号已被禁用，请联系管理员或客服",
        EXP_AUTH_USER_STATUS_ERROR: "该帐号已被禁用，请联系管理员或客服",
        "ERR.RIGHT.DENY": "对不起，您没有权限进行此操作",
        "ERR.LOGIN.SUPERADMINERR": "对不起，请从主域名登录",
        "ERR.LOGIN.USERNOTEXIS": "该帐号不存在，请您确认正确的域名和帐号",
        "ERR.LOGIN.USRSEXP": "该帐号已过期，请联系管理员或客服",
        "ERR.REQ.VERI": "密码输入错误次数过多，请稍候再试",
        "LOGOOUT.FAIL": "",
        "SYSTEM.BUSY": "系统繁忙，请您稍候再试",
        "DOMAIN.DEFAULT": "",
        "LOGIN.PERMDENY": "该帐号无权限登录Web邮箱，请联系管理员",
        "VERIFYCODE.ERROR": "验证码错误",
        "VERIFYCODE.REQ": "请输入验证码",
        ACCOUNTLOCKED: "密码输入错误次数过多，请稍候再试"
    }, b = {
        init: function () {
            var a = this, b = a.getQueryString("hl");
            "zh_TW" == b ? window.location.href = "//mail.qiye.163.com/?hl=zh_TW" : "en_US" == b && (window.location.href = "//mail.qiye.163.com/?hl=en_US"), a.curentDomainNode = a.getQueryString("from") || "bj", a.initData(), a.initDom(), a.initAccount(), a.initIpt(), a.initTab(), a.initResize(), a.initFKX(), a.initCheck(), a.initSeletor(), a.initEvent(), a.showCode = a.getQueryString("code") ? !0 : !1, a.showCode && ($(".m-verifycode").show(), a.changeVerifyCode())
        }, initData: function () {
            var a = this;
            a.entryURL = a.setEntry(), a.loginType = "account", a.gOption = {
                sCookieDomain: "email.163.com",
                product: "mailqiye",
                sDomain: "163.com",
                gad: "email.163.com"
            }
        }, initDom: function () {
            var a = this;
            a.$errorNode = $("#msgpid"), a.$autoLabel = $(".js-autolabel"), a.$testSpeed = $(".js-testspeed"), a.$accLoginForm = $(".js-loginform-acc"), a.$adminLoginForm = $(".js-loginform-admin"), a.$curLoginForm = a.$accLoginForm, a.$accountNode = $("#accname"), a.$adminAccNode = $("#adminname"), a.$verifyImg = $(".refreshVerifycode")
        }, initAccount: function () {
            var a, b = this, c = fGetCookie("qiye_account"), d = fGetCookie("qiyeadmin_account");
            c && (b.$accountNode.val(c), a = b.$accLoginForm.find(".js-logincheck"), a.find(".js-autologin").attr("checked", !1), b.setCheckbox(a)), d && (b.$adminAccNode.val(d), a = b.$adminLoginForm.find(".js-logincheck"), a.find(".js-autologin").attr("checked", !1), b.setCheckbox(a)), b.focusInput()
        }, initIpt: function () {
            var b = this;
            b.checkInput($(".js-value")), $(".js-ipt").on("blur", ".js-value", function () {
                b.checkInput($(this))
            }).on("click", ".js-placeholder", function () {
                $(this).prev(".js-value").focus()
            });
            var c = b.getQueryString("msg");
            if (c ? (a[c] && b.$errorNode.text(a[c]), setTimeout(function () {
                    $(".js-value").on("input propertychange", function () {
                        b.$errorNode.text(""), b.checkInput($(this))
                    })
                }, 1e3)) : $(".js-value").on("input propertychange", function () {
                    b.$errorNode.text(""), b.checkInput($(this))
                }), document.body.attachEvent) for (var d = ["accname", "accpwd", "adminname", "adminpwd"], e = 0; e < d.length; e++) {
                var f = e;
                $id(d[f]).attachEvent("onpropertychange", function (a) {
                    return function () {
                        b.$errorNode.text(""), b.checkInput($id(d[a]))
                    }
                }(e))
            }
        }, initTab: function () {
            var a = this, b = $(".js-loginpanel"), c = a.getQueryString("p");
            "admin" === c && (b.addClass("m-login-tab2"), a.$accLoginForm.hide(), a.$adminLoginForm.show(), a.$curLoginForm = a.$adminLoginForm, a.loginType = "admin", a.focusInput()), $(".js-loginhd").on("click", function () {
                var c = $(this).attr("data-lgtype");
                "admin" === c && "account" === a.loginType ? (b.addClass("m-login-tab2"), a.$accLoginForm.hide(), a.$adminLoginForm.show(), a.$curLoginForm = a.$adminLoginForm, a.loginType = "admin", a.changeVerifyCode()) : "account" === c && "admin" === a.loginType && (b.removeClass("m-login-tab2"), a.$adminLoginForm.hide(), a.$accLoginForm.show(), a.$curLoginForm = a.$accLoginForm, a.loginType = "account", a.changeVerifyCode()), a.focusInput()
            })
        }, initResize: function () {
            var a = null;
            $("body").addClass("resize"), $(window).resize(function () {
                a && clearTimeout(a), a = setTimeout(resizeBody, 100)
            })
        }, initFKX: function () {
            function a(a) {
                for (var b = "", c = a; c--;) b += Math.floor(10 * Math.random());
                return b
            }

            var b = $("#KX_IMG"), c = "https://ss.cnnic.cn/verifyseal.dll?sn=e12051044010020841301459&ct=df&pa=",
                d = a(6);
            b.attr("href", c + d)
        }, initCheck: function () {
            var a = this;
            $(".js-logincheck").on("click", ".js-checkbox, .js-autolabel", function () {
                var b = $(this).closest(".js-logincheck");
                a.setCheckbox(b)
            })
        }, initSeletor: function () {
            var a = $(".js-sslsel"), b = $(".js-lgselect"), c = b.find(".js-selitem");
            a.on("click", function () {
                b.show()
            }), b.hover(function () {
            }, function () {
                $(this).hide()
            }), b.on("click", ".js-selitem", function () {
                var d = $(this).attr("data-allssl"), e = $(this).closest(".js-loginform"),
                    f = e.find(".js-isAllSecure");
                0 == d ? f.val(0) : 1 == d && f.val(1), c.removeClass("selected"), $(this).addClass("selected"), b.hide(), a.find(".js-ssltxt").text($(this).text())
            })
        }, changeVerifyCode: function () {
            var a = this;
            if (a.showCode !== !1) {
                var b = "entry.qiye.163.com", c = a.curentDomainNode;
                ("qiyehz" === c || "hz" === c) && (b = "entryhz.qiye.163.com");
                var d = "//" + b + "/domain/getverifycode.jsp?rnd=" + (new Date).getTime();
                return $(".refreshVerifycode").attr("src", d), d
            }
        }, initEvent: function () {
            var a = this;
            a.$autoLabel.hover(function () {
                $(this).next(".js-securetip").show()
            }, function () {
                $(this).next(".js-securetip").hide()
            }), a.$verifyImg.click(function () {
                $(this).attr("src", a.changeVerifyCode())
            }), $(".js-loginform").submit(function () {
                return a.submitForm()
            })
        }, setEntry: function () {
            var a = this, b = {
                    accEntry: "https://entry.qiye.163.com/domain/domainEntLogin",
                    adminEntry: "https://entry.qiye.163.com/domain/domainAdminLogin"
                }, c = a.curentDomainNode, d = a.getQueryString("hl"), e = a.getQueryString("domain"),
                f = $(".js-language"), g = $(".js-domain");
            return "" === c || "qiyebj" === c || "bj" === c ? (b.accEntry = "https://entry.qiye.163.com/domain/domainEntLogin", b.adminEntry = "https://entry.qiye.163.com/domain/domainAdminLogin") : "qiyehz" === c || "hz" === c ? (b.accEntry = "https://entryhz.qiye.163.com/domain/domainEntLogin", b.adminEntry = "https://entryhz.qiye.163.com/domain/domainAdminLogin") : "ym" === c && (b.accEntry = "https://entry.ym.163.com/login/login", b.adminEntry = "https://entry.ym.163.com/login/login"), "" === d || "zh_CN" === d ? f.val(0) : "en_US" === d ? f.val(1) : "zh_TW" === d && f.val(2), g.val(e), b
        }, setCheckbox: function (a) {
            var b = a.find(".js-autologin"), c = a.find(".js-checkbox");
            b.attr("checked") ? (b.attr("checked", !1), c.removeClass("icon-checkbox-checked")) : (b.attr("checked", !0), c.addClass("icon-checkbox-checked"))
        }, isScript: function (a) {
            var b = /(\>|\<|\'|\\|\\\\)/gi;
            return b.test(a)
        }, getQueryString: function (a) {
            var b = this, c = new RegExp("(^|&)" + a + "=([^&]*)(&|$)", "i"),
                d = window.location.hash || window.location.search, e = d.substr(1).match(c);
            return null == e || b.isScript(e[2]) ? "" : unescape(e[2])
        }, focusInput: function () {
            var a = this, b = a.$curLoginForm, c = b.find(".js-username"), d = c.next(".js-placeholder"),
                e = b.find(".js-pwd");
            c.val() ? (d.hide(), e.focus()) : c.focus()
        }, checkInput: function (a) {
            $(a).val() ? $(a).next(".js-placeholder").hide() : $(a).next(".js-placeholder").show()
        }, submitForm: function () {
            var b = this, c = b.$curLoginForm, d = c.find(".js-username"), e = c.find(".js-pwd"),
                f = c.find(".js-verifycode"), g = fTrim(d.val()), h = e.val(), i = f.val(), j = c.find(".js-domain"),
                k = c.find(".js-accname"), l = c.find(".js-autologin").attr("checked"), m = "", n = "";
            if (d.val(g), "" === g) return b.$errorNode.text("请输入你的帐号").show(), d.focus(), !1;
            var o = g.split("@");
            if (!o[1]) return d.focus(), setTimeout(function () {
                b.$errorNode.text("请输入域名").show()
            }, 100), !1;
            if (j.val(o[1]), o[0] && k.val(o[0]), "" === h) return b.$errorNode.text("请输入你的密码").show(), e.focus(), !1;
            if (b.showCode && "" === i) return b.$errorNode.text(a["VERIFYCODE.REQ"]).show(), f.focus(), !1;
            "account" === b.loginType ? (m = b.entryURL.accEntry, n = "qiye_account") : "admin" === b.loginType && (m = b.entryURL.adminEntry, n = "qiyeadmin_account"), c.attr("action", m);
            try {
                l ? fSetCookie(n, g, !0, location.host) : fSetCookie(n, "", !0, location.host)
            } catch (p) {
                console.log(p)
            }
            return b.prelogin(c), !1
        }, isEmpty: function (a) {
            return "undefined" == typeof a || null === a || "" === a ? !0 : !1
        }, prelogin: function (b, c) {
            var d = this, e = c || d.curentDomainNode;
            if ("ym" === e) d.doSubmit(b, e); else {
                var f;
                f = "hz" == e || "qiyehz" == e ? "https://entryhz.qiye.163.com/domain/prelogin.jsp" : "https://entry.qiye.163.com/domain/prelogin.jsp";
                var g = b.find(".js-accname").val() + "@" + b.find(".js-domain").val();
                f = f + "?uid=" + g + "&code=" + b.find(".js-verifycode").val(), $.jsonp({
                    url: f,
                    callbackParameter: "callback",
                    success: function (c) {
                        if (c && 200 == c.code) if (c.data.locked === !0) e && (d.curentDomainNode = e), d.$errorNode.text(a.ACCOUNTLOCKED).show(); else {
                            if (c.data.verify_code === !0) return e && (d.curentDomainNode = e), d.showCode ? ($(".m-verifycode").show(), d.changeVerifyCode(), d.$errorNode.text(a["VERIFYCODE.ERROR"]).show()) : (d.showCode = !0, $(".m-verifycode").show(), d.changeVerifyCode(), d.$errorNode.text(a["VERIFYCODE.REQ"]).show()), void b.find(".js-verifycode").val("");
                            try {
                                var f = c.data, g = new RSAKey;
                                g.setPublic(f.modulus, f.exponent);
                                var h = g.encrypt(b.find(".js-pwd").val() + "#" + f.rand);
                                d.isEmpty(h) || d.isEmpty(f.pubid) || (b.find(".js-pwd").val(h), b.find(".js-pubid").val(f.pubid), b.find(".js-passtype").val("3"))
                            } catch (i) {
                                console.log(i)
                            } finally {
                                d.doSubmit(b, e)
                            }
                        } else c && 404 == c.code ? ($(".m-verifycode").hide(), $(".js-verifycode").val(""), d.prelogin(b, c.data.node)) : d.doSubmit(b, e)
                    },
                    error: function () {
                        d.doSubmit(b, e)
                    }
                })
            }
        }, doSubmit: function (a, b) {
            var c = this;
            "qiyebj" === b || "bj" === b ? "accountlogin" == a.attr("name") ? a.attr("action", "https://entry.qiye.163.com/domain/domainEntLogin") : "adminlogin" == a.attr("name") && a.attr("action", "https://entry.qiye.163.com/domain/domainAdminLogin") : "qiyehz" === b || "hz" === b ? "accountlogin" == a.attr("name") ? a.attr("action", "https://entryhz.qiye.163.com/domain/domainEntLogin") : "adminlogin" == a.attr("name") && a.attr("action", "https://entryhz.qiye.163.com/domain/domainAdminLogin") : "ym" === b && ("accountlogin" == a.attr("name") ? a.attr("action", "https://entry.ym.163.com/login/login") : "adminlogin" == a.attr("name") && a.attr("action", "https://entry.ym.163.com/login/login"));
            var d = c.getQueryString("p");
            return c.isEmpty(d) || "admin" == d && "accountlogin" == a.attr("name") || (a.append('<input type="hidden" id="p" value="" name="p" />'), $("#p").val(d)), a.removeAttr("onsubmit"), a[0].onsubmit = null, a[0].submit(), !0
        }, showTheme: function (a) {
            var b, c = this, d = a.data[0], e = a.data[1], f = a.data[2], g = e.length, h = d.find(".js-linkTheme");
            f ? b = ++c.curThemeIndex % g : (b = --c.curThemeIndex % g, 0 > b && (b = g + b, c.curThemeIndex = g + b)), d.removeClass("u-bd-img1 u-bd-img2 u-bd-img3 u-bd-img4 u-bd-img5 u-bd-img6 u-bd-img7 u-bd-img8 u-bd-img9 u-bd-img10 u-bd-img11 u-bd-img12 u-bd-img13 u-bd-img14"), d.addClass(e[b].bgImgClass), h.attr("href", e[b].bgImgLink)
        }, themeHandler: function () {
            var a = this;
            a.curThemeIndex = Math.round(99 * Math.random());
            var b = $(".js-bdImg"),
                c = [{bgImgClass: "u-bd-img14", bgImgLink: "https://qiye.163.com/hd/couple12/index.html"}];
            b.find(".js-prevTheme").on("click", [b, c, !1], a.showTheme.bind(a)), b.find(".js-nextTheme").on("click", [b, c, !0], a.showTheme.bind(a)), b.find(".js-nextTheme").trigger("click")
        }, helpHandler: function () {
            var a = this, b = a.getQueryString("from"), c = $("#help-url-id");
            "ym" === b ? c.attr("href", "http://app.ym.163.com/ym/help/help.html") : c.attr("href", "http://qiye.163.com/entry/help/help-client.htm")
        }, refreshQrCode: function () {
            var a = $("#appCode");
            return function () {
                var b = "https://mail.qiye.163.com/mailapp/commonweb/qrcode/getqrcode.do?p=qiyemail&w=130&h=130&r=" + (new Date).getTime();
                a.prop("src", b)
            }
        }(), startScan: function () {
            var a = this;
            a.stopScan(), a.refreshQrCode();
            var b = $("#appLoginWait"), c = $("#appLoginScan");
            b.show(), c.hide(), a.scanTimer = setInterval(function () {
                $.ajax({
                    url: "https://mail.qiye.163.com/mailapp/commonweb/qrcode/checkstatus.do?p=qiyemail",
                    type: "get",
                    dataType: "jsonp",
                    success: function (d) {
                        if (d && 200 === d.code) {
                            var e = d.result;
                            0 == e.status || (1 == e.status ? (b.hide(), c.show()) : 2 == e.status ? window.location.href = e.loginurl : 3 == e.status && a.showCodeRefresh())
                        }
                    },
                    error: function () {
                        console.log("connection fail...")
                    }
                })
            }, 1e3)
        }, stopScan: function () {
            var a = this;
            clearInterval(a.scanTimer)
        }, showCodeRefresh: function () {
            var a = this;
            a.stopScan(), $("#appCodeRefresh").show(), $("#appCodeWrap").removeClass("allowmove")
        }, hideCodeRefresh: function () {
            var a = this;
            $("#appCodeRefresh").hide(), $("#appCodeWrap").addClass("allowmove"), a.startScan()
        }, initQrcode: function () {
            var a = this;
            a.scanTimer = null;
            var b = $(".js-codebox");
            $(".js-codeentry").on("click", function () {
                b.show().animate({width: "295px", height: "414px", opacity: "1"}, 200), a.startScan()
            }), $(".js-closeentry").on("click", function () {
                b.animate({width: "0", height: "0", opacity: "0"}, 200), a.stopScan()
            }), $("#appLoginRestart").on("click", function () {
                a.startScan()
            }), $("#appCodeRefresh").on("click", function () {
                a.hideCodeRefresh()
            })
        }, scanCodeHandler: function () {
            var a = this, b = $("#appCodeWrap"), c = $("#howToUseApp"), d = $("#appCodeBox");
            c.on("mouseover", function () {
                b.addClass("hover")
            }), c.on("mouseout", function () {
                b.removeClass("hover")
            }), d.on("mouseover", function () {
                b.addClass("hover")
            }), d.on("mouseout", function () {
                b.removeClass("hover")
            }), a.initQrcode()
        }
    };
    b.init(), b.helpHandler(), b.themeHandler(), b.scanCodeHandler()
}();