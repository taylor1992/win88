
// 全局字典缓存
var DictCache = DictCache || {};

/**
 * 加载单个字典，兼容旧代码
 * @param dictType  字典类型
 * @param callback  function(list)
 */
function loadDict(dictType, callback) {
    loadDicts([dictType], function () {
        callback && callback(DictCache[dictType] || []);
    });
}


/**
 * 批量加载多个字典（推荐在页面初始化时用）
 * @param dictTypes  字符串或数组，比如 'productStatus' 或 ['shopType', 'enable']
 * @param callback   function(cache)  // cache 就是 DictCache
 */
function loadDicts(dictTypes, callback) {
    if (typeof dictTypes === 'string') {
        dictTypes = [dictTypes];
    }

    // 过滤出缓存里还没有的
    var need = [];
    for (var i = 0; i < dictTypes.length; i++) {
        var t = dictTypes[i];
        if (!DictCache[t]) {
            need.push(t);
        }
    }

    // 都在缓存里了，直接回调
    if (need.length === 0) {
        callback && callback(DictCache);
        return;
    }

    $.ajax({
        url: ctx + 'api/dict/batch',
        type: 'GET',
        traditional: true,          // 关键：让 ?types=a&types=b 这种形式生效
        data: {types: need},
        success: function (res) {
            if (res.code === 0 && res.data) {
                var data = res.data;
                // 写入缓存
                for (var key in data) {
                    if (data.hasOwnProperty(key)) {
                        DictCache[key] = data[key] || [];
                    }
                }
            } else {
                layer && layer.msg && layer.msg(res.msg || '加载字典失败');
            }
            callback && callback(DictCache);
        },
        error: function () {
            layer && layer.msg && layer.msg('请求字典接口异常');
            callback && callback(DictCache);
        }
    });
}


/**
 * 根据字典值取 label，用于列表渲染
 * @param dictType  字典类型
 * @param value     后端存的 int 值
 */
function getDictLabel(dictType, value) {
    var list = DictCache[dictType] || [];
    for (var i = 0; i < list.length; i++) {
        if (String(list[i].value) === String(value)) {
            return list[i].label;
        }
    }
    return value == null ? '' : value;
}

/**
 * 渲染下拉框
 * @param dictType    字典类型
 * @param $select     jQuery 对象，如 $('#status')
 * @param placeholder 占位文字
 * @param formFilter  layui form 的 lay-filter（可选）
 */
function renderDictSelect(dictType, $select, placeholder, formFilter) {
    debugger;
    loadDict(dictType, function (list) {
        $select.empty();
        if (placeholder) {
            $select.append('<option value="">' + placeholder + '</option>');
        }
        $.each(list, function (i, item) {
            $select.append(
                '<option value="' + item.value + '">' + item.label + '</option>'
            );
        });

        if (layui && layui.form) {
            if (formFilter) {
                layui.form.render('select', formFilter);
            } else {
                layui.form.render('select');
            }
        }
    });
}

/**
 * 根据缓存渲染 select，不再请求接口（内部使用）
 */
function renderDictSelectFromCache(dictType, $select, placeholder, formFilter) {
    var list = DictCache[dictType] || [];

    $select.empty();
    if (placeholder) {
        $select.append('<option value="">' + placeholder + '</option>');
    }

    $.each(list, function (i, item) {
        $select.append(
            '<option value="' + item.value + '">' + item.label + '</option>'
        );
    });

    if (layui && layui.form) {
        if (formFilter) {
            layui.form.render('select', formFilter);
        } else {
            layui.form.render('select');
        }
    }
}

/**
 * 扫描页面所有 .dict-select 元素，统一加载&渲染字典
 *
 * 约定：
 *   <select class="dict-select" data-dict-type="PurchaseStatus" data-placeholder="Select one"></select>
 *
 * @param callback function(cache)  // 字典全部加载并渲染完之后调用
 */
function initDictSelects(callback) {
    var $all = $('.dict-select');
    if (!$all.length) {
        callback && callback(DictCache);
        return;
    }

    // 收集本页需要的字典类型（去重）
    var typeMap = {};
    $all.each(function () {
        var t = $(this).data('dictType');   // data-dict-type
        if (t) {
            typeMap[t] = true;
        }
    });

    var types = Object.keys(typeMap);
    if (!types.length) {
        callback && callback(DictCache);
        return;
    }

    // 1) 先批量加载所有需要的字典
    loadDicts(types, function () {
        // 2) 再根据 DictCache 渲染所有 select
        $all.each(function () {
            var $sel = $(this);
            var t = $sel.data('dictType');
            var placeholder = $sel.data('placeholder') || '';

            var list = DictCache[t] || [];

            $sel.empty();
            if (placeholder) {
                $sel.append('<option value="">' + placeholder + '</option>');
            }
            $.each(list, function (i, item) {
                $sel.append(
                    '<option value="' + item.value + '">' + item.label + '</option>'
                );
            });
        });

        // 3) 统一 render 一次 select
        if (window.layui && layui.form) {
            layui.form.render('select');
        }

        // 4) 告诉外面“所有字典 select 已经准备好”
        callback && callback(DictCache);
    });
}
