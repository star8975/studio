package com.samsung.sds.brightics.lightweight.job;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.samsung.sds.brightics.lightweight.job.JobRunnerContext;
import com.samsung.sds.brightics.lightweight.job.JobRunnerWrapper;

public class JobRunnerContextTest {

	private static final Logger logger = LoggerFactory.getLogger(JobRunnerContextTest.class);

	@Test
	public void executeJobRunnerWrapper() {
		String modelPath = "./jsonflow";
		long startTime = System.currentTimeMillis();
		JobRunnerWrapper jobRunner = null;
		try {
			logger.info("Start job runner test.");
			jobRunner = new JobRunnerContext().createJobRunner(modelPath + "/eventloadtest.json",
					"[{\"mid\":\"myq944vftm7cys4p\",\"fid\":\"fn6h23gnp8ed9ntc\",\"tid\":\"tmncknxtypj2h6bh\",\"data\":{\"col_names\":[\"test\",\"test2\"],\"type_array\":[\"int\",\"string\"],\"data_array\":[[\"1\",\"test\"]]}},{\"mid\":\"mid01\",\"fid\":\"fid02\",\"tid\":\"tid02\",\"data\":{\"col_names\":[\"test\",\"test2\"],\"type_array\":[\"int\",\"string\"],\"data_array\":[[\"1\",\"test\"]]}}]");
			jobRunner.run();

			logger.info("Finish job runner.");
			logger.info(jobRunner.getStatus());
		} catch (Exception e) {
			logger.error("Cannot execute job", e);
		} finally {
			long elapsed = System.currentTimeMillis() - startTime;
			DateFormat df = new SimpleDateFormat("HH 'hours', mm 'mins,' ss 'seconds'");
			df.setTimeZone(TimeZone.getTimeZone("GMT+0"));
			logger.info("Finish job runner. elapsed time : " + df.format(new Date(elapsed)));
		}
	}

	@Test
	public void executeJobRunner() {
		
		// create job runner context.
		JobRunnerContext jobRunnerContext = new JobRunnerContext();
		try {
			// Create job runner as model01
			JobRunnerWrapper jobRunner01 = jobRunnerContext.createJobRunner("./jsonflow/model01.json");
			// Set user (default brightics@samsung.com)
			jobRunner01.setUser("user01");
			// Set timeout second (default Integer.MAX)
			jobRunner01.setTimeout(10);
			// Run model
			jobRunner01.run();
			// Get result status
			logger.info(String.format("Resut status : %s", jobRunner01.getStatus()));

			// Create job runner as model02
			JobRunnerWrapper jobRunner02 = jobRunnerContext.createJobRunner("./jsonflow/model02.json");
			// Run model
			jobRunner02.setUser("user02").run();
			
		} catch (Exception e) {
			logger.error("Cannot execute job", e);
		}
	}

}
