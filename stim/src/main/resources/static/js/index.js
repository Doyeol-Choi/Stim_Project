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
});
