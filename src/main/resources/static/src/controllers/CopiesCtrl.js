lmsApp.controller("CopiesCtrl", function($scope, $http, $window, $location,lmsConstants,httpFactory){
 httpFactory.readAllObjects(lmsConstants.SHOW_BOOK_LIST).then(function(data){
		$scope.copies = data;
		$scope.copiesSize = data.length;   
	}) 

	$scope.checkOut = function(){ 
	var borrow ={
	cardNo : $scope.cardNo,
	branchId : $scope.branchId,
	bookId:$scope.bookId
				};
				
		httpFactory.saveObject(lmsConstants.BORROW_BOOK,borrow).then(function(data){})
		$window.location = "#/copies";
								};
	    
	$scope.checkIn = function(){};	
				
				
					
	})