
$(function(){
    $(".slidebar .slidebar-list li").click(function() {
        $(this).addClass("current").siblings("li").removeClass("current");
    });
    
})