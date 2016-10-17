angular.module("sistema").controller("quoteController", function($scope, $http, quoteAPI) {
    $scope.quote = { "id": 0 };
    $scope.item = { "quoteId": 0, "id": 0 };
    $scope.quote.total = 0.0
    $scope.quote.items = [];

    $scope.itemMaster = { "quoteId": 0, "id": 0 };

    var reset = function() {
        $scope.item = angular.copy($scope.itemMaster);
    };


    var _totalPrice = function(quantity, price) {
        return quantity * price;
    }

    var _totalQuote = function(items) {
        var soma = 0;
        for (var i in items)
            soma += items[i].total;
        return soma;
    }

    var loadQuotes = function() {
        quoteAPI.getQuotes().success(function(data) {
            $scope.quotes = data;
        }).error(function(data, status) {
            $scope.message = "Someting went wrong :(" + data
        });
    }

    $scope.addItem = function(item) {
        item.total = _totalPrice(item.quantity, item.price);
        $scope.quote.items.push(angular.copy(item));
        $scope.quote.total = _totalQuote($scope.quote.items);
        reset();
    }

    $scope.saveQuote = function(quote) {
        $scope.quote.date = new Date().getTime();

        quoteAPI.saveQuote(quote).success(function(data) {
            loadQuotes();
        });
    }

    $scope.deleteQuote = function(id) {
        quoteAPI.deleteQuote(id).success(function(data) {
            loadQuotes();
        });
    }

    loadQuotes();
});
