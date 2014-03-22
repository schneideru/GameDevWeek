package de.hochschuletrier.gdw.ws1314.states;

import de.hochschuletrier.gdw.commons.gdx.assets.AssetManagerX;
import de.hochschuletrier.gdw.commons.gdx.state.GameState;
import de.hochschuletrier.gdw.ws1314.Main;
import de.hochschuletrier.gdw.ws1314.hud.ServerLobbyStage;
import de.hochschuletrier.gdw.ws1314.lobby.IServerLobbyListener;
import de.hochschuletrier.gdw.ws1314.lobby.ServerLobbyManager;
import de.hochschuletrier.gdw.ws1314.network.DisconnectCallback;
import de.hochschuletrier.gdw.ws1314.network.NetworkManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class ServerLobbyState extends GameState implements IServerLobbyListener, DisconnectCallback {
	private static final Logger logger = LoggerFactory.getLogger(ServerLobbyState.class);
	
	protected ServerLobbyManager serverLobby;
	
	private ServerLobbyStage stage;
	
	private DisconnectClick disconnectClickListener;
	
    public void init (AssetManagerX assetManager) {
        super.init (assetManager);
        
        this.disconnectClickListener = new DisconnectClick();
    }

    public void render () {
    	this.stage.render();
    }

    public void update (float delta) {
        // TODO
    }

    public void dispose () {
        // TODO
    }

	public void onEnter() {
		super.onEnter();
		Main.inputMultiplexer.addProcessor(stage);
		
		// TODO: Temporär nur zum localen Testen
        if (!NetworkManager.getInstance().isServer())
        {
        	NetworkManager.getInstance().listen(NetworkManager.getInstance().getDefaultServerIp(), NetworkManager.getInstance().getDefaultPort(), 10);
        }
        
        NetworkManager.getInstance().setDisconnectCallback(this);
        
    	serverLobby = new ServerLobbyManager();
    	serverLobby.addServerLobbyListener(this);
    	
    	
    	logger.info("Server-Lobby created.");
    	
    	this.stage = new ServerLobbyStage();
    	this.stage.init(assetManager);
    	
    	this.stage.getDisconnectButton().addListener(this.disconnectClickListener);

    	stage.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public void onEnterComplete() {
		super.onEnterComplete();
	}

	public void onLeave() {
		super.onLeave();
		
		NetworkManager.getInstance().setDisconnectCallback(null);
		
		this.stage.getDisconnectButton().removeListener(this.disconnectClickListener);
		
		Main.inputMultiplexer.removeProcessor(this.stage);
		
		this.serverLobby = null;
		this.stage = null;
	}

	public void onLeaveComplete() {
		super.onLeaveComplete();
	}
    
	public void startGame() {
		((ServerGamePlayState) GameStates.SERVERGAMEPLAY.get()).setPlayerDatas(this.serverLobby.getPlayers());
		((ServerGamePlayState) GameStates.SERVERGAMEPLAY.get()).setMapName(this.serverLobby.getMap());
		GameStates.SERVERGAMEPLAY.init(assetManager);
		GameStates.SERVERGAMEPLAY.activate();
		logger.info("Sending GameStateChange to Clients");
		NetworkManager.getInstance().sendGameState(GameStates.CLIENTGAMEPLAY);
	}
	
	private class DisconnectClick extends ClickListener {
		public void clicked(InputEvent event, float x, float y) {
			NetworkManager.getInstance().stopServer();
		}
	}

	public void disconnectCallback(String msg) {
		logger.info(msg);
		GameStates.MAINMENU.init(assetManager);
		GameStates.MAINMENU.activate();
	}
}
