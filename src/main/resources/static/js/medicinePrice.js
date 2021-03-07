$(document).ready(function() {

	$("#medicine").change(function() {
		if($(this).val()!=0){
			
		plotMedicinePrice($(this).val());
		}else{
			alert("Choose a medicine");
		}
	});

});

function plotMedicinePrice(medicine)
{

	
	$.ajax({
		type : 'POST',
		contentType : "application/json",
		url : "/admin/readMedicinePrice/"+medicine,
		data : {},
		dataType : "json",
		success : function(data) {
			
			var medicinePrice = data;
			
			document.getElementById("price").value=parseFloat(medicinePrice).toFixed(2);
				
					},
		error : function(data) {
			alert('failure');
		}
	});	
}