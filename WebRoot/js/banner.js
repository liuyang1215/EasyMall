window.onload = function () { 
   flag = 0; 
   obj1 = document.getElementById("slider"); 
   obj2 = document.querySelectorAll("#wrap li");
   obj2[0].style.backgroundColor = "#666666";
   //默认被选中颜色 
   time = setInterval("turn();", 5000); 
   obj1.onmouseover = function () { 
    clearInterval(time); 
   } 
   obj1.onmouseout = function () { 
    time = setInterval("turn();", 6000); 
   } 
  
   for (var num = 0; num < obj2.length; num++) { 
    obj2[num].onmouseover = function () { 
     turn(this.innerHTML); 
     clearInterval(time); 
    } 
    obj2[num].onmouseout = function () { 
     time = setInterval("turn();", 6000); 
    } 
   } 
   //延迟加载图片，演示的时候，使用本地图片
   //上线后请改为二级域名提供的图片地址 
   document.getElementById("second").src = "img/index/2.jpg";
   //使用图片宽660，高550 
   document.getElementById("third").src = "img/index/3.jpg"; 
   document.getElementById("four").src = "img/index/4.jpg"; 
  }

  function turn(value) { 
   if (value != null) { 
    flag = value - 2; 
   } 
   if (flag < obj2.length - 1) 
    flag++; 
   else
    flag = 0; 
   obj1.style.top = flag * (-400) + "px"; 
   for (var j = 0; j < obj2.length; j++) { 
    obj2[j].style.backgroundColor = "#ffffff"; 
   } 
   obj2[flag].style.backgroundColor = "#666666"; 
  } 