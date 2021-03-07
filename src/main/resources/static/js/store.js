$(document).ready(function() {

	$("#city").change(function() {
		
		plotBranch($(this).val());

	});

});

function plotBranch(cityId)
{

	var cityName = $("#city option:selected").text();

	var city = {
		"cityId" : cityId
	};

debugger;
	$.ajax({
		type : 'POST',
		contentType : "application/json",
		url : "/admin/getBranchListInfo",
		data : JSON.stringify(city),
		dataType : "json",
		success : function(data) {
			
			var branches = "<option  value='0'>-- Select Branch -- </option>";
			for(i=0;i<data.length;i++)
				{
				branches += "<option value='"+data[i].branchId+"'>"+data[i].branchName+"</option>";
				}
			
			$("#branch").html(branches);
			
		},
		error : function(data) {
			alert('failure');
		}
	});	
}
function plotBranchOnSelect(cityId,branchId)
	{
	
	var cityName = $("#city option:selected").text();

	var city = {
		"cityId" : cityId
	};

	debugger
	

		$.ajax({
			type : 'POST',
			contentType : "application/json",
			url : "/admin/getBranchListInfo",
			data : JSON.stringify(city),
			dataType : "json",
			success : function(data) {
				var branches = "<option  value='-1'>-- Select Branch -- </option>";
				for(i=0;i<data.length;i++)
					{
					
					if(branchId == data[i].branchId)
					branches += "<option selected value='"+data[i].branchId+"'>"+data[i].branchName+"</option>";
					else
						branches += "<option  value='"+data[i].branchId+"'>"+data[i].branchName+"</option>";
					}
				
				$("#branch").html(branches);
				
			},
			error : function(data) {
				alert('failure');
			}
		});	
}
	
																		
