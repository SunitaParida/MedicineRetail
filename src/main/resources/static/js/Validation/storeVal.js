  function validate()
  {
	  var state=document.getElementById("state").value;
	  if(state==0)
		  {
		  alert("please select state");
		  document.getElementById("state").focus();
		  return false;
		  }
	  var city=document.getElementById("city").value;
	  if(city==0)
		  {
		  alert("please select city");
		  document.getElementById("city").focus();
		  return false;
		  }
	  var name = document.getElementById("name").value;
	  if(name == undefined || name == null || name.trim() == '' || name.trim().length == 0)
		  {
		  alert('Please provide Store name.');
		  document.getElementById("name").focus();
		  return false;
		  }
	  
	  var description = document.getElementById("description").value;
	  if(description == undefined || description == null || description.trim() == '' || description.trim().length == 0)
		  {
		  document.getElementById("description").focus();
		  alert('Please provide the store description.');
		  return false;
		  }
	  
	  
	  return true;
  }
  
  