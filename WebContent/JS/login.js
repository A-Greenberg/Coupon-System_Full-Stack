	var xmlhttp = new XMLHttpRequest();
	

	function login(){
		var url;
		var username = document.getElementById("username").value;
		var password = document.getElementById("password").value;
		
		var radios = document.getElementsByName("client");

		for (var i = 0, length = radios.length; i < length; i++) {
		    if (radios[i].checked) {
		    	var client = radios[i].value;
		        break;
		    	}
		    }
		if (client == "customer"){
				url = "rest/customer/login?";
		} 
		else if (client == "company"){
				url = "rest/company/login?";			
		}
		else if (client == "admin"){
				url = "rest/admin/login?";
		}
		
		url += "username=" +username + "&password=" +password;
		xmlhttp.onreadystatechange = updateMessage;
		xmlhttp.open("GET", url, true);
		xmlhttp.send(null);
	}
	

	function updateMessage() {
		if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 400)
				window.location = "./Error.html";
			
			else if (xmlhttp.responseText == "SuccessAdmin") {
				window.location = "./admin.html";
				
			}else if (xmlhttp.responseText == "SuccessCompany") {
				window.location = "./company.html";
				
			}else if (xmlhttp.responseText == "SuccessCustomer") {
				window.location = "./customer.html";
			
			} else if (xmlhttp.responseText != null && xmlhttp.responseText != "") {
				window.alert(xmlhttp.responseText);
			}
		}
	}

//	function addCompany() {
//		var url = "rest/admin/createCompany";
//		var companyName = document.getElementById("companyName").value;
//		var password = document.getElementById("companyPassword").value;
//
//		var c = new Object;
//		c.companyName = companyName;
//		c.password = companyPassword;
//		var companyString = JSON.stringify(c);
//
//		xmlhttp.onreadystatechange = updateMessage;
//		xmlhttp.open("POST", url, true);
//
//		xmlhttp.setRequestHeader("Content-Type", "Application/Json");
//
//		xmlhttp.send(companyString);
//	}
