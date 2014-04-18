package net.sourceforge.jetris;

import java.awt.event.KeyEvent;

public class SetupKey
{

	protected int keyTurn;
	protected int keyLeft;
	protected int keyRight;
	protected int keyDrop;
	
	public SetupKey () {
		
		keyTurn = KeyEvent.VK_V;
		keyLeft = KeyEvent.VK_O;
		keyRight = KeyEvent.VK_T;
		keyDrop = KeyEvent.VK_SEMICOLON;
	}
}
