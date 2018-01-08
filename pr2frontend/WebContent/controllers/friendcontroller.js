/**
 * FriendController
 */
app.controller('FriendController',function($scope,$location,FriendService){
	function getAllSuggestedUsers(){
		FriendService.getAllSuggestedUsers().then(function(response){
			$scope.suggestedusers=response.data
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
	}
	function getAllPendingRequests(){
		FriendService.getAllPendingRequests().then(function(response){
			$scope.pendingrequests=response.data
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
	}
	function getListOfFriends(){
		FriendService.getListOfFriends().then(function(response){
			$scope.friends=response.data
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
	}
	$scope.addFriendRequest=function(toId){
		FriendService.addFriendRequest(toId).then(function(response){
			alert('friend request has been sent succesfully')
			getAllSuggestedUsers()
		},function(response){
			if(response.status==401)
				$location.path('/login')
		
		})
	}
	$scope.updatePendingRequest=function(pendingrequest,updatedstatus){
		pendingrequest.status=updatedstatus
		FriendService.updatePendingRequest(pendingrequest).then(function(response){
			getAllPendingRequests()
		},function(response){
			if(response.status==401)
				$location.path('/login')
		})
	}
	getAllSuggestedUsers()
	getAllPendingRequests()
	getListOfFriends()
})