package gk.younger.com.quartz;

import java.util.ArrayList;
import java.util.List;

import org.quartz.SchedulerException;

public class Start {

	@SuppressWarnings("serial")
	public static void main(String[] args) throws SchedulerException {
//		ExecuteServiceTask task = new ExecuteServiceTask();
		List<Integer> list = new ArrayList<Integer>() {
			{
				add(1);add(2);add(3);add(4);add(5);
				add(6);add(7);add(8);add(9);add(10);
			}
		};
//		task.execute(list);
//		task.execute2(list);
		SimpleTriggerExample simpleTriggerExample = new SimpleTriggerExample();
		simpleTriggerExample.run(list);
	}

}
