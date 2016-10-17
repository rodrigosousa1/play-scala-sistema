angular.module("sistema").config(function ($httpProvider){
	$httpProvider.interceptors.push("loadingInterceptor");
});