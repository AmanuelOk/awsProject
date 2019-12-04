lmsApp.controller("bookLoansCtrl", function($scope, $http, $window, $location,lmsConstants,httpFactory){
 
 httpFactory.readAllObjects(lmsConstants.SHOW_ALL_BOOK_LOANS).then(function(data){
		$scope.borrowers = data;
		$scope.borrowersSize = data.length;
         })
        $scope.extend = function(cardNo){
         alert(cardNo);
        $http.post(lmsConstants.EXTEND_DUE_DATE+cardNo).then(function(data){})
        
        $window.location="#/borrower";
        
        } 
         
         
         
         
         });