package thisisxanderh.turrets.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import thisisxanderh.turrets.core.GameStage;
import thisisxanderh.turrets.entities.players.Player;
import thisisxanderh.turrets.graphics.Toast;

public class UIManager extends Stage {
	private Player player;
	private Skin skin;
	private BuildUI build;
	private SiegeUI siege;
	private PauseUI pause;
	private ToastUI toast;
	private ConsoleUI console;
	private PlayUI play;
	
	private boolean consoleEnabled = false;
	
	private UserInterface active;
	private boolean paused = false;
	public UIManager(Player player) {
		super();
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        build = new BuildUI(player, skin);
        siege = new SiegeUI(player, skin);
        toast = new ToastUI(skin);
        this.addActor(toast);
        pause = new PauseUI(player, skin);
        
        console = new ConsoleUI(player, skin);
        
        play = new PlayUI(player, skin);
        this.addActor(play);
        
        active = build;
        this.addActor(active); 
		this.player = player;
		
		
		
        Gdx.input.setInputProcessor(this);
	}
	
	public void killToast(boolean immediate) {
		toast.eject(immediate);
	}
	
	public void togglePause() {
		if (paused) {
			unPause();
		} else {
			pause();
		}
	}
	
	public void pause() {
		paused = true;
		active.remove();
		this.addActor(pause);
	}
	
	public void unPause() {
		paused = false;
		pause.remove();
		this.addActor(active);
	}
	
	public void toggleConsole() {
		consoleEnabled = !consoleEnabled;
		GameStage stage = (GameStage) player.getStage();
		stage.getController().togglePause();
		
		if (consoleEnabled) {
			this.addActor(console);
			console.focus();
		} else {
			console.remove();
		}
	}
	
	private void setMode(UserInterface mode) {
		active.remove();
		active = mode;
		this.addActor(active);
	}
	
	public void setBuild() {
		setMode(build);
	}
	
	public void setSiege() {
		setMode(siege);
	}
	
	public void toggleMode() {
		if (active instanceof SiegeUI) {
			setMode(build);
		} else {
			setMode(siege);
		}
	}
	
	public Skin getSkin() {
		return skin;
	}
	
	public void toast(Toast bread) {
		toast.toast(bread);
	}
	
	@Override
	public void act(float delta) {
		active.act(delta);
		
		if (paused) {
			pause.act(delta);
		} else {
			play.act(delta);
			toast.act(delta);
			if (consoleEnabled) {
				console.act(delta);
			}
		}
	}
}
