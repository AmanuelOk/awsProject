lmsApp.controller("showBookCtrl", function($scope, $http, $window, $location,lmsConstants,httpFactory){
       httpFactory.readAllObjects(lmsConstants.READ_ALL_BOOKS).then(function(data){
		$scope.books = data;
		$scope.bookSize = data.length;
	}) 
	
	$scope.saveBook = function(){
	var book = {
				title : $scope.title,
				pubId : $scope.pubId,
				authorId : $scope.authorId,
				branchId :$scope.branchId,
				copies :$scope.copies,
				genreId :$scope.genreId
	             }
	             
	 httpFactory.saveObject(lmsContants.SAVE_BOOK,book).then(function(data){$scope.response = data;
		alert(response);
		})
		  alert("touched");
		$window.location = "#/books";}           
	             
	})