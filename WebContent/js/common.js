function disablePageElement(){
	$('input').attr("disabled","disabled");
	$('select').attr("disabled","disabled");
	$('checkbox').attr("disabled","disabled");
	$('textarea').attr("disabled","disabled");
	$('input[type=submit]').remove();
	$('#cancel').removeAttr('disabled');
	$('#cancel').val('关闭');
	var title = $('div.article_tit').eq(0);
	var newtext = title.html().replace("修改","查看").replace("新建","查看");
	title.html(newtext);
}

function checkAll(ele){
	if($(ele).is(':checked')){
		$('input[name=select][selected!=selected]').attr("checked",'checked');
	}else{
		$('input[name=select]').removeAttr("checked");
	}		
}

function tabChangeForProperty(className, dom){
	$(className + ' ' + dom).each(function(){
	 	var fileName1 = location.pathname;
	 	var fileName2 = location.search;
	 	var fileName = fileName1+fileName2;
	 	var urlName = $(this).attr('href');
	 	fileName = fileName.split('/');
		fileName = fileName[fileName.length - 1];
  		if (urlName == fileName) {
  			$(this).addClass('active');
  		}
 	 });
}