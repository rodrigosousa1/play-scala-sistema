angular.module("sistema").controller("quoteController", function($scope, $http, quoteAPI) {
    $scope.quotes = [];
    $scope.quote = {};
    $scope.quote.total = 0.00
    $scope.quote.items = [];

    var _totalPrice = function(quantity, price) {
        return parseInt(quantity) * parseFloat(price);
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
    }

    loadQuotes();
});
