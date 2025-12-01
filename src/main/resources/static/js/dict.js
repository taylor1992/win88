
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
    debugger;
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
