package ai;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;

import net.sourceforge.jetris.JetrisMainFrame;
import ai.executor.Executor;
import ai.executor.InternExecutor;
import ai.input.CameraInput;
import ai.input.GameInput;
import ai.input.NoInputException;
import ai.input.TetrisDataInput;
import ai.model.Grid;

public class IAMain implements Runnable
{
	private TetrisDataInput tdi;
	private Grid currentState;
	private SortedSet<Grid> possibleStates;

	public static void main(String args[])
	{
		// (new Thread(new IAMain(new GameInput(new
		// JetrisMainFrame())))).start();
		(new Thread(new IAMain(new CameraInput()))).start();
	}

	public IAMain(TetrisDataInput tdi)
	{
		this.tdi = tdi;
	}

	@Override
	public void run()
	{
		try
		{
			Thread.sleep(5000);
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		System.out.println("C'est parti!");
		while (!tdi.isGameOver())
		{
			// Acquisition de la grille
			try
			{
				try
				{
					currentState = tdi.getTetrisData();}catch (IndexOutOfBoundsException e)
				{
					continue;
				}
				// création des sous grilles
				System.out.println(currentState);
				possibleStates = currentState.children();
				System.out.println(possibleStates.first());
				// calcul du chemin entre la grille actuelle et le sous grille
				// envoi du chemin
				play(getCommands());
			} catch (NoInputException e)
			{
				e.printStackTrace();
				continue;
			}
			try
			{
				for (int i = 0; i < 20; i++)
					if (tdi.getTetrisData().removeTetro()
							.compareTo(possibleStates.first()) > 1
							|| tdi.getTetrisData().removeTetro()
									.compareTo(possibleStates.first()) < -1)
						try
						{
							Thread.sleep(100);
						} catch (InterruptedException e)
						{
							e.printStackTrace();
						}
			} catch (IndexOutOfBoundsException | NoInputException e)
			{
				System.err.println("Probleme de camera");
				e.printStackTrace();
			}
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	private List<Command> getCommands()
	{
		List<Command> list = new LinkedList<Command>();
		if (possibleStates.isEmpty())
			return list;
		Grid objective = possibleStates.first();
		for (int i = 0; i < objective.getRotation(); i++)
			list.add(Command.ROTATE);
		for (int i = 0; i < objective.getTranslation(); i++)
			list.add(Command.MOVE_RIGHT);
		for (int i = 0; i > objective.getTranslation(); i--)
			list.add(Command.MOVE_LEFT);
		list.add(Command.MOVE_DOWN);
		System.out.println(list);
		return list;
	}

	private void play(List<Command> commands)
	{
		Executor ex = new InternExecutor();
		for (Command c : commands)
		{
			ex.execute(c);
			try
			{
				Thread.sleep(10);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}
}
