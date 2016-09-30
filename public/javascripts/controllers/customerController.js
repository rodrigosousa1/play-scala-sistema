angular.module("sistema").controller("customerController", function($scope, $http, customerAPI){
	$scope.customers = [];
	$scope.customer = {};
	$scope.customer.phones = [];

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
		delete $scope.customer;
	    $scope.customerForm.$setPristine();
	}

	loadCustomers();
});