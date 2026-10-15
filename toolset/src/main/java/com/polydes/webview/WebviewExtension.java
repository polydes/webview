package com.polydes.webview;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.polydes.webview.app.WebviewPage;
import stencyl.app.ext.PageAddon;
import stencyl.app.ext.PageAddon.EngineExtensionPageAddon;
import stencyl.core.api.fs.Locations;
import stencyl.core.ext.GameExtension;
import stencyl.core.ext.engine.ExtensionInstanceManager.FormatUpdateSubmitter;
import stencyl.core.io.FileHelper;
import stencyl.sw.app.center.GameLibrary;
import stencyl.sw.core.lib.game.Game;

public class WebviewExtension extends GameExtension
{
	//webview
	public static String WVHTMLCODE;
	
	private static final Logger log = Logger.getLogger(WebviewExtension.class);

	@Override
	protected void onLoad() {
		super.onLoad();

		PageAddon sidebarPage = new EngineExtensionPageAddon(owner())
		{
			@Override
			public JPanel getPage()
			{
				return WebviewPage.get();
			}
		};

		owner().getAddons().setAddon(GameLibrary.DASHBOARD_SIDEBAR_PAGE_ADDONS, sidebarPage);

		try {
			Path datafolderlocation = ((Game) getProject()).getFiles().getExtensionGameDataLocation("com.byrobingames.manager");
			Path wvFile = datafolderlocation.resolve("webview.html");
			if(Files.exists(wvFile))
			{
				WVHTMLCODE = FileHelper.readFileToString(wvFile);
			}
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	protected void onUnload() {
		WebviewPage.disposeInstance();
	}

	@Override
	protected void onSave() {
		WebviewPage.get().save();
		
		try {
			Game game = (Game) getProject();

			Path datafolderlocation = game.getFiles().getExtensionGameDataLocation("com.byrobingames.manager");
			FileHelper.writeStringToFile(datafolderlocation.resolve("webview.html"), WVHTMLCODE);

		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public void onGameBuild() {
		try {
			Path assetsDataDir = Locations.getHXProjectDir(getProject()).resolve("Assets/data/com.byrobingames.manager");
			FileHelper.writeStringToFile(assetsDataDir.resolve("webview.html"), WVHTMLCODE);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}

	///////////////////

	@Override
	public void updateFromVersion(int fromVersion, FormatUpdateSubmitter formatUpdateQueue) {

	}
}