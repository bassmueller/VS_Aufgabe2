
import java.util.ArrayList;
import java.util.List;
import akka.actor.*;

public class Master extends UntypedActor {
	@Override
	public void onReceive(Object message) throws Exception {
		if (message instanceof CalculateMessage) {
			CalculateMessage calculate = (CalculateMessage) message;
			// send CalculateMessage to each worker
			for (String path : workerNodes) {
				ActorSelection worker = system.actorSelection(path);
				worker.tell(calculate, self());
			}
		} else if (message instanceof ResultMessage) {
			// print result and shutdown the actor system
			self().tell(PoisonPill.getInstance(), self());
			system.shutdown();
		} else {
			throw new IllegalArgumentException("Unknown message [" + message + "]");
		}
	}
	
	private List<String> workerNodes = new ArrayList<String>() {{
		add("akka.tcp://Lab2Node@127.0.0.1:2553/user/worker");
		}};
		
	private ActorSystem system;
	
	public Master(ActorSystem system) {
		this.system = system;
	}
}