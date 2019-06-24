$(function () {
    $(".reply").on("click", function(){
        // $(this).parents(".b-comment-item").find(".display_form").css("display", "block")
        $(this).parents(".comment-content").find(".display_form").css("display", "block")
    })
    $(".del").on("click", function(){
        $(this).parents(".display_form").css("display", "none")
    });
    $("#btn-send").on('submit', function(){
        return true;
    })
})
function formPro(){
    $("#selecform").on("ajaxSubmit", function(message) {
        // 对于表单提交成功后处理，message为提交页面saveReport.htm的返回内容
        console.log(message)
        $('.content').empty();
        check()
    });
    return true;
}