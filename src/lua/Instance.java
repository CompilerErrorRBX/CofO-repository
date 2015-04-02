package lua;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class Instance extends TwoArgFunction {

	@Override
	public LuaValue call(LuaValue modname, LuaValue globalEnv) {
		LuaValue table = LuaValue.tableOf();
		table.set("new", newObject);
		return table;
	}

	VarArgFunction newObject = new VarArgFunction() {
		@Override
		public LuaValue call(LuaValue obj) {
			try {
				LuaValue inst = CoerceJavaToLua.coerce(Class.forName("objects." + obj.tojstring()).newInstance());
				return inst;
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			};
			return error("Cannot create instance of type '" + obj + "'");
		}
	};
	
}