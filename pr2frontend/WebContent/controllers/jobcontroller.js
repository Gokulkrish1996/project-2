/**
 *JobController 
 */
app.controller('JobController',function($scope,JobService,$location){
	$scope.showDetails=false
	$scope.addJob=function(){
		JobService.addJob($scope.job).then(
				function(response){
					alert('Job Details Posted Succesfully')
					$location.path('/home')
				},function(response){
					if(response.status==401){
						if(response.data.code == 6)
							alert('Access Denied')
							$location.path('/home')
					}
					else{
					$scope.error=response.data
					$location.path('/login')
					}
					if(response.status==500){
						$scope.error=response.data
						$location.path('/addjob')
						
					}
				})
	}
	$scope.getJob=function(jobId){
		$scope.showDetails=true
		JobService.getJob(jobId).then(function(response){
		$scope.job=response.data	
		},function(respnse){
			if(response.status==401){
				$scope.error=response.data
				$location.path('/login')
			}
		})
	}
	function getAllJobs(){
		JobService.getAllJobs().then(function(response){
			$scope.jobs=response.data
		},function(response){
			if(response.status==401){
				$scope.error=response.data
				$location.path('/login')
			}
			
		})
	}
	getAllJobs()
})