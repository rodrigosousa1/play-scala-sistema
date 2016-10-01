angular.module("sistema").controller("customerController", function($scope, $http, customerAPI){
	$scope.customers = [];
	$scope.customer = {};
	$scope.customer.phones = [];

	$scope.customerMaster = {"phones":[{"customerId":0,"id":0}],"id":0};

	var reset = function() {
        $scope.customer = angular.copy($scope.customerMaster);
    };

	var loadCustomers = function() {
		customerAPI.getCustomers().success(function(data){
			$scope.customers = data;
		}).error(function(data, status){
			$scope.message = "Someting went wrong :(" + data
		});
	}


	$scope.saveCustomer = function(customer) {
		customerAPI.saveCustomer(customer).success(function(data){
			delete $scope.customer;
			loadCustomers();
		});
	};

	$scope.deleteCustomer = function(id) {
		customerAPI.deleteCustomer(id).success(function(data){
			loadCustomers();
		});
	}

	$scope.getCustomerById = function(id) {
		customerAPI.getCustomerById(id).success(function(data){
			$scope.customer = data;
		});
	}

	$scope.updateCustomer = function(customer) {
		customerAPI.updateCustomer(customer).success(function(data){
			delete $scope.customer;
			loadCustomers();
		});
	}



	$scope.clearForm = function() {
		reset();
	    $scope.customerForm.$setPristine();
	}

	loadCustomers();
});