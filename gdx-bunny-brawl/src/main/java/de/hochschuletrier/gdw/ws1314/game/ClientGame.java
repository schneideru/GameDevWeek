package de.hochschuletrier.gdw.ws1314.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;

import de.hochschuletrier.gdw.commons.gdx.assets.AssetManagerX;
import de.hochschuletrier.gdw.commons.gdx.input.InputInterceptor;
import de.hochschuletrier.gdw.commons.gdx.tiled.TiledMapRendererGdx;
import de.hochschuletrier.gdw.commons.resourcelocator.CurrentResourceLocator;
import de.hochschuletrier.gdw.commons.tiled.Layer;
import de.hochschuletrier.gdw.commons.tiled.LayerObject;
import de.hochschuletrier.gdw.commons.tiled.TileSet;
import de.hochschuletrier.gdw.commons.tiled.TiledMap;
import de.hochschuletrier.gdw.commons.tiled.tmx.TmxImage;
import de.hochschuletrier.gdw.ws1314.Main;
import de.hochschuletrier.gdw.ws1314.entity.ClientEntityManager;
import de.hochschuletrier.gdw.ws1314.input.InputHandler;

/**
 * Created by Jerry on 18.03.14.
 */
// Modfied by El Fapo: updated intention changes
public class ClientGame {
	private ClientEntityManager entityManager;
	private ClientServerConnect netManager;
	private int Inputmask;
	private TiledMap map;
	private TiledMapRendererGdx mapRenderer;
	private InputHandler inputHandler;

	public ClientGame(InputProcessor inputProcessor) {
		entityManager = ClientEntityManager.getInstance();
		netManager = ClientServerConnect.getInstance();
		
		inputHandler = new InputHandler();
		Main.inputMultiplexer.addProcessor(inputHandler);

	}

	public void init(AssetManagerX assets) {
		map = loadMap("data/maps/miniarena.tmx");
		HashMap<TileSet, Texture> tilesetImages = new HashMap<TileSet, Texture>();
		
		for (TileSet tileset : map.getTileSets()) {
			TmxImage img = tileset.getImage();
			String filename = CurrentResourceLocator.combinePaths(tileset.getFilename(),
					img.getSource());
			tilesetImages.put(tileset, new Texture(filename));
		}
		mapRenderer = new TiledMapRendererGdx(map, tilesetImages);
		mapRenderer.setDrawLines(false);
	}

	public void render() {
		for (Layer layer : map.getLayers()) {
			mapRenderer.render(0, 0, layer);
		}
	}

	public void update(float delta) {
		entityManager.update(delta);
	}

	public TiledMap loadMap(String filename) {
		try {
			return new TiledMap(filename, LayerObject.PolyMode.ABSOLUTE);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Map konnte nicht geladen werden: "
					+ filename);
		}
	}

}
