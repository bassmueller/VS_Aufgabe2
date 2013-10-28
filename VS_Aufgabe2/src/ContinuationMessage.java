import java.io.Serializable;
import akka.actor.ActorRef;

public class ContinuationMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	private int stateId;
	private CalculateMessage calculateMessage;
	private ActorRef server;
	
	public int getStateId() {
		return stateId;
	}
	public CalculateMessage getCalculateMessage() {
		return calculateMessage;
	}
	
	public ContinuationMessage(ActorRef server, int stateId, CalculateMessage calculateMessage) {
		this.server = server;
		this.stateId = stateId;
		this.calculateMessage = calculateMessage;
	}
	public ActorRef getServer() {
		return server;
	}
}