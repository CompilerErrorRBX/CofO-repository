package scripting;

import game.Main;
import objects.BasePart;

import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.TwoArgFunction;
import org.luaj.vm2.lib.VarArgFunction;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class JavaGlobals extends TwoArgFunction {

	@Override
	public LuaValue call(LuaValue modname, LuaValue globalEnv) {
		LuaValue table = LuaValue.tableOf();
		table.set("new", newObject);
		return table;
	}

	OneArgFunction newObject = new OneArgFunction() {
		@Override
		public LuaValue call(LuaValue obj) {
			// System.out.println(obj);
			BasePart part = new BasePart(Main.game);
			//LuaValue table = LuaValue.tableOf();
//			for (int i = 0; i < BasePart.class.getFields().length; i++) {
//				if (BasePart.class.getFields()[i].getModifiers() != 1) continue;
//				table.set(BasePart.class.getFields()[i].getName(), CoerceJavaToLua.coerce(BasePart.class.getFields()[i]));
//			}
//			for (int i = 0; i < BasePart.class.getMethods().length; i++) {
//				if (BasePart.class.getMethods()[i].getModifiers() != 1) continue;
//				VarArgFunction method = new VarArgFunction() {
//			        @Override
//			        public LuaValue call(LuaValue x) {
//			            return NIL;
//			        }
//			    };
//				table.set(BasePart.class.getMethods()[i].getName(), method);
//			}
			return NIL;
		}
	};

}