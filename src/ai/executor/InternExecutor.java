package ai.executor;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import ai.Command;

public class InternExecutor implements Executor
{

	@Override
	public void execute(Command c)
	{
		System.out.println("->"+c);
		try
		{
			switch (c)
			{
			case MOVE_DOWN:
				new Robot().keyPress(KeyEvent.VK_SPACE);
				break;
			case MOVE_LEFT:
				new Robot().keyPress(KeyEvent.VK_LEFT);
				break;
			case MOVE_RIGHT:
				new Robot().keyPress(KeyEvent.VK_RIGHT);
				break;
			case ROTATE:
				new Robot().keyPress(KeyEvent.VK_UP);
				break;
			}
		} catch (AWTException e)
		{
			e.printStackTrace();
		}
	}
}
