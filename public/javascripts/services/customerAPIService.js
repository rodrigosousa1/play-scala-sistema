angular.module("sistema").factory("customerAPI", function ($http, config){

	var _getCustomers = function() {
		return $http.get(config.baseUrl + "/customers");
	};

	var _saveCustomer = function(customer) {
		return $http.post(config.baseUrl + "/customer", customer);
	}

	var _deleteCustomer = function(id) {
		return $http.delete(config.baseUrl + "/customer/" + id);
	}

	var _getCustomerById = function(id) {
		return $http.get(config.baseUrl + "/customer/" + id)
	}

	var _updateCustomer = function(customer) {
		return $http.put(config.baseUrl + "/customer/" + customer.id, customer)
	}


	return {
		getCustomers: _getCustomers,
		saveCustomer: _saveCustomer,
		deleteCustomer: _deleteCustomer,
		getCustomerById: _getCustomerById,
		updateCustomer: _updateCustomer
	};
});