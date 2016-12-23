package thisisxanderh.turrets.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;

import com.williamhayward.turrets.Turrets;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 720;
		config.height = 480;
		config.title = "Turrets";
		TexturePacker.process("sprites", "packed", "sprites.atlas");
		new LwjglApplication(new Turrets(), config);
	}
}
