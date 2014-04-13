package ai.model;

import java.util.SortedSet;
import java.util.TreeSet;

import ai.model.tetrominos.*;
import net.sourceforge.jetris.Figure;
import net.sourceforge.jetris.figures.*;

public class Grid implements Comparable<Grid>
{
	public static final int WIDTH = 10;
	public static final int HEIGHT = 20;
	private Cell[][] board;
	private Tetromino tetromino;
	private Tetromino nextTetromino;

	private int rotations, translations, down;

	public Grid()
	{
		board = new Cell[WIDTH][HEIGHT];
		for (int col = 0; col < WIDTH; col++)
			for (int row = 0; row < HEIGHT; row++)
				board[col][row] = new Cell(col, row);
		rotations = 0;
		translations = 0;
		down = 0;
		// TODO KEEP ? updateTetromino();
	}

	public Grid(Grid grid)
	{
		this.board = new Cell[WIDTH][HEIGHT];
		for (int col = 0; col < WIDTH; col++)
			for (int row = 0; row < HEIGHT; row++)
			{
				this.board[col][row] = new Cell(col, row);
				if (grid.board[col][row].isOccupied())
					this.board[col][row].occupy();
			}
		switch (grid.tetromino.getType())
		{
		case I:
			this.tetromino = new TetrominoI(grid.tetromino);
			break;
		case J:
			this.tetromino = new TetrominoJ(grid.tetromino);
			break;
		case L:
			this.tetromino = new TetrominoL(grid.tetromino);
			break;
		case O:
			this.tetromino = new TetrominoO(grid.tetromino);
			break;
		case S:
			this.tetromino = new TetrominoS(grid.tetromino);
			break;
		case T:
			this.tetromino = new TetrominoT(grid.tetromino);
			break;
		case Z:
			this.tetromino = new TetrominoZ(grid.tetromino);
			break;
		}
		rotations = 0;
		translations = 0;
		down = 0;
	}

	public Cell getCell(int row, int col)
	{
		if (row < 0 || row >= HEIGHT || col < 0 || col >= WIDTH)
		{
			Cell a = new Cell(row, col);
			a.occupy();
			return a;
		}
		return board[col][row];
	}

	public boolean moveDown()
	{
		for (Cell cell : tetromino.getCells(this))
		{
			if (isOccupied(cell.getCol(), cell.getRow() + 1))
				return false;
		}
		tetromino.fall();
		return true;
	}

	/**
	 * D√©place le tetromino vers la gauche
	 * 
	 * @return
	 */
	public boolean moveLeft()
	{
		for (Cell cell : tetromino.getCells(this))
			if (isOccupied(cell.getCol() - 1, cell.getRow()))
				return false;
		tetromino.moveLeft();
		return true;
	}

	/**
	 * D√©place le tetromino vers la droite
	 */
	public boolean moveRight()
	{
		for (Cell cell : tetromino.getCells(this))
			if (isOccupied(cell.getCol() + 1, cell.getRow()))
				return false;
		tetromino.moveRight();
		return true;
	}

	/**
	 * Tourne le tetromino dans le sens horaire
	 */
	public void turnClockwise()
	{
		// tetromino.turnCounterClockwise(this);
		tetromino.turnClockwise(this);
	}

	/**
	 * Tourne le tetromino dans le sens hiraire inverse
	 */
	public void turnCounterClockwise()
	{
		tetromino.turnCounterClockwise(this);
	}

	/**
	 * V√©rifie si le tetromino peut se d√©placer suivant les coordonn√©es x, y
	 * 
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isOccupied(int col, int row)
	{
		if (col < 0)
			return true;
		if (row < 0)
			return true;
		if (row >= HEIGHT)
			return true;
		if (col >= WIDTH)
			return true;

		return board[col][row].isOccupied();
	}

	public String toString()
	{
		String str = "\n";
		for (int row = 0; row < HEIGHT; row++)
		{
			str += "|";
			for (int col = 0; col < WIDTH; col++)
			{
				if (getCell(row, col).isOccupied())
					str += " X ";
				else if (tetromino != null
						&& tetromino.getCells(this).contains(getCell(row, col)))
					str += " O ";
				else
					str += " . ";
			}
			str += "|\n";
			if (row == HEIGHT - 1)
				str += "\\______________________________/";
		}

		return str;
	}

	public void setCell(int row, int col, boolean occupied)
	{
		if (occupied)
			board[col][row].occupy();
		else
			board[col][row].free();
	}

	public void setFigure(Figure figure)
	{
		if (figure instanceof FigureI)
			tetromino = new TetrominoI();
		if (figure instanceof FigureO)
			tetromino = new TetrominoO();
		if (figure instanceof FigureL)
			tetromino = new TetrominoL();
		if (figure instanceof FigureS)
			tetromino = new TetrominoS();
		if (figure instanceof FigureZ)
			tetromino = new TetrominoZ();
		if (figure instanceof FigureT)
			tetromino = new TetrominoT();
		if (figure instanceof FigureJ)
			tetromino = new TetrominoJ();
	}

	public SortedSet<Grid> children()
	{
		SortedSet<Grid> children = new TreeSet<Grid>();
		Grid gridA, gridB;
		int t = 0;
		gridA = new Grid(this);
		for (int i = 0; i < 4; i++)
		{
			while (gridA.moveRight())
			{
				t++;
				gridB = new Grid(gridA);
				while (gridB.moveDown())
					gridB.down++;
				gridB.rotations = i;
				gridB.translations = t;
				children.add(gridB);
			}
			while (gridA.moveLeft())
			{
				t--;
				gridB = new Grid(gridA);
				while (gridB.moveDown())
					gridB.down++;
				gridB.rotations = i;
				gridB.translations = t;
				children.add(gridB);
			}
			gridA.turnClockwise();
		}
		return children;
	}

	@Override
	public int compareTo(Grid o)
	{
		return (int) (o.compute() - this.compute());
	}

	public double compute()
	{
		double val = 0;
		val += -4.500158825082766 * landingHeight();
		val += 3.4181268101392694 * rowsEliminated();// OK
		val += -3.2178882868487753 * rowsTransitions();// OK
		val += -9.348695305445199 * columnsTransitions();// OK
		val += -7.899265427351652 * holesNumber();// OK
		val += -3.3855972247263626 * wellSums(); // OK
		return val;
	}

	private double wellSums()
	{
		double i = 0;
		for (int row = 0; row < HEIGHT; row++)
			for (int col = 0; col < WIDTH; col++)
				if (!occupied(col, row) && occupied(col - 1, row)
						&& occupied(col + 1, row))
					i += wellValue(col, row);
		return i;
	}

	private double wellValue(int col, int row)
	{
		double i = 0;
		int r = row;
		do
		{
			i++;
			r++;
		} while (!occupied(col, r));
		return i;
	}

	private double holesNumber()
	{
		double i = 0;
		for (int col = 0; col < WIDTH; col++)
			i += countHolesinColumn(col);
		return i;
	}

	private double countHolesinColumn(int col)
	{
		for (int row = 0; row < HEIGHT; row++)
			if (occupied(col, row))
				return countHolesBelowCase(col, row);
		return 0;
	}

	private double countHolesBelowCase(int col, int row)
	{
		for (int i = row + 1; i <= HEIGHT; i++)
			if (!occupied(col, i))
				return 1;
		return 0;
	}

	private double columnsTransitions()
	{
		double i = 0;
		for (int col = 0; col < WIDTH; col++)
		{
			boolean b = true;
			for (int row = 0; row < HEIGHT; row++)
			{
				if (occupied(col, row) != b)
				{
					i++;
					b = occupied(col, row);
				}
			}
		}
		return i;
	}

	/**
	 * The total number of row transitions. A row transition occurs when an
	 * empty cell is adjacent to a filled cell on the same row and vice versa.
	 */
	private double rowsTransitions()
	{
		double i = 0;
		for (int row = 0; row < HEIGHT; row++)
		{
			boolean b = true;
			for (int col = 0; col < WIDTH; col++)
			{
				if (occupied(col, row) != b)
				{
					i++;
					b = occupied(col, row);
				}
			}
		}
		return i;
	}

	private double rowsEliminated()
	{
		double d = 0;
		for (int i = 0; i < HEIGHT; i++)
			d += testRow(i);
		return d;
	}

	private double testRow(int row)
	{
		for (int i = 0; i < WIDTH; i++)
			if (!occupied(i, row))
				return 0;
		return 1;
	}

	/**
	 * Test si la case situÈe ‡ la colonne col , ligne row est occupÈ
	 * 
	 * @param col
	 * @param row
	 * @return true si la case est hors du jeu
	 */
	private boolean occupied(int col, int row)
	{
		if (col < 0 || row < 0 || col >= WIDTH || row >= HEIGHT)
			return true;
		return board[col][row].isOccupied()
				|| tetromino.getCells(this).contains(getCell(row, col));
	}

	public int getRotation()
	{
		return rotations;
	}

	public int getTranslation()
	{
		return translations;
	}

	public int getDown()
	{
		return down;
	}

	private double landingHeight()
	{
		return tetromino.getHeightPosition() + tetromino.getHeigt() / 2;
	}

	public Grid removeTetro()
	{
		if (tetromino != null)
		{
			tetromino.representation = new boolean[1][1];
			tetromino.representation[0][0] = false;
		}
		return this;
	}
}
