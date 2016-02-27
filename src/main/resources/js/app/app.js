var App = angular.module('Foorumi', ['ngRoute']);

App.config(function($routeProvider){
  $routeProvider
    .when('/', {
      controller: 'HomeController',
      templateUrl: 'templates/index.html'
    })
    .when('/ketjut', {
      controller: 'KetjutController',
      templateUrl: 'templates/ketjut.html'
    })
    .when('/viestit', {
      controller: 'ViestitController',
      templateUrl: 'templates/viestit.html'
    })
    .otherwise({
      redirectTo: '/'
    });
});


App.controller('HomeController', function($scope){
});

App.controller('KetjutController', function($scope){
});

App.controller('ViestitController', function($scope){
});