package de.hochschuletrier.gdw.ws1314.states;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.InputProcessor;
import de.hochschuletrier.gdw.commons.gdx.assets.AssetManagerX;
import de.hochschuletrier.gdw.commons.gdx.state.GameState;
import de.hochschuletrier.gdw.commons.gdx.utils.DrawUtil;
import de.hochschuletrier.gdw.commons.utils.FpsCalculator;
import de.hochschuletrier.gdw.ws1314.Main;
import de.hochschuletrier.gdw.ws1314.game.ClientGame;
import de.hochschuletrier.gdw.ws1314.game.ClientServerConnect;
import de.hochschuletrier.gdw.ws1314.game.ServerGame;
import de.hochschuletrier.gdw.ws1314.network.DisconnectCallback;
import de.hochschuletrier.gdw.ws1314.sound.LocalMusic;
import de.hochschuletrier.gdw.ws1314.sound.LocalSound;

/**
 * Menu state
 * 
 * @author Santo Pfingsten
 */
public class ClientGamePlayState extends GameState implements InputProcessor, DisconnectCallback {
	private static final Logger logger = LoggerFactory.getLogger(ClientGamePlayState.class);
	
	private ClientGame tmpGame;
	private final FpsCalculator fpsCalc = new FpsCalculator(200, 100, 16);
	private LocalMusic stateMusic;
	private LocalSound stateSound;
	
	


	public ClientGamePlayState() {
	}

	public void init(AssetManagerX assetManager) {
		super.init(assetManager);
		tmpGame = new ClientGame();
		tmpGame.init(assetManager);
		stateMusic = new LocalMusic(assetManager);
		stateSound = LocalSound.getInstance();
		stateSound.init(assetManager);

		Main.inputMultiplexer.addProcessor(this);
		
		
	}

	public void render() {
		DrawUtil.batch.setProjectionMatrix(DrawUtil.getCamera().combined);
		// game.render();
		tmpGame.render();
		
                
		//TODO: Jemand der weis woher das kommt bitte fixen, hier war nach dem Merge zwischen integration_test/network_gameplay und master ein Build-Fehler.
                //game.getManager().render();
	}

	@Override
	public void update(float delta) {
		tmpGame.update(delta);
		fpsCalc.addFrame();
		
		
		
		
		
		
		//TODO: @Eppi connect ui to gamelogic
		//debug healthbar till connected to gamelogic
	}

	@Override
	public void onEnter() {
	}

	@Override
	public void onLeave() {
	}

	@Override
	public void dispose() {
		//stage.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
        //tmpGame.keyDown(keycode);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
        //tmpGame.keyUp(keycode);
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	@Override
	public void callback(String msg) {
		logger.warn(msg);
		GameStates.MAINMENU.init(assetManager);
		GameStates.MAINMENU.activate();
	}
}