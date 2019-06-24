let arr = [];
$(function(){
    $(".tab_input").on("keydown",function(e) {
        if(e.keyCode == 13){
            let text = $(this).val();
            if(text == ""){
                return;
            }
            if(arr.indexOf(text) != -1){
                return;
            }
            $(".no_tab").hide();
            arr.push(text);
            console.log(arr);
            let a = $('<div class="tab"><span>'+ text +'</span><i class="fa fa-close"></i></div>').appendTo($(".tab_div"));
            i_click(a);
            $(this).val("");
        }
    });
})
var i_click = function(a){
    $(a).children("i").on("click",function(){
        $(this).parent().remove();
        if($(".tab_div").find(".tab").length === 0){
            $(".no_tab").show();       
        }
        arr.remove($(this).parent().children("span").html());
    })
}
Array.prototype.remove = function(val) {
    var index = this.indexOf(val);
    if (index > -1) {
        this.splice(index, 1);
    }
};
