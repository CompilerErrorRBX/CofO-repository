package utility;

import java.util.ArrayList;

public class Event {
	public ArrayList<Object> returns = new ArrayList<>();
	private ArrayList<Action> actions = new ArrayList<>();
	public void connect(Action action) {
		actions.add(action);
	}
	public void execute() {
		for (Action act : actions) {
			act.run(returns);
		}
	}
}
