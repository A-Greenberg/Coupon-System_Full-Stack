var app = angular.module('administrator', [
      'jcs-autoValidate',
      'angular-ladda'
  ]);


app.run(function(defaultErrorMessageResolver){
		defaultErrorMessageResolver.getErrorMessages().then(function(errorMessages){
			errorMessages['badUserName']='User name can only contain numbers and letters';
			errorMessages['dontMatch']='Passwords do not match';
			errorMessages['badPassword']='Password should contain Upper and Lower case letters and Numbers';
			errorMessages['validPrice']='Enter a Valid price 0.00';
		});
	}
);

app.controller('AdminController' ,function($scope, $http, $window){
	var vm = this;
	
	this.tab = 2;
	
	this.selectTab = function(setTab){
		this.tab = setTab;	
	};
	
	this.isSelected = function(checkTab){
		return this.tab === checkTab;
	};
	
	var bootstrap_enabled = (typeof $().modal == 'function');
	
	vm.limit = 4;
	
	vm.selectLimit = function(setlimit){
		vm.limit = setlimit;	
	};
	
	$scope.search = {};
	
	$scope.order ="id";
	
	$scope.orderCoupons ="id";
	
	$scope.selectedIndex = null;
	
	$scope.selectCoupon = function (id){
		$scope.selectedIndex = id;
	};
	
	$scope.compDeleted = false;
	
	
	$scope.updateCompSubmitting = false;
	
	
	$scope.showInCompanyPage = function(company){
		$scope.myCompany = company;
		
	};
	
	$scope.showInCustomerPage = function(customer){
		$scope.myCustomer = customer;
		if ($scope.myCustomer.myCoupons.length == 0){
			$scope.showCustCoupons = false;
		}else{
			$scope.showCustCoupons = true;
		}
	};
	

		$scope.addCompfunction = function(addComp) {
		console.log("Hey Im submitted")
		var company = {
			compName : vm.addComp.compName,
			password : vm.addComp.password,
			email : 	vm.addComp.email
		}
		console.log(company)

		$http.post('rest/admin/createCompany', company).then(
				function mySuccess(response) {
					console.log(":-)");
					window.alert("Company Created");
					vm.resetAddCompForm();
				}, function myError(response) {
					console.log(":-(");
					window.alert("Company Wasn't Created");
					if (response.status == 400) {
						window.location = 'Error.html';
					} else {
						console.log(response.data);
						window.alert(response.data);
					}
				});
	};
		
		  
			vm.resetAddCompForm = function(){
		  	vm.addCompForm.$setPristine();
		    vm.addCompForm.$setUntouched();
		    vm.addComp.compName="";
		    vm.addComp.password="";
		    vm.addComp.password2="";
		    vm.addComp.email="";
		  };

		$scope.addCustFunction = function(addCust) {
			
			$scope.addCustSubmitting = true;
			console.log("Hey Im submitted");
	
			var cust = {
			custName:vm.addCust.custName,
			password:vm.addCust.password
			
			}
			
			console.log(cust);
			$http.post('rest/admin/createCustomer', cust).then(
					function mySuccess(response) {
						console.log(":-)");
						window.alert("Customer Created");
						vm.resetAddCustForm();
					}, function myError(response) {
						console.log(":-(");
						window.alert("Customer Wasn't Created");
						if (response.status == 400) {
							window.location = 'Error.html';
						} else {
							window.alert(response.data);
						}
					});
		};
	
	vm.resetAddCustForm = function(){
	  	vm.addCustForm.$setPristine();
	    vm.addCustForm.$setUntouched();
	    vm.addCust.custName="";
	    vm.addCust.password="";
	    vm.addCust.password2="";
	}
	
	$scope.getComps = function () {
		
		vm.selectLimit(4);
		
		$http.get('rest/admin/getAllCompanies').then(
				function mySucces(response){
				//console.log("Number of companies in DB is " + response.data.length);
				console.log(":-)");
				$scope.sampleComps = response.data;
				if ($scope.sampleComps.length < 5) {
					$scope.showButton = false;
				}else{
					$scope.showButton = true;
				}
			}, function myError(response){
				console.log(":-(");
				window.alert("Problem showing companies");
				window.location = 'Error.html';
			});
	};
	
	$scope.getCusts = function () {
		
		vm.selectLimit(4);
		
		$http.get('rest/admin/getAllCustomers').then(
			 	function mysucces(response){
				//console.log("Number of customers in DB is " + response.data.length);
				console.log(":-)");
				$scope.sampleCusts = response.data;
				if ($scope.sampleCusts.length < 5) {
					$scope.showButton = false;
				}else{
					$scope.showButton = true;
				}
			},function myError(response){
				console.log(":-(");
				window.alert("Problem showing customers");
				window.location = 'Error.html';
			});
	};
	
	$scope.getCompCoupons = function (compId){
		var url ="rest/admin/getCompanyCoupons?";
		url += "compId=" + compId;
		$http.get(url).then(
			function mySuccess(response){
				console.log(":-)");
				$scope.companyCoupons = response.data;
			if ($scope.companyCoupons.length == 0){
				$scope.showCompCoupons = false;
			}else{
				$scope.showCompCoupons = true;
			}
		}, function myError(response){
			console.log(":-(");
		});
		
	};
	
	$scope.updateComp = function (){
		$scope.updateCompSubmitting= true;
		
		var comp = new Object();
		comp.compName = $scope.myCompany.compName;
		comp.password = $scope.myCompany.password;
		comp.email = $scope.myCompany.email;

		var compJson = JSON.stringify(comp);
		
		$http.put('rest/admin/updateCompany', compJson).then(
					function mySucces(response){
					console.log(":-)");
					window.alert("Company details were updated");
					}, function myError(response){
						$scope.updateCompSubmitting= false;
						console.log(":-(");
						window.alert("Problem updating companies");
						if (response.status == 400){
							$scope.updateCompSubmitting= false;
							/*window.location = 'Error.html';*/
						}else{
							window.alert(response.data)
							$scope.updateCompSubmitting= false;
						}
					});		
	}
	
	
	$scope.updateCust = function (){
		$scope.updateCustSubmitting= true;
		
		var cust = new Object();
		cust.custName = $scope.myCustomer.custName;
		cust.password = $scope.myCustomer.password;

		var custJson = JSON.stringify(cust);
		
		$http.put('rest/admin/updateCustomer', custJson).then(
					function mySucces(response){
					console.log(":-)");
					window.alert("Customer details were updated");
					}, function myError(response){
						$scope.updateCustSubmitting= false;
						console.log(":-(");
						window.alert("Problem updating customers");
						if (response.status == 400){
							$scope.updateCustSubmitting= false;
							/*window.location = 'Error.html';*/
						}else{
							window.alert(response.data)
							$scope.updateCustSubmitting= false;
						}
					});
	}
	

		$scope.removeComp = function(comp){
		var deleteComp = $window.confirm('Are you absolutely sure you want to delete?');
		if (deleteComp) {
			$http.post('rest/admin/removeCompany', comp).then(
					function mySucces(response) {
						console.log(":-)");
						$window.alert(comp.compName + ' was deleted succesfuly!');
					}, function myError(response) {
						console.log(":-(");
						window.alert("Problem deleting company");
						if (response.status == 400) {
						} else {
							window.alert(response.data)
						}
					});
		}
	}
		

			$scope.removeCust = function(cust) {
		var deleteCust = $window
				.confirm('Are you absolutely sure you want to delete?');
		if (deleteCust) {
			$http.post('rest/admin/removeCustomer', cust).then(
					function mySucces(response) {
						console.log(":-)");
						$window.alert(cust.custName
								+ ' was deleted succesfuly!');
					}, function myError(response) {
						console.log(":-(");
						window.alert("Problem deleting customer");
						if (response.status == 400) {
						} else {
							window.alert(response.data)
						}
					});
		}
	}
				
});

app.controller('CompanyController' ,function($scope, $http, $window){
	var vm = this;
	
	this.tab = 7;
	
	this.selectTab = function(setTab){
		this.tab = setTab;	
	};
	
	this.isSelected = function(checkTab){
		return this.tab === checkTab;
	};
	
	vm.limit = 4;
	
	vm.selectLimit = function(setlimit){
		vm.limit = setlimit;	
	};
	
	$scope.getCoupons = function () {
		vm.selectLimit(4);
		$http.get('rest/company/getAllCoupons').then(
				function mySucces(response){
				console.log(":-)");
				$scope.sampleCoups = response.data;
				if ($scope.sampleCoups.length < 5) {
					$scope.showButton = false;
				}else{
					$scope.showButton = true;      /****************************/
				}
			}, function myError(response){
				console.log(":-(");
				window.alert("Problem showing coupons");
			});
	};
	
	$scope.showInCouponPage = function(coupon){
		$scope.myCoupon = coupon;
		$scope.updateCoupon = {endDate:"", price: Math.round(coupon.price*100)/100};
	};
	
	$scope.updateCouponfunction = function(coupon) {
		
		console.log(coupon);
		
		
		$http.put('rest/company/updateCoupon', coupon).then(
				function mySucces(response) {
					console.log(":-)");
					window.alert("Coupon details were updated");
				}, function myError(response) {
					console.log(":-(");
					window.alert("Problem updating coupon");
					if (response.status == 400) {
						/* window.location = 'Error.html'; */
					} else {
						window.alert(response.data)
					}
				});
	};
	
	
	$scope.updateCouponEndDate = function(updatedendDate){
		var updatedCoupon = $scope.myCoupon;
		updatedCoupon.endDate = updatedendDate;
		$scope.updateCouponfunction(updatedCoupon);
	}
	
	$scope.updateCouponPrice = function (updatedPrice){
		var updatedCoupon = $scope.myCoupon;
		updatedCoupon.price = Math.round(updatedPrice*100)/100;
		$scope.updateCouponfunction(updatedCoupon);
	}
	
	$scope.addCoupfunction = function(addCoup) {
		
		console.log("Inside create coupon")
		
		var newCoupon = {
			title : vm.addCoup.title,
			amount : vm.addCoup.amount,
			type: vm.addCoup.type,
			price :	vm.addCoup.price,
			endDate : vm.addCoup.endDate,
			message : vm.addCoup.message,	
			image : vm.addCoup.image
		}
		
		console.log(newCoupon)
		
	/*	var url ="rest/company/createCoupon?";
		url += "title=" + newCoupon.title + "&amount=" 
		+newCoupon.amount + "&type=" + newCoupon.type 
		+"&price=" + newCoupon.price + "&endDate=" 
		+newCoupon.endDate + "&message="+ newCoupon.message 
		+"&image=" + newCoupon.image; */
		
		$http.post('rest/company/createCoupon', newCoupon).then(
				function mySuccess(response) {
					console.log(":-)");
					window.alert("Coupon Created");
					vm.resetAddCoupForm();
				}, function myError(response) {
					console.log(":-(");
					window.alert("Coupon Wasn't Created");
					if (response.status == 400) {
						window.alert(400);
					} else {
						window.alert(response.data);
					}
				});
	};
	
	vm.resetAddCoupForm = function(){
  	vm.addCoupForm.$setPristine();
    vm.addCoupForm.$setUntouched();
    vm.addCoup.title="";
    vm.addCoup.amount="";
    vm.addCoup.price="";
    vm.addCoup.endDate="";
    vm.addCoup.message="";
    vm.addCoup.image="";
  };
	
  $scope.checkClick =function(){
	  console.log("!!!");
  }
  
  $scope.removeCoupon = function(myCoupon){
		var deleteComp = $window.confirm('Are you absolutely sure you want to delete?');
		if (deleteComp) {
			$http.post('rest/company/removeCoupon', myCoupon).then(
					function mySucces(response) {
						console.log(":-)");
						$window.alert(myCoupon.title + ' was deleted succesfuly!');
					}, function myError(response) {
						console.log(":-(");
						window.alert("Problem deleting company");
						if (response.status == 400) {
						} else {
							window.alert(response.data)
						}
					});
		}
	}
  
  $scope.getCouponsByType = function (type){
	  vm.selectLimit(4);
	  console.log(type);
	  
	  	var url = "rest/company/getAllCouponsByType?";
	  		url += "type=" + type;
	  	

		$http.get(url).then(
				function mySucces(response){
				console.log(":-)");
				$scope.sampleCoups = response.data;
				if ($scope.sampleCoups.length < 5) {
					$scope.showButton = false;
				}else{
					$scope.showButton = true;      
				}
			}, function myError(response){
				console.log(":-(");
				window.alert("Problem showing coupons");
			});
  }
  
  $scope.getCouponsByPrice = function (price){
	  vm.selectLimit(4);
	  console.log(price);
	  
	  	var url = "rest/company/getAllCouponsByPrice?";
	  		url += "price=" + price;
	  	

		$http.get(url).then(
				function mySucces(response){
				console.log(":-)");
				$scope.sampleCoups = response.data;
				if ($scope.sampleCoups.length < 5) {
					$scope.showButton = false;
				}else{
					$scope.showButton = true;    
				}
			}, function myError(response){
				console.log(":-(");
				window.alert("Problem showing coupons");
			});
	
	  
  }
  
  $scope.getCouponsByEndDate = function (date){
	  vm.selectLimit(4);
	  var	 miliDate = date.getTime();
	  
	  var	url = "rest/company/getAllCouponsByDate?";
			url += "date=" + miliDate;

		$http.get(url).then(
				function mySucces(response){
				console.log(":-)");
				$scope.sampleCoups = response.data;
				if ($scope.sampleCoups.length < 5) {
					$scope.showButton = false;
				}else{
					$scope.showButton = true;   
				}
			}, function myError(response){
				console.log(":-(");
				window.alert("Problem showing coupons");
			});
  }
});

app.controller('CustomerController' ,function($scope, $http, $window){
	
	var vm = this;
	
	this.tab = 5;
	
	this.selectTab = function(setTab){
		this.tab = setTab;	
	};
	
	this.isSelected = function(checkTab){
		return this.tab === checkTab;
	};
	
	vm.limit = 4;
	
	vm.selectLimit = function(setlimit){
		vm.limit = setlimit;	
	};
	
	$scope.infoCoupon = function(coupon){
		console.log(coupon.message);
		window.alert(JSON.stringify(coupon.message));
	}
	
	
	$scope.getCustomerCoupons = function () {
		vm.selectLimit(4);
		$http.get('rest/customer/getCustomerCoupons').then(
				function mySucces(response){
				console.log(":-)");
				$scope.customerCoups = response.data;
				if ($scope.customerCoups.length < 5) {
					$scope.showButton = false;
				}else{
					$scope.showButton = true;
				}
			}, function myError(response){
				console.log(":-(");
				window.alert("Problem showing coupons");
			});
	};
	
	
	$scope.showCouponsForSell = function() {
		$scope.coupsForSale = "";
		vm.selectLimit(4);
		$http.get('rest/customer/getCouponsForSell').then(
				function mySucces(response){
				console.log(":-)");
				$scope.coupsForSale = response.data;
				if ($scope.coupsForSale.length < 5) {
					$scope.showButton = false;
				}else{
					$scope.showButton = true;
				}
				if ($scope.coupsForSale.length < 1){
					window.alert("There are no coupons for sale at this moment");
				}
				
			}, function myError(response){
				console.log(":-(");
				window.alert("Problem showing coupons");
			});
	};
	
	
/*	$scope.getCouponsByType */
	
/*	$scope.getCouponsByPrice */
	
/*	$scope.showCoupInPage(coup) -----> $scope.buyCoupon();   */
	
	$scope.showInCouponPage = function(coupon){
		console.log(coupon);
		$scope.myCoupon= coupon;
	}
	
	$scope.getCouponsByType = function (type){
		if (type == null){
			window.alert("please choose a type");
		}else {
			vm.selectLimit(4);
	  	var url = "rest/customer/getCouponsByType?";
	  		url += "type=" + type;
		$http.get(url).then(
				function mySucces(response){
				console.log(":-)");
				$scope.coupsForSale = response.data;
				if ($scope.coupsForSale.length < 5) {
					$scope.showButton = false;
				}else{
					$scope.showButton = true;
				}
				if ($scope.coupsForSale.length < 1){
					window.alert("There are no coupons for sale by this type at the moment");
					$scope.showButton = false;
					
				}
			}, function myError(response){
				console.log(":-(");
				window.alert("Problem showing coupons");
			});
		}
	};
	
	
	$scope.getCouponsByPrice = function (price){
		if (price == null){
			price=0;
		}else {
			vm.selectLimit(4);
	  	var url = "rest/customer/getCouponsByPrice?";
	  		url += "price=" + price;
		$http.get(url).then(
				function mySucces(response){
				console.log(":-)");
				$scope.coupsForSale = response.data;
				if ($scope.coupsForSale.length < 5) {
					$scope.showButton = false;
				}else{
					$scope.showButton = true;
				}
				if ($scope.coupsForSale.length < 1){
					window.alert("There are no coupons for sale up to this price at the moment");
					$scope.showButton = false;
				}
			}, function myError(response){
				console.log(":-(");
				window.alert("Problem showing coupons");
			});
		}
	};
	
	$scope.buyCoupon = function(coupon){
		var buyCoup = $window.confirm('Are you sure you want to buy?');
		if (buyCoup) {
			$http.post('rest/customer/buyCoupon', coupon).then(
					function mySucces(response) {
						console.log(":-)");
						$window.alert(coupon.title + ' was bought!');
					}, function myError(response) {
						console.log(":-(");
						window.alert("Problem buying company");
						if (response.status == 400) {
						} else {
							window.alert(response.data)
						}
					});
		}
	}
	
			
	
});

