
import java.math.BigInteger;
import akka.actor.UntypedActor;
public class Worker extends UntypedActor {
	private int stateId = 0;
	
	public void onReceive(Object message) {
		if (message instanceof CalculateMessage) {
			CalculateMessage calculateMessage = (CalculateMessage) message;
			// send a continuation message to self
			self().tell(new ContinuationMessage(sender(), ++stateId, calculateMessage), self());
		} else if (message instanceof ContinuationMessage) {
			ContinuationMessage continuationMessage = (ContinuationMessage) message;
			// discard old message
		if (continuationMessage.getStateId() != stateId) return;
			// calculate and send result
			BigInteger a = continuationMessage.getCalculateMessage().getA();
			BigInteger b = continuationMessage.getCalculateMessage().getB();
			BigInteger result = a.add(b);
			continuationMessage.getServer().tell(new ResultMessage(result), self());
		} else {
			throw new IllegalArgumentException("Unknown message [" + message + "]");
		}
	}

}