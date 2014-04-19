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

	public void setKeyRight(int newKey) {keyRight = newKey;}
	public void setKeyLeft(int newKey) {keyLeft = newKey;}
	public void setKeyTurn(int newKey) {keyTurn = newKey;}
	public void setKeyDrop(int newKey) {keyDrop = newKey;}
	
	public int getKeyRight() {return keyRight;}
	public int getKeyLeft() {return keyLeft;}
	public int getKeyTurn() {return keyTurn;}
	public int getKeyDrop() {return keyDrop;}
}
