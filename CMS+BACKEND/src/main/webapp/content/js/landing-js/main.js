
window.mainHolder = function () {
	$(window).scroll(function () {
		var scroll = $(window).scrollTop();
		if (scroll > 100) {
			$('.navbar').removeClass('navbar-dark').addClass('navbar-light');
		}

		else {
			$('.navbar').removeClass('navbar-light').addClass('navbar-dark');
		}
	});

    $(".navbar a").on("click", function () {
        $(".navbar").find(".active").removeClass("active");
        $(this).parent().addClass("active");
    });
};


