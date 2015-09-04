 function refreshWookmark(){
  (function ($){
      $('#tiles').imagesLoaded(function() {
        // Prepare layout options.
        var options = {
          itemWidth: 100, // Optional min width of a grid item
          autoResize: true, // This will auto-update the layout when the browser window is resized.
          container: $('#tiles'), // Optional, used for some extra CSS styling
          offset: 5, // Optional, the distance between grid items
          outerOffset: 20, // Optional the distance from grid to parent
          flexibleWidth: '50%' // Optional, the maximum width of a grid item
        };

        // Get a reference to your grid items.
        var handler = $('#tiles li');

        var $window = $(window);
        $window.resize(function() {
          var windowWidth = $window.width(),
              newOptions = { flexibleWidth: '100%' };

          // Breakpoint
          if (windowWidth < 1024) {
            newOptions.flexibleWidth = '100%';
          }

          handler.wookmark(newOptions);
        });

        // Call the layout function.
        handler.wookmark(options);
      });
    })(jQuery);
  }
  
    (function ($){
      $('#tiles').imagesLoaded(function() {
        // Prepare layout options.
        var options = {
          itemWidth: 100, // Optional min width of a grid item
          autoResize: true, // This will auto-update the layout when the browser window is resized.
          container: $('#tiles'), // Optional, used for some extra CSS styling
          offset: 5, // Optional, the distance between grid items
          outerOffset: 20, // Optional the distance from grid to parent
          flexibleWidth: '50%' // Optional, the maximum width of a grid item
        };

        // Get a reference to your grid items.
        var handler = $('#tiles li');

        var $window = $(window);
        $window.resize(function() {
          var windowWidth = $window.width(),
              newOptions = { flexibleWidth: '100%' };

          // Breakpoint
          if (windowWidth < 1024) {
            newOptions.flexibleWidth = '100%';
          }

          handler.wookmark(newOptions);
        });

        // Call the layout function.
        handler.wookmark(options);
      });
    })(jQuery);
	
	////////////////////////////////////////////////////////// x for removing images
	
	function addXCloser(element){
	a = document.createElement("img");
	a.src="img/x.png";
	a.className="close";
	a= $(a);
	a.css("position","absolute");
	a.css("top","0px");
	a.css("right","0px");
	a.css("width","15px");
	a.css("height","15px");
	element.parent().append(a);
	a.hide();
	a.bind('click',function(){
		b=element;
		while (!b.hasClass("thumb")){
			b=b.parent();
			console.log("a");
		}
		b.remove();
		refreshWookmark();
	});
	}
	 $(function(){
  $.each($(".thumb_img"),function(){
	addXCloser($(this));
});	

    $(".thumb").hover(function(){
		$(this).children().each(function(){
		if ($(this).hasClass("close")){
			$(this).show();
			}
		});

	},
	function(){
		$(this).children().each(function(){
		if ($(this).hasClass("close")){
			$(this).hide();
		}
		});
	}
	);
	  });
////////////////////////////////////////////////////////