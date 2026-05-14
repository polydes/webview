package com.byrobingames.manager.app.pages;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.*;

import com.byrobingames.manager.ByRobinGameExtension;

import stencyl.app.comp.UI;
import stencyl.app.comp.dg.DialogPanel;
import stencyl.app.lnf.Fonts;
import stencyl.app.lnf.Theme;
import stencyl.core.SWC;

public class WebviewPage extends JPanel
{
	protected DialogPanel dPanel;
	
	private static WebviewPage _instance;
	
	private JTextArea htmlArea;
	
	public WebviewPage(){
		super(new BorderLayout());

		dPanel = new DialogPanel(Theme.LIGHT_BG_COLOR3);
		
		setSettings();
		
		add(dPanel, BorderLayout.NORTH);
	}
	public void setSettings(){
		JLabel title = new JLabel("Webview Settings");
		title.setFont(SWC.get(Fonts.class).getTitleBoldFont());
		title.setForeground(Theme.TEXT_COLOR);
		dPanel.addGenericRow(title);

		JLabel label = new JLabel("<html>There are no special settings for Webview.<br><br>"
				+ "You can add HTML code below to use with the openHTML block<br>"
				+ "<strong>(Leave the textfield in openHTML block empty if you use the below textarea)<br> </strong>");
		label.setFont(SWC.get(Fonts.class).getNormalFont());
		label.setForeground(Theme.TEXT_COLOR);
		dPanel.addGenericRow(label);
		
		htmlArea = new JTextArea();
		htmlArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));	
		htmlArea.setText(ByRobinGameExtension.WVHTMLCODE);
		htmlArea.setLineWrap(true);
		htmlArea.setWrapStyleWord(true);
		JScrollPane scroll = UI.createScrollPane(htmlArea);
		scroll.setMinimumSize(new Dimension(100, 80));
		scroll.setPreferredSize(new Dimension(100, 300));
		dPanel.addGenericRow("HTML Code",scroll);
		
		dPanel.finishBlock();
	}
	
	public void save()
	{
		ByRobinGameExtension.WVHTMLCODE = htmlArea.getText();
		
		revalidate();
		repaint();
		
	}
	
	public static WebviewPage get()
	{
		if (_instance == null)
			_instance = new WebviewPage();

		return _instance;
	}
	
	public static void disposeInstance()
	{
		_instance = null;
	}

}


