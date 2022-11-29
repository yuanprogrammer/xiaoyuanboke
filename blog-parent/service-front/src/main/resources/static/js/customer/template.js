/*打字机*/
$(function(){
    var $str = $("#con span"),
        $show = $("#show"),
        Innt = [],//预定义用存储的数组  
        Index = 0,//预定义用存储的数组的角标
        Itimes = 0,//光标闪烁次数
        FootIndex = 0,//输出时用的角标
        InnerHTML = "",//当前页面已经输出的元素
        Auto = null,//自动打印方法  
        temp = '',//保存标签的变量  
        flag = true,//标记标签已经开始  
        endFlag = false;//判断标签结束
    for (var i = 0; i < $str.length; i++) {
        for(var j = 0; j<$str.eq(i).html().length;j++){
            if($str.eq(i).html()[j] == "<" ) {
                flag = false;
            }
            endFlag = false;
            if($str.eq(i).html()[j] == ">" ) {
                endFlag = true;
            }
            if (flag) {
                Innt[Index++] = $str.eq(i).html()[j];

            } else {
                temp += $str.eq(i).html()[j];
                if (endFlag) {
                    Innt[Index++] = temp;
                    temp = '';
                    flag = true;
                }
            }

        }
    }
    FunOut = function(){
        if (Itimes % 3 != 0) {
            Itimes++;
            $show.html(InnerHTML + "<b>|</b>");
        }else{
            if(FootIndex<Index){
                InnerHTML +=Innt[FootIndex++];
                $show.html(InnerHTML);
            }else{
                clearInterval(Auto);
            }
        }
        Itimes++;
    };
    Auto = setInterval(FunOut,300);
})



/*侧边栏二维码*/
$(function(){
    $('[data-toggle="tooltip"]').tooltip()
})


/*顶部导航滑过显示*/
$(function(){
    $(".wd1").hover(function(){
        $(".wd2").fadeIn();

    },function(){
        $(".wd2").hide();
    })
})
$(function(){
    $(".gy1").hover(function(){
        $(".gy2").fadeIn();
    },function(){
        $(".gy2").hide();
    })
})

/*1秒打开部分滑过显示*/
$(function(){
    $(".c6 a").hover(function(){
        $(this).find("img:first-child").fadeIn();
    },function(){
        $(this).find("img:first-child").fadeOut();
    })
})

/*侧边栏菜单*/
$(function(){
    $(".b2 li").click(function(){
        $(".b2 li span").removeClass("active");
        $(".b2 li div").hide();
        $(this).find("span").addClass("active");
        $(this).find("div").show();
    })
    $(".bul2-a a").click(function(){
        /*$(".b2 li span")*/
        $(".bul2-a a").removeClass("active");
        $(this).addClass("active");
    })
})
/*侧边点击滑出*/
$(function(){
    $("#zd").click(function(){
        $("#main").animate({left:'-500'},300);
        $("#box").animate({right:'-500'},300);
        /*$("#main").animate({width:'toggle'},300);
        $("#box").animate({right:'0'},300);*/
        /*height: 100%;*/
        /*overflow-x: hidden;*/
        $("body").css("height","100%");
        $("body").css("overflow","hidden");
    })
    $("#X").click(function(){
        $("#main").animate({left:'0'},300);
        $("#box").animate({right:'-2880'},300);
        $("body").css("overflow-y","visible");
    })
    $("#wd").click(function(){
        $("#ul1").animate({width:'toggle'},300);
        $("#wd1").animate({right:'0'},300);
    })
    $("#fanhui1").click(function(){
        $("#wd1").animate({right:'-2880'},300);
        $("#ul1").animate({width:'toggle'},300);
    })
    $("#gy").click(function(){
        $("#ul1").animate({width:'toggle'},300);
        $("#gy1").animate({right:'0'},300);
    })
    $("#fanhui2").click(function(){
        $("#gy1").animate({right:'-2880'},300);
        $("#ul1").animate({width:'toggle'},300);
    })
    $("#gcxx").click(function(){
        $("#ul2").animate({width:'toggle'},300);
        $("#gc").animate({right:'0'},300);
    })
    $("#fanhui3").click(function(){
        $("#ul2").animate({width:'toggle'},300);
        $("#gc").animate({right:'-2880'},300);
    })
})