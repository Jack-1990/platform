/**
 *
 * @authors Your Name (you@example.org)
 * @date    2016-05-24 14:39:57
 * @version $Id$
 */
//滚动条
function scrollFn(scrollContain,scrollContent,scrollbar){
    var scloll=0;
    var $scrollContent=$("#"+scrollContent),$scrollHeight=$scrollContent.height();
    var $iframe=$("#"+scrollContain),$iframeHeight=$iframe.height();
    var $scrollbar=$("#"+scrollbar),$scrollbarHeight=$scrollbar.height();
    $iframe.css("overflow","hidden");$scrollContent.css("position","absolute");
    function scrollFunc(e){
        var e=e||window.event;
        $scrollHeight=$scrollContent.height();
        var wheelValue=e.wheelDelta/120||-e.detail;
        var dir=wheelValue>0?"up":"down";
        if(dir=="up"){scloll+=40;}else{scloll-=40;}
        if(Math.abs(scloll)>$scrollHeight-$iframeHeight){
            scloll=-$scrollHeight+$iframeHeight;
        }
        if(scloll>0){scloll=0;}
        $("#"+scrollContent).stop().animate({top:scloll+"px"},50);
        var $percent=Math.abs(scloll)/($scrollHeight-$iframeHeight);
        var $scrolllength=$iframeHeight-$scrollbarHeight;
        $scrollbar.animate({top:$percent*$scrolllength+"px"},50);
        e.preventDefault();
        return false;
    }
//滑块滚动
    if(scrollbar){
        $scrollbar.css({"opacity":"0"});
        $iframe.hover(function(){
            $scrollbar.animate({"opacity":0});
        },function(){
            $scrollbar.animate({"opacity":"0"});
        })
        document.getElementById(scrollbar).onmousedown=function(e){
            var objY=this.offsetTop;
            var e=e||window.event;
            var y=e.clientY;
            var realY=y-objY;
            var _this=this;
            $scrollHeight=$scrollContent.height();
            document.onmousemove=function(e){
                var e=e||window.event;
                console.log(objY)
                var y1=e.clientY;
                var realY2=y1-realY;
                if(realY2<0){
                    realY2=0;
                }else if(realY2>$iframeHeight-$scrollbarHeight){
                    realY2=$iframeHeight-$scrollbarHeight;
                }
                _this.style.top=realY2+"px";
                $("#"+scrollContent).stop().animate({top:-realY2/($iframeHeight-$scrollbarHeight)*($scrollHeight-$iframeHeight)+"px"},0);
                document.onmouseup=function(){
                    document.onmousemove=null;
                }
                e.preventDefault();
                return false;
            }

        }
    }
    if(document.addEventListener){
        document.getElementById(scrollContain).addEventListener('DOMMouseScroll',scrollFunc,false);
    }
    document.getElementById(scrollContain).onmousewheel=scrollFunc;
//移动端触摸滚动
    var x,y;
    if('ontouchstart' in window){
        document.getElementById(scrollContain).ontouchstart=function(e){
            var e=e||window.event;
            var touch = e.touches[0];
            var x = touch.clientX;
            var y = touch.clientY;
            $scrollHeight=$scrollContent.height();
            this.ontouchmove=function(e){
                var e=e||window.event;
                var touch = e.touches[0];
                x1 = touch.clientX;
                y1 = touch.clientY;
                if(y1-y>20){scloll+=5;}else if(y1-y<-20){scloll-=5;}
                $("#"+scrollContent).stop().animate({top:scloll+"px"},200);
                if(Math.abs(scloll)>$scrollHeight-$iframeHeight){
                    scloll=-$scrollHeight+$iframeHeight;
                }
                if(scloll>0){scloll=0;}
                e.preventDefault();
                return false;
            }
        }
    }
}
$("#data-move").css("height",(document.documentElement.clientHeight-$(".header").eq(0).height())+"px")
scrollFn("data-move","data-movecontent","scrollbar2");



