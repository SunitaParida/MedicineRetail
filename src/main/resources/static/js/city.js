$(document).ready(function() {

	$("#state").change(function() {
		
		plotCity($(this).val());

	});

});

function plotCity(stateId)
{

	var stateName = $("#state option:selected").text();

	var state = {
		"stateId" : stateId
	};

	$.ajax({
		type : 'POST',
		contentType : "application/json",
		url : "/admin/getCityListInfo",
		data : JSON.stringify(state),
		dataType : "json",
		success : function(data) {
			
			var cities = "<option  value='0'>-- Select City -- </option>";
			for(i=0;i<data.length;i++)
				{
				cities += "<option value='"+data[i].cityId+"'>"+data[i].cityName+"</option>";
				}
			
			$("#city").html(cities);
			
		},
		error : function(data) {
			alert('failure');
		}
	});	
}
	function plotCityOnSelect(stateId,cityId)
	{
	debugger
		var stateName = $("#state option:selected").text();

		var state = {
			"stateId" : stateId
		};

		$.ajax({
			type : 'POST',
			contentType : "application/json",
			url : "/admin/getCityListInfo",
			data : JSON.stringify(stateId),
			dataType : "json",
			success : function(data) {
				
				var cities = "<option  value='-1'>-- Select City -- </option>";
				for(i=0;i<data.length;i++)
					{
					if(cityId == data[i].cityId)
					cities += "<option selected value='"+data[i].cityId+"'>"+data[i].cityName+"</option>";
					else
						cities += "<option  value='"+data[i].cityId+"'>"+data[i].cityName+"</option>";
					}
				
				$("#city").html(cities);
				
			},
			error : function(data) {
				alert('failure');
			}
		});	
}
																		
