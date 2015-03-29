package objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.Scanner;

import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.OneArgFunction;
import org.luaj.vm2.lib.jse.JsePlatform;

public class Script extends Container {
	String source = "Instance = require('scripting.JavaGlobals')";
	public Script(String src) {
		source = src;
		init();
	}
	public Script(File src) {
		Scanner fileReader;
		try {
			fileReader = new Scanner(src);
			while (fileReader.hasNextLine()) {
				source += fileReader.nextLine() + "\n";
			}
			init();
			fileReader.close();
		} catch (FileNotFoundException e) {
			System.err.println("Could not load or find file: " + src.toString() + ".");
		}
	}
	private void init() {
		this.execute();
	}
	protected void execute() {
		Globals globals = JsePlatform.standardGlobals();
		globals.load(new StringReader(this.source), "main.lua" ).call();
	}
}
