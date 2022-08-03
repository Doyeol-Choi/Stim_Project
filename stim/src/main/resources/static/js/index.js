$(function() {
	var swiper = new Swiper("#newGame", {
	    slidesPerView: 'auto',
	    centeredSlides: true,
	    spaceBetween: 25,
	    loop: true,
	    observer: true,
	  	observeParents: true,
	    navigation: {
	        nextEl: ".swiper-button-next",
	        prevEl: ".swiper-button-prev",
	    },
	})
	
	var index = 1;
	mainView(index);
})

function changeMain(n) {
	clearTimeout(timeId);
	clearInterval(id);
    mainView(n);
}

function mainView(n) {
    var x = document.getElementsByClassName("mainSlides");
    var y = document.getElementsByClassName("subG");
    var z = document.getElementsByClassName("subG_Bar");
    index = n;
    if (n > x.length) {index = 1}
    if (n < 1) {index = x.length};
    for (let i = 0; i < x.length; i++) {
        x[i].style.display = "none";
        y[i].style.opacity = 0.3;
        z[i].style.width = '0%';
    }
    x[index-1].style.display = "block";
    y[index-1].style.opacity = 0.8;
    timeId = setTimeout("mainView(index)", 5000);
    move(index);
    index++;
}

function move(idx) {
	var name = "bar"+idx;
  	var bar = document.getElementById(name);
  	var width = 100;
  	id = setInterval(frame, 5);
	function frame() {
    	if (bar.style.width == '1%') {
      		clearInterval(id);
    	} else {
      	width -= 0.1; 
      	bar.style.width = width + '%'; 
		}
	}
}