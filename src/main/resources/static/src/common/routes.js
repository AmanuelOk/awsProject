lmsApp.config(["$routeProvider", function($routeProvider){
	return $routeProvider.when("/",{
		redirectTo: "Home"
	}).when("/home",{
		templateUrl: "index.html"
	}).when("/administrator", {
		templateUrl: "AdminPage.html"
	}).when("/librarian", {
		templateUrl: "LibrarianPage.html"
	}).when("/copies", {
		templateUrl: "BorrowerPage.html"
	}).when("/authors", {
		templateUrl: "authorList.html"
	}).when("/borrowers", {
		templateUrl: "borrowerList.html"
	}).when("/books", {
		templateUrl: "books.html"
	}).when("/branches", {
		templateUrl: "showAllBranches.html"
	})
}])
