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
	  var customerNm = document.getElementById("customerNm").value;
	  if(customerNm == undefined || customerNm == null || customerNm.trim() == '' || customerNm.trim().length == 0)
		  {
		  alert('Please enter customer name.');
		  document.getElementById("customerNm").focus();
		  return false;
		  }
	  if (!/^[a-z A-Z]*$/g.test(customerNm)) {
	        alert("please enter characters only for customer name");
	        document.getElementById("customerNm").select();
	        return false;
	    }

	   var mob=document.getElementById("mobile").value;
		if ( mob.length!=10) 
			
		{
			alert("please enter 10 digit mobile number");
			document.getElementById("mobile").select();
			return false;}


if (!/^[0-9]+$/.test(mob)) {
    alert("please enter numbers only for ph no");
    document.getElementById("mobile").select();
    return false;
}
     


	  return true;
  }