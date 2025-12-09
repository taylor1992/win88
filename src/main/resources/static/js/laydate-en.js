// laydate-en.js
// 统一英文版 laydate 封装

;(function (win) {

    // 英文本地化配置
    var EN_LOCALE = {
        weeks: ['Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'],
        months: [
            'January', 'February', 'March', 'April', 'May', 'June',
            'July', 'August', 'September', 'October', 'November', 'December'
        ],
        btns: {
            now: 'Now',
            confirm: 'OK',
            clear: 'Clear'
        }
    };

    /**
     * 内部：把当前打开的日期面板改成英文
     */
    function applyEnglishLocale() {
        setTimeout(function () {
            // 星期
            $(".laydate-week-list th").each(function (i) {
                if (EN_LOCALE.weeks[i]) {
                    $(this).text(EN_LOCALE.weeks[i]);
                }
            });

            // 年 + 月显示
            $(".laydate-set-ym span").each(function (i) {
                // i: 0 年份  1 月份
                if (i === 1) {
                    var text = $(this).text();   // “12月” / “12”
                    var num = parseInt(text, 10);
                    if (!isNaN(num)) {
                        var idx = num - 1;
                        if (EN_LOCALE.months[idx]) {
                            $(this).text(EN_LOCALE.months[idx]);
                        }
                    }
                }
            });

            // 底部按钮
            $(".laydate-footer .laydate-btns-clear").text(EN_LOCALE.btns.clear);
            $(".laydate-footer .laydate-btns-now").text(EN_LOCALE.btns.now);
            $(".laydate-footer .laydate-btns-confirm").text(EN_LOCALE.btns.confirm);
        }, 10);
    }

    /**
     * 通用英文版 datetime 组件
     *
     * @param {Object} config laydate.render 的配置（elem 必填）
     *                        可覆盖默认 type/format/theme 等
     *
     * 例子：
     *   LaydateEn.render({
     *     elem: '#createdTimeBegin'
     *   });
     */
    function render(config) {
        if (!config || !config.elem) {
            console.error('LaydateEn.render: elem is required');
            return;
        }
        var laydate = layui.laydate;

        // 默认配置（可被外部 config 覆盖）
        var defaultCfg = {
            type: 'datetime',
            format: 'yyyy-MM-dd HH:mm:ss',
            lang: 'en',         // 自带部分英文
            calendar: true,
            theme: 'molv'
        };

        // 合并配置（浅拷贝即可）
        var finalCfg = {};
        for (var k in defaultCfg) {
            finalCfg[k] = defaultCfg[k];
        }
        for (var k2 in config) {
            finalCfg[k2] = config[k2];
        }

        // 包一层 ready：先做英文替换，再调用用户传入的 ready
        var userReady = finalCfg.ready;
        finalCfg.ready = function () {
            applyEnglishLocale();
            if (typeof userReady === 'function') {
                userReady.apply(this, arguments);
            }
        };

        return laydate.render(finalCfg);
    }

    // 暴露一个全局对象
    win.LaydateEn = {
        render: render,
        _applyEnglishLocale: applyEnglishLocale   // 如果你想特殊情况手动调用也可以
    };

})(window);
