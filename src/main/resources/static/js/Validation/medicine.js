  function validate()
  {
	  var store=document.getElementById("store").value;
	  if(store==0)
		  {
		  alert("please select store");
		  document.getElementById("store").focus();
		  return false;
		  }
	  var medicine=document.getElementById("medicine").value;
	  if(medicine==0)
		  {
		  alert("please select medicine");
		  document.getElementById("medicine").focus();
		  return false;
		  }
	  var name = document.getElementById("name").value;
	  if(name == undefined || name == null || name.trim() == '' || name.trim().length == 0)
		  {
		  alert('Please enter quantity of medicine.');
		  document.getElementById("name").focus();
		  return false;
		  }
	  if (!/^[0-9]+$/.test(name)) {
	        alert("please enter numbers only for quantity of medicine");
	        document.getElementById("name").select();
	        return false;
	    }

	  
	  var expireDate = document.getElementById("expireDate").value;
	  if(expireDate == undefined || expireDate == null || expireDate.trim() == '' || expireDate.trim().length == 0)
		  {
		  document.getElementById("expireDate").focus();
		  alert('Please provide expire date of the medicine.');
		  return false;
		  }
	  
		
	    var cdate= new Date(expireDate);
	    var today=new Date();
	    today.setHours(0,0,0,0);
	    if(cdate<today)
	    {
			alert("previous  date can't be taken..Plz try again");
			document.getElementById("expireDate").select();
			return false;
			}

	  return true;
  }
  
  