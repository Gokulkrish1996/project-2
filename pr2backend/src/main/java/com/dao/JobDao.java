package com.dao;

import java.util.List;

import com.model.Job;

public interface JobDao {
 void saveJob(Job job);
 List<Job> getAllJobs();
 Job getJob(int jobId);
}
