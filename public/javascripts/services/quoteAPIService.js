angular.module("sistema").factory("quoteAPI", function ($http, config){

	var _getQuotes = function() {
		return $http.get(config.baseUrl + "/quotes");
	};

	var _saveQuote = function(quote) {
		return $http.post(config.baseUrl + "/quote", quote);
	}

	var _deleteQuote = function(id) {
		return $http.delete(config.baseUrl + "/quote/" + id);
	}

	var _getQuoteById = function(id) {
		return $http.get(config.baseUrl + "/quote/" + id)
	}

	var _updateQuote = function(quote) {
		return $http.put(config.baseUrl + "/quote/" + quote.id, quote)
	}

	var _downloadPdf = function(id) {
		return $http.get(config.baseUrl + "/generatePdf/" + id, { responseType: 'arraybuffer' })
	}


	return {
		getQuotes: _getQuotes,
		saveQuote: _saveQuote,
		deleteQuote: _deleteQuote,
		getQuoteById: _getQuoteById,
		updateQuote: _updateQuote,
		downloadPdf: _downloadPdf
	};
});