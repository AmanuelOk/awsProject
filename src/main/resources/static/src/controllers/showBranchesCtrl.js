lmsApp.controller("branchesCtrl",function(lmsConstants, httpFactory,$scope){

 httpFactory.readAllObjects(lmsConstants.LIST_BRANCHES).then(function(data){
		$scope.branches = data;
		$scope.branchesSize = data.length;})


})