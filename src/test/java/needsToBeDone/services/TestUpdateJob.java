package needsToBeDone.services;

import com.fssa.needstobedone.exception.ServiceException;
import com.fssa.needstobedone.model.Job;
import com.fssa.needstobedone.services.JobService;

public class TestUpdateJob {
	public static void main(String[] args) {
		try {
			JobService jobService = new JobService();
			Job job1 = new Job(10000, "128718a6-b34c-4557-abb6-61f5175d8a85", "Updated");
			jobService.listJobs("128718a6-b34c-4557-abb6-61f5175d8a85");
			jobService.UpdateJobs(job1);
			jobService.listJobs("128718a6-b34c-4557-abb6-61f5175d8a85");
		} catch (ServiceException e) {
			e.printStackTrace();
		}
	}
}