package thisisxanderh.turrets.ui;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;

import thisisxanderh.turrets.entities.players.Player;

public class ConsoleUI extends UserInterface {
	private TextField console;
	private ArrayList<Label> logs;
	
	public ConsoleUI(Player player, Skin skin) {
		super();
		console = new TextField("", skin);
		console.setSize(Gdx.graphics.getWidth(), 20);
		console.setPosition(0, Gdx.graphics.getHeight() - console.getHeight() - 100f);
		
		this.addActor(console);
		
		logs = new ArrayList<>();
		
		for (int i = 0; i < 5; i++) {
			Label log = new Label("", skin);
			log.setSize(Gdx.graphics.getWidth(), 20f);
			log.setPosition(0, Gdx.graphics.getHeight() - 20f * (i + 1));
			logs.add(log);
			this.addActor(log);
		}
	}
	
	protected void focus() {
		this.getStage().setKeyboardFocus(console);
		console.setText("");
	}
	
	@Override
	public void act(float delta) {
		if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			for (int i = 0; i < 4; i++) {
				Label current = logs.get(i);
				Label next = logs.get(i + 1);
				
				current.setText(next.getText());
			}
			logs.get(4).setText(console.getText());
			console.setText("");
		}
	}
}
