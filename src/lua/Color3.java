package lua;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import utility.Color3f;

public class Color3 extends TwoArgFunction {
	@Override
    public LuaValue call(LuaValue modname, LuaValue globalEnv) {
		LuaValue table = LuaValue.tableOf();
		table.set("new", color);
		return table;
	}

	VarArgFunction color = new VarArgFunction() {
		@Override
		public LuaValue call(LuaValue x, LuaValue y, LuaValue z) {
			return CoerceJavaToLua.coerce(new Color3f(x.tofloat() / 51, y.tofloat() / 51, z.tofloat() / 51));
		}
	};
}
