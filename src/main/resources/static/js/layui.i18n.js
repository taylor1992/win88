// layui.en.js —— Layui 分页中英文切换（不依赖 render 返回值）
;(function (win) {
    if (!win.layui) return;

    var LANG = win.APP_LANG || 'en';

    var PACK = {
        zh: {
            first: '首页',
            last: '尾页',
            prev: '上一页',
            next: '下一页',
            countText: function (count) { return '共 ' + count + ' 条'; },
            limitText: function (limit) { return limit + ' 条/页'; },
            skipPrefix: '到第',
            skipBtn: '确定'
        },
        en: {
            first: 'First',
            last: 'Last',
            prev: 'Prev',
            next: 'Next',
            countText: function (count) { return 'Total ' + count + ' items'; },
            limitText: function (limit) { return limit + ' / page'; },
            skipPrefix: 'Go to',
            skipBtn: 'Go'
        }
    };

    layui.config({
        table: {
            text: {
                none: "No data"
            }
        }
    });


    layui.use(['laypage', 'jquery'], function () {
        var laypage = layui.laypage;
        var $ = layui.$;
        if (!laypage || !laypage.render) return;

        var pack = PACK[LANG];
        var _render = laypage.render;

        // 覆盖 render
        laypage.render = function (opts) {
            opts = opts || {};

            // 1. 按语言设置按钮文字
            opts.first = pack.first;
            opts.last  = pack.last;
            opts.prev  = pack.prev;
            opts.next  = pack.next;

            if (!opts.layout) {
                opts.layout = ['prev', 'page', 'next', 'count', 'limit', 'skip'];
            }

            // 2. 把用户自己的 jump 包一层
            var userJump = opts.jump;
            opts.jump = function (obj, first) {
                // 先调用原来的 jump（如果有）
                userJump && userJump(obj, first);

                // 再做国际化处理
                var $page = $('.layui-laypage').last();   // 通常只有一个分页，用 last 即可
                if (!$page.length) return;

                // (1) "共 X 条" → "Total X items"
                var $count = $page.find('.layui-laypage-count');
                if ($count.length) {
                    $count.text(pack.countText(obj.count));
                }

                // (2) "10 条/页" 下拉选项 → "10 / page"
                var $limitSelect = $page.find('.layui-laypage-limits select');
                if ($limitSelect.length) {
                    $limitSelect.find('option').each(function () {
                        var txt = this.textContent || '';
                        var m = txt.match(/(\d+)/);
                        if (!m) return;
                        var num = m[1];
                        if (LANG === 'en') {
                            this.textContent = num + ' / page';
                        } else {
                            this.textContent = num + ' 条/页';
                        }
                    });
                }

                // (3) "到第  [input]  页  确定" → "Go to [input] Go"
                var $skip = $page.find('.layui-laypage-skip');
                if ($skip.length) {
                    // 清掉原来的文本节点，只保留 input 和 button
                    $skip.contents().each(function () {
                        if (this.nodeType === 3) {
                            this.textContent = '';
                        }
                    });

                    // 在最前面加前缀文本
                    if (!$skip.data('i18n-added')) {
                        $skip.prepend(document.createTextNode(pack.skipPrefix + ' '));
                        $skip.data('i18n-added', true);
                    }

                    var $btn = $skip.find('.layui-laypage-btn');
                    if ($btn.length) {
                        $btn.text(pack.skipBtn);
                    }
                }
            };

            // 3. 调回原始 render
            return _render.call(laypage, opts);
        };
    });

    layui.use(['table'], function () {
        var table = layui.table;

        // 只在英文环境覆盖
        if (LANG === 'en') {
            table.set({
                text: {
                    none: 'No data'
                }
            });
        } else {
            table.set({
                text: {
                    none: '无数据'
                }
            });
        }
    });
})(window);
