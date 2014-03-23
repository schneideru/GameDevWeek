package de.hochschuletrier.gdw.ws1314.hud;

import org.lwjgl.opengl.GL11;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.esotericsoftware.tablelayout.BaseTableLayout.Debug;

import de.hochschuletrier.gdw.commons.gdx.assets.AssetManagerX;
import de.hochschuletrier.gdw.commons.gdx.utils.DrawUtil;
import de.hochschuletrier.gdw.ws1314.Main;
import de.hochschuletrier.gdw.ws1314.hud.elements.LevelList;
import de.hochschuletrier.gdw.ws1314.hud.elements.ListElement;

public class MainMenuStage extends AutoResizeStage {
	
	private BitmapFont font;
	private Skin defaultSkin;
	
	private LevelList levelList;
	
	
	private Table uiTable;
	
	//buttons
	private ImageButton playServer;
	private ImageButton gameBrowser;
	private ImageButton options; 
	private ImageButton credits;
	private ImageButton exit;
	
	//test
	TextButton startServerAndPlay;
	
	public MainMenuStage() {
		super();
	}


	AssetManagerX assetManager;

	public void init(AssetManagerX assetManager) {
		this.defaultSkin = new Skin(Gdx.files.internal("data/huds/default.json"));
		uiTable = new Table();
		this.assetManager = assetManager;
		
		Main.inputMultiplexer.addProcessor(this);
		
		uiTable.setFillParent(true); // ganzen platz in Tabelle nutzen
		uiTable.debug(Debug.all); //debug output
		this.addActor(uiTable);
		
		Label playerNameLabel = new Label("Player name: ", defaultSkin);
		uiTable.add(playerNameLabel);
		
		Table tmpTable = new Table(); 
		uiTable.add(tmpTable).pad(20);
		
		TextureRegion texture = new TextureRegion(assetManager.getTexture("menuButtonPlayClient"));
		ImageButtonStyle style = new ImageButtonStyle(defaultSkin.get(ButtonStyle.class));
		style.imageUp = new TextureRegionDrawable(texture);
		gameBrowser = new ImageButton(style);
		
		texture = new TextureRegion(assetManager.getTexture("menuButtonPlayServer"));
		style = new ImageButtonStyle(defaultSkin.get(ButtonStyle.class));
		style.imageUp = new TextureRegionDrawable(texture);
		playServer = new ImageButton(style);
		
		texture = new TextureRegion(assetManager.getTexture("menuButtonOptions"));
		style = new ImageButtonStyle(defaultSkin.get(ButtonStyle.class));
		style.imageUp = new TextureRegionDrawable(texture);
		options = new ImageButton(style);
		
		texture = new TextureRegion(assetManager.getTexture("menuButtonCredits"));
		style = new ImageButtonStyle(defaultSkin.get(ButtonStyle.class));
		style.imageUp = new TextureRegionDrawable(texture);
		credits = new ImageButton(style);
		
		texture = new TextureRegion(assetManager.getTexture("menuButtonExit"));
		style = new ImageButtonStyle(defaultSkin.get(ButtonStyle.class));
		style.imageUp = new TextureRegionDrawable(texture);
		exit = new ImageButton(style);
		
		tmpTable.add(gameBrowser).pad(5).prefSize(50);
		tmpTable.add(playServer).pad(5).prefSize(50);

		uiTable.row();		
		tmpTable = new Table();
		uiTable.add(tmpTable).pad(20);
		
		tmpTable.add(options).pad(5);
		tmpTable.add(credits).pad(5);
		tmpTable.add(exit).pad(5);
	}

	public void render() {		
		Gdx.gl.glClear(GL11.GL_DEPTH_BUFFER_BIT);
		this.act(Gdx.graphics.getDeltaTime());
		
		DrawUtil.batch.flush();
		this.draw();
		//Table.drawDebug(this);
	}

	public void addLevel(String levelName) {
		levelList.addLevel(levelName);
	}
	
	public ListElement getSelecetedLevel() {
		return levelList.getSelected();
	}
	
	// getter for adding listener to the buttons

	//for testing server-client stuff
	public void resize(int width, int height) {
		super.resize(width, height);
		if(this.xScale >0 && this.yScale>0)
			uiTable.setScale(this.xScale, this.yScale);
	}
	
	public ImageButton getGameBrowserButton() {
		return gameBrowser;
	}
	
	public ImageButton getPlayServerButton() {
		return playServer;
	}	
	
	public TextButton getStartServerAndPlayButton() {
		return startServerAndPlay;
	}
	
	public ImageButton getOptionsButton() {
		return options;
	}
	
	public ImageButton getCreditsButton() {
		return options;
	}
	
	public ImageButton getExitButton() {
		return exit;
	}
}
