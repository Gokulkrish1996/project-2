/**
 * JobService
 */
app.factory('JobService',function($http){
	var jobService={}
	jobService.addJob=function(job)
	{
		return $http.post("http://localhost:8085/pr2middleware/savejob",job);
	}
	jobService.getAllJobs=function()
	{
		return $http.get("http://localhost:8085/pr2middleware/alljobs");
	}
	jobService.getJob=function(jobId)
	{
		return $http.get("http://localhost:8085/pr2middleware/getjob/"+jobId)
	}
	
	return jobService;
})