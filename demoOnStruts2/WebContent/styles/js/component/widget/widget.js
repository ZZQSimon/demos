$(function() {
	$("#rowNum").combobox({
		onChange:function(rowNum){
			
			if(rowNum == 1){
				$("#column2").hide();
				$("#column3").hide();
				$("#column4").hide();
			}
			
			if(rowNum == 2){
				$("#column2").show();
				$("#column3").hide();
				$("#column4").hide();
			}
				
			if(rowNum == 3){
				$("#column2").show();
				$("#column3").show();
				$("#column4").hide();
			}
			
			if(rowNum == 4){
				$("#column2").show();
				$("#column3").show();
				$("#column4").show();
			}
	}});
});