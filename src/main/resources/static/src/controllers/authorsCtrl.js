lmsApp.controller("authorsCtrl", function($scope, $http, $window, $location, lmsConstants, httpFactory){
alert('controller');
   httpFactory.readAllObjects(lmsConstants.READ_ALL_AUTHORS).then(function(data){
		$scope.authors = data;
		$scope.authorsSize = data.length;   
	}) 
$scope.createAuthor = function(){
		var author = {
				authorName: $scope.AuthorFName +" "+ $scope.AuthorLName
		}
		
		httpFactory.saveObject(lmsConstants.SAVE_AUTHOR,author).then(function(data){alert("Submitted"); })
		
		$window.location = "#/authors";
		};
		
      
})