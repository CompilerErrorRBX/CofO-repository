package lua;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

import utility.Vector3f;

public class Vector3 extends TwoArgFunction {
	@Override
    public LuaValue call(LuaValue modname, LuaValue globalEnv) {
		LuaValue table = LuaValue.tableOf();
		table.set("new", vector);
		return table;
	}

	VarArgFunction vector = new VarArgFunction() {
		@Override
		public LuaValue call(LuaValue x, LuaValue y, LuaValue z) {
			return CoerceJavaToLua.coerce(new Vector3f(x.tofloat(), y.tofloat(), z.tofloat()));
		}
	};
}
