/* =======================================
 * jquery-pagination.js v1.0
 * =======================================
 * Date 2015/01/23
 * */
(function($){
	/* Pagination definition
	 * ===================================*/
	
	
	$.fn.pagination = function (options) {
		
		var pagination = new PaginationTemplate(this, options);
		
		pagination.init();
	};
	
	var PaginationTemplate = function(element, options){
		var _defaults = {
				pageSize: 35,     //Integer: default page size
				startPage: 1,    //Integer: current page that show on start (default: 1)
				pagesInGroup: 12, //Integer: the number of pages in one group
				pagesToMove: 2,  //Integer: the maximum number of pages that moves each time 
				pageClass: 'page',
				prevClass: 'prev',
				nextClass: 'next',
				prev: ' ',
				next: ' ',
				activeClass: 'active',
				disabledClass: 'disabled'
		};
		
		var _opts = $.extend({}, _defaults, options);

		var $container = $(element);
		
		function _render(page){
			
			var _start = page,
				total = _opts.pagesInGroup,
				numeric = _start,
				last = _start + total - 1,
				prev = _start;
			
			if(_opts.prev){
				$container.append(_buildItem('prev', _opts.prev));	
			}
			
			for(var i = 0; i < total; i ++) {
				$container.append(_buildItem('page', numeric ++));
			}
			
			if(_opts.next){
				$container.append(_buildItem('next', _opts.next));
			}
			
			if(last == _opts.pageSize){
				$container.children('li.' + _opts.nextClass).addClass(_opts.disabledClass);
			}else {
				goTo(_opts.nextClass, _start + _opts.pagesToMove);
			}
			if(prev == 1){
				$container.children('li.' + _opts.prevClass).addClass(_opts.disabledClass);
			}else{
				goTo(_opts.prevClass, _start - _opts.pagesToMove);
			}
			
		};
		
		function _getStartPage (page){
			var _start = page;
			var _end = _start + _opts.pagesInGroup -1;
			
			if(_start <=0) {
				_start = 1;
			}
			if(_start > 0 && _end > _opts.pageSize) {
				
				_start = _opts.pageSize - _opts.pagesInGroup + 1;
			}
			
			return _start;
		};
		
		function _buildItem(type, page) {
			var itemContainer = $('<li></li>'),
				itemContent = $('<a href="#"></a>'),
				itemText = null;
			
			switch (type) {
				case 'page' :
					itemText = page;
					itemContent.text(itemText + 'F');
					itemContainer.addClass(_opts.pageClass);
					itemContainer.append(itemContent);
					break;
				case 'prev' :
					itemText = _opts.prev;
					itemContent.text(itemText);
					itemContainer.addClass(_opts.prevClass);
					itemContainer.append(itemContent.attr('href', 'javascript: void(0);'));
					break;
				case 'next' :
					itemText = _opts.next;
					itemContent.text(itemText);
					itemContainer.addClass(_opts.nextClass);
					itemContainer.append(itemContent.attr('href', 'javascript: void(0);'));
					break;
			}

			return itemContainer;
		};
		
		function goTo(className, offset) {
			$container.children('li.' + className).click(function(){
				$container.empty();
				_render(_getStartPage(offset));			
			});
		};
		
		return {
			init: function(){
				_render(_getStartPage(_opts.startPage));
			}
		}
	};
	
	
})(jQuery);