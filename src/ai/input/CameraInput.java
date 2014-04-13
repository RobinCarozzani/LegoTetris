package ai.input;

import java.util.List;

import vision.BadWebcamException;
import vision.NoWebCamException;
import vision.Vision;
import net.sourceforge.jetris.Figure;
import net.sourceforge.jetris.figures.FigureI;
import net.sourceforge.jetris.figures.FigureJ;
import net.sourceforge.jetris.figures.FigureL;
import net.sourceforge.jetris.figures.FigureO;
import net.sourceforge.jetris.figures.FigureS;
import net.sourceforge.jetris.figures.FigureT;
import net.sourceforge.jetris.figures.FigureZ;
import ai.model.Grid;

public class CameraInput implements TetrisDataInput
{
	private Grid g;
	private Vision vision;

	public CameraInput()
	{
		try
		{
			vision = new Vision();
		} catch (NoWebCamException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	public CameraInput(Vision vision)
	{
		this.vision = vision;
	}

	@Override
	public Grid getTetrisData() throws NoInputException
	{
		g = new Grid();
		List<List<Integer>> rawInput;
		try
		{
			rawInput = vision.getJeu();
		} catch (BadWebcamException e)
		{
			throw new NoInputException(e.getMessage());
		}
		for (int row = 0; row < 20; row++)
			for (int col = 0; col < 10; col++)
				g.setCell(row, col, rawInput.get(row).get(col) == 1);
		/*System.out.println("---------------------------------------------");
		System.out.println("---------------------------------------------");
		System.out.println("---------------------------------------------");
		for (int row = 0; row < 20; row++)
		{
			for (int col = 0; col < 10; col++)
				System.out.print(rawInput.get(row).get(col));
			System.out.println("");
		}*/
		setTetromino(g, rawInput);
		return g;
	}

	private void setTetromino(Grid g, List<List<Integer>> rawInput) throws NoInputException
	{
		int row = 0;
		int col = 0;
		retour: for (int i = 0; i < Grid.HEIGHT; i++)
			for (int j = 0; j < Grid.WIDTH; j++)
				if (rawInput.get(i).get(j) == 2)
				{
					row = i;
					col = j;
					break retour;
				}
		Figure currentTetromino = null;
		try{
		if (rawInput.get(row).get(col + 1) == 2
				&& rawInput.get(row + 1).get(col + 1) == 2
				&& rawInput.get(row + 1).get(col + 2) == 2)
			currentTetromino = new FigureZ();
		if (rawInput.get(row).get(col + 1) == 2
				&& rawInput.get(row + 1).get(col - 1) == 2
				&& rawInput.get(row + 1).get(col) == 2)
			currentTetromino = new FigureS();
		if (rawInput.get(row).get(col + 1) == 2
				&& rawInput.get(row).get(col + 2) == 2
				&& rawInput.get(row + 1).get(col + 1) == 2)
			currentTetromino = new FigureT();
		if (rawInput.get(row + 1).get(col) == 2
				&& rawInput.get(row + 2).get(col) == 2
				&& rawInput.get(row + 2).get(col - 1) == 2)
			currentTetromino = new FigureJ();
		if (rawInput.get(row + 1).get(col) == 2
				&& rawInput.get(row + 1).get(col) == 2
				&& rawInput.get(row + 2).get(col + 1) == 2)
			currentTetromino = new FigureL();
		if (rawInput.get(row + 1).get(col) == 2
				&& rawInput.get(row + 2).get(col) == 2
				&& rawInput.get(row + 3).get(col) == 2)
			currentTetromino = new FigureI();
		if (rawInput.get(row).get(col + 1) == 2
				&& rawInput.get(row + 1).get(col + 1) == 2
				&& rawInput.get(row + 1).get(col) == 2)
			currentTetromino = new FigureO();
		if (currentTetromino == null)
			throw new NoInputException();
		}
		catch (IndexOutOfBoundsException e){
			e.printStackTrace();
		}
		g.setFigure(currentTetromino);
	}

	@Override
	public boolean isGameOver()
	{
		return false;
	}
}
