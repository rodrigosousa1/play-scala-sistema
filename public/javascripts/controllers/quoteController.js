angular.module("sistema").controller("quoteController", function($scope, $http, quoteAPI) {
    $scope.quotes = [];

    var loadQuotes = function() {
        quoteAPI.getQuotes().success(function(data) {
            $scope.quotes = data;
        }).error(function(data, status) {
            $scope.message = "Someting went wrong :(" + data
        });
    }

    loadQuotes();
});
