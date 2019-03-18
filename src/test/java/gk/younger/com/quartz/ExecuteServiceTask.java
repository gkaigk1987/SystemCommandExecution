package gk.younger.com.quartz;

public class ExecuteServiceTask {
	
	/*public void execute(List<Integer> list) {
		ExecutorService executorService = Executors.newFixedThreadPool(5);
		CronTriggerExample example = new CronTriggerExample();
		for(Integer i : list) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(1 * 1000);
					} catch (InterruptedException e) {
						// ignore
					}
//					System.out.println(i);
					try {
						example.run("测试执行"+i);
					} catch (SchedulerException e) {
						e.printStackTrace();
					}
				}
			});
		}
		executorService.shutdown();
		System.out.println("executorService.shutdown()");
	}
	
	public void execute2(List<Integer> list) {
		CronTriggerExample example = new CronTriggerExample();
		for (Integer i : list) {
			try {
				example.run("测试执行" + i);
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
		}
	}*/

}
