/**
 * Angular JS Module
 */

var app=angular.module("app",['ngRoute','ngCookies'])
app.config(function($routeProvider){
	$routeProvider
	.when('/register',{
		templateUrl:'views/userform.html',
		controller:'UserController'
	})
	.when('/login',{
		templateUrl:'views/login.html',
		controller:'UserController'
			})
			.when('/editprofile',{
		templateUrl:'views/updateuser.html',
		controller:'UserController'
			})
			.when('/addjob',{ //Data is from jobform.html to controller
		templateUrl:'views/jobform.html',
		controller:'JobController'
			})
			.when('/alljobs',{ //from controller to view {$scope.jobs[]]
		templateUrl:'views/joblist.html',
		controller:'JobController'
			})
			.when('/addblog',{ //V to Contoller
		templateUrl:'views/blogform.html',
		controller:'BlogPostController'
			})
			.when('/getblogs',{ //V to Contoller
				templateUrl:'views/bloglist.html',
				controller:'BlogPostController'
					})
					.when('/admin/getblog/:id',{ 
				templateUrl:'views/approvalform.html',
				controller:'BlogPostDetailsController'
					})
					.when('/getblog/:id',{ 
				templateUrl:'views/blogdetails.html',
				controller:'BlogPostDetailsController'
					})

					.when('/home',{ 
				templateUrl:'views/home.html',
				controller:'HomeController'
					})
					.when('/uploadpic',{
		        templateUrl:'views/ProfilePicture.html'
	                })
                     .when('/suggestedusers',{ 
				templateUrl:'views/suggestedusers.html',
				controller:'FriendController'
					})
					.when('/pendingrequests',{ 
				templateUrl:'views/pendingrequests.html',
				controller:'FriendController'
					})
					.when('/friends',{ 
				templateUrl:'views/friendslist.html',
				controller:'FriendController'
					})
	.otherwise({templateUrl:'views/home.html',controller:'HomeController'})
})
app.run(function($rootScope,$cookieStore,UserService,$location){
	alert($cookieStore.get('currentUser'))
	if($rootScope.currentUser==undefined)
		$rootScope.currentUser=$cookieStore.get('currentUser')
	
	
	$rootScope.logout=function(){
/*
 * call middleware logout url -> middleware- remove Httpsession attribute,update online status to false
 * on success in frontend,remove cookieStore attribute currentUser , delete $rootscope.
 */
UserService.logout().then(function(response){
	delete $rootScope.currentUser;
	$cookieStore.remove('currentUser')
	$location.path('/login')
	},function(response){
		console.log(response.status)
		$location.path('/login')
		
	})
	}
	})
	


