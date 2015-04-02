package lua;

import game.Main;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class Core extends TwoArgFunction {

	@Override
	public LuaValue call(LuaValue modname, LuaValue globalEnv) {
		LuaValue table = LuaValue.tableOf();
		table.set("Workspace", CoerceJavaToLua.coerce(Main.game));
		return table;
	}

}
