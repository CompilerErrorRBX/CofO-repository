package objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.Scanner;

import org.luaj.vm2.Globals;
import org.luaj.vm2.lib.jse.JsePlatform;

public class Script extends Container {
	private String source = "local Instance = require('lua.Instance') "
			+ "local Vector3 = require('lua.Vector3') "
			+ "local Color3 = require('lua.Color3') "
			+ "local game = require('lua.Core') "
			+ "local Game = game "
			+ "function wait(time) "
			+ "		t = os.clock() "
			+ "		for i = 1, math.huge do "
			+ "			timeElapsed = os.clock()-t "
			+ "			if (timeElapsed >= time) then "
			+ "				return timeElapsed "
			+ "			end "
			+ "		end "
			+ "end "
			+ "function time() "
			+ "		return os.clock() "
			+ "end ";
	public Script() {
		init();
	}
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
		name = "Script";
		new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				execute();
				stop();
			}
		}.start();
	}
	protected void execute() {
		Globals globals = JsePlatform.standardGlobals();
		globals.load(new StringReader(this.source), "main.lua" ).call();
	}
}
