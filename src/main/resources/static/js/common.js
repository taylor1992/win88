/**
 * 自动给必填项的 label 加红星
 * @param {String} container 可选，表单容器选择器，如 '#addForm'
 */
function autoMarkRequired(container) {
    // 用 layui 自带的 jQuery
    var $ = layui.$ || window.jQuery;

    // 默认扫描整个页面，也可以传入某个 form 容器
    var $root = container ? $(container) : $(document);

    // 找到所有有 lay-verify 的表单元素
    $root.find('[lay-verify]').each(function () {
        var $field = $(this);
        var verify = $field.attr('lay-verify') || '';

        // 只处理包含 required 的规则（支持 required|number 这种写法）
        if (verify.indexOf('required') === -1) {
            return;
        }

        // 找到最近的 .layui-form-item
        var $item = $field.closest('.layui-form-item');
        if ($item.length === 0) {
            return;
        }

        // 找该 form-item 里的 label
        var $label = $item.find('.layui-form-label').first();
        if ($label.length === 0) {
            return;
        }

        // 已经有红星就不重复加
        if ($label.find('.layui-form-required').length > 0) {
            return;
        }

        // 追加红星
        $label.append('<span class="layui-form-required">*</span>');
    });
}
