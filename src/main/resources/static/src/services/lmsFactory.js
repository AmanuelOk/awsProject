lmsApp.factory("httpFactory", function($http){
	return{
		readAllObjects: function(url){
			var listObjs = [];
			return $http.get(url).success(function(data){
				listObjs = data;
			}).then(function(){
				return listObjs;
			})
		},
		saveObject: function(url, obj){
			return $http.post(url, obj).then(function(data){
				return data;
			})
		}
	}
})