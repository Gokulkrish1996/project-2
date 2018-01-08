/**
 * BlogPostDetailsController
 */



app.controller('BlogPostDetailsController',function($scope,$location,$routeParams,BlogService)
		{
	var id=$routeParams.id
	$scope.isRejected=false;
	$scope.showComment=false;
	BlogService.getBlogPost(id).then(function(response){
		$scope.blogPost=response.data
	},function(response){
		if(response.status==401){
			$location.path('/login')
		}
	
		})
		$scope.showRejectionTxt=function(val)
		{
		$scope.isRejected=val
		}
		
		$scope.updateBlogPost=function(){
		BlogService.updateBlogPost($scope.blogPost,$scope.rejectionReason).then(function(response){
		$location.path('/getblogs')	
		},function(response){
			if(response.status==401){
				$location.path('/login')
			}
		if(response.status==500){
			alert(response.data)
			$scope.error=response.data
		}
		})
		
	}
		$scope.updateLikes=function(){
			BlogService.updateLikes($scope.blogPost).then(function(response){
				//blogdetails.html
				//updated likes,glyphicon
				$scope.blogPost=response.data;
				$scope.liked=!$scope.liked;
			},function(response){
				if(response.status==401){
					$location.path('/login')
				}
			})
		}
		$scope.addComment=function(){
			if($scope.commentText==undefined){
				alert('enter the comment')
			}
			else
			BlogService.addComment($scope.commentText,id).then(function(response){
			alert('added sucessfully')	
			$scope.commentText=''
			$scope.blogPost=response.data //list of blog comments for the blogpost
			},function(response){
				if(response.status==401)
					{
					$location.path('/login')
					}
				if(response.status==500){
					$scope.error=response.data
				}
			})
			}
			
	
	$scope.showComments=function(){
	alert('show comments')
	$scope.showComment=!$scope.showComment
}
		})



