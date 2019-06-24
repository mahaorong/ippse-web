$(function () {
    $(".minus").on('click', function () {
        var status = $(this).data("status");
        var minus = $(this).data("mp");
        var now = $(this).parents(".number").find(".text-shop").attr("data-now")
        if (status == true) {
            console.log("不能再小了");
            return;
        } else {
            shopCart.minus_plus($(this), minus, now);
        }
    })

    $(".plus").on('click', function () {
        var status = $(this).data("status");
        var plus = $(this).data("mp")
        var now = $(this).parents(".number").find(".text-shop").attr("data-now")
        if (status == true) {
            console.log("已经是最大了哈")
            return;
        } else {
            shopCart.minus_plus($(this), plus, now);
        }
    })
})

const shopCart = {
    minus_plus: function (el, mp, now) {
        if (mp == "plus") {
            this.plus(el, now);
        } else {
            this.minus(el, now);
        }
    },
    plus: function (el, now) {
        var val = el.parents(".number").find(".text-shop").val()
        var par = parseInt(val);
        var adpar = ++par;
        var max = el.parents(".number").find(".text-shop").data("max");
        var nownum = now;
        if (nownum >= max) {
            el.attr("data-status", true)
            return true;
        } else {
            el.parents(".number").find(".text-shop").val(adpar);
            el.parents(".number").find(".text-shop").attr("data-now", adpar);
            if (nownum >= 1) {
                el.parents(".number").find(".minus").attr("data-status", false)
            }
        }
    },
    minus: function (el, now) {
        var val = el.parents(".number").find(".text-shop").val()
        var par = parseInt(val);
        var adpar = --par;
        var max = el.parents(".number").find(".text-shop").data("max");
        var nownum = now;
        if (nownum <= 1) {
            el.attr("data-status", true)
            return true;
        } else {
            el.parents(".number").find(".text-shop").val(adpar);
            el.parents(".number").find(".text-shop").attr("data-now", adpar);
            el.parents(".number").find(".minus").attr("data-status", false)
        }
    }
}