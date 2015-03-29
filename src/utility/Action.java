package utility;

import java.util.ArrayList;

public interface Action {
	public ArrayList<Object> returns = new ArrayList<>();
	public void run(Object... params);
}
