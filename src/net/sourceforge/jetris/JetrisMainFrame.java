package net.sourceforge.jetris;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
/*import javax.swing.text.html.HTML;*/





import res.ResClass;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.BufferedInputStream;

public class JetrisMainFrame extends JFrame  {
    
	private static final long serialVersionUID = 1L;

	private MainLayout mainLayout = new MainLayout(getPlayPanel());
	
	private static final String NAME = "JETRIS";
    private static final int CELL_H = 24;
	
    private Font font;
    private JPanel playPanel;
    private JPanel[][] cells;
    
    private TetrisGrid tg;
    
    private int nextX;
    private int nextY;
    private Figure f;
    private Figure fNext;
    private FigureFactory ff;
    private boolean isNewFigureDroped;
    private boolean isGameOver;
    private boolean isPause;
    private TimeThread tt;
    private KeyListener keyHandler;
    
    private JPanel about;
    private SetupKey setupKey;
    private JDialog frame;
    
    //MENU
    private JMenuItem jetrisRestart;
    private JMenuItem jetrisPause;
    private JMenuItem jetrisSetup;
    private JMenuItem jetrisExit;
    
    private JMenuItem helpAbout;
    private JMenuItem helpJetris;
    
    private HelpDialog helpDialog;
    
    //private PublishHandler pH;

	public boolean isOccupied(int row, int col)
	{
		for (int j = 0; j < 4; j++)
		{
			if (row == f.arrY[j] + f.offsetY && col == f.arrX[j] + f.offsetX)
				return false;
		}

		return !cells[row][col].getBackground().equals(new Color(55, 55, 55));
	}
    
    private class GridThread extends Thread {
        
        private int count = 0;
        
        public void run() {
            try {
                while (true) {
                    if (isGameOver || isPause) {
                        Thread.sleep(50);
                    } else {
                        if(isNewFigureDroped) {
                            isNewFigureDroped = false;
                            count = 0;
                            nextMove();
                            continue;
                        } else {
                            Thread.sleep(50);
                        }
                        count += 50;
                        if(count + 50*tg.getLevel() >= 1100) {
                            count = 0;
                            nextY++;
                            nextMove();
                        }
                    } 
                }
            } catch (Exception e) {
                e.printStackTrace();
            } 
        }
    }
    
    private class TimeThread extends Thread {
        
        private int hours;
        private int min;
        private int sec;
        
        private int count;
        
        private void incSec() {
            sec++;
            if(sec == 60) {
                sec = 0;
                min++;
            }
            if(min == 60) {
                min = 0;
                hours++;
            } 
        }
        
        private void resetTime() {
            hours = min = sec = 0;
        }
        
        public void run() {
            try {
                while (true) {
                    Thread.sleep(50);
                    if (isGameOver) {
                        Graphics g = playPanel.getGraphics();
                        Font font = new Font(g.getFont().getFontName(), Font.BOLD, 24);
                        g.setFont(font);
                        g.drawString("GAME OVER", 47, 250);

                    } else if(isPause) {
                    	mainLayout.time.setText("PAUSED");
                    } else if(count >= 1000) {
                        count = 0;
                        incSec();
                        mainLayout.time.setText(this.toString());
                    } else {
                        count+=50;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } 
        }
        
        public String toString() {
            StringBuffer sb = new StringBuffer();
            if(hours < 10) {
                sb.append('0');
            }
            sb.append(hours);
            
            sb.append(':');
            
            if(min < 10) {
                sb.append('0');
            }
            sb.append(min);
            
            sb.append(':');
            
            if(sec < 10) {
                sb.append('0');
            }
            sb.append(sec);
            
            return sb.toString();
        }
    }
    
    public JetrisMainFrame() {
        super(NAME);

        this.setSize(1360, 860);        
        //pack();
        this.setResizable(false);

        //SplashScreen sp = new SplashScreen();
        
        setIconImage(loadImage("jetris16x16.png"));
        setupKey = new SetupKey();
        
        
        keyHandler = new KeyAdapter(){

            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                if (code == setupKey.keyLeft || code == KeyEvent.VK_LEFT) {
                    moveLeft();
                } else if(code == setupKey.keyRight || code == KeyEvent.VK_RIGHT) {
                    moveRight();
                } else if(code == KeyEvent.VK_J || code == KeyEvent.VK_DOWN) {
                    moveDown();
                } else if(code == setupKey.keyTurn || code == KeyEvent.VK_UP) {
                    rotation();
                } else if(code == setupKey.keyDrop || code == KeyEvent.VK_SPACE ) {
                    moveDrop();
                }/*else if(code == KeyEvent.VK_R) { //Only for the applet needed
                    restart();
                } else if(code == KeyEvent.VK_P) {
                    pause();
                } */
            }
        };
        addKeyListener(keyHandler);
        
        /*pH = new PublishHandler();*/
        
        font = new Font("Dialog", Font.PLAIN, 12);
        tg = new TetrisGrid();
        ff = new FigureFactory();
        
        initMenu();

        /*************** LAYOUT ***************/
        JPanel all = new JPanel(new BorderLayout());
        
        JPanel infoPanel = new JPanel(new GridBagLayout());
        infoPanel.setSize(420, 50);
        
        all.add(mainLayout, BorderLayout.CENTER);
        all.add(getCopyrightPanel(), BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(all, BorderLayout.CENTER);        
        
        fNext = ff.getRandomFigure();
        dropNext();
        
        GridThread gt = new GridThread();
        tt = new TimeThread();
        gt.start();
        tt.start();

        addWindowFocusListener(new WindowFocusListener(){

            public void windowGainedFocus(WindowEvent arg0) {}

            public void windowLostFocus(WindowEvent arg0) {
                isPause = true;
            }
        });
        
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - getWidth() / 2, screenSize.height / 2 - getHeight() / 2);
        setVisible(true);
        
        /*sp.setVisible(false);
        sp.dispose();*/
    }
    
    
    /*  
     * Méthode permettant de configurer les touches du clavier pour jouer au Tetris (via l'IHM) 
     * */
    
    private void configureButtons() {

    	final String str = "   Change ";
    	final JLabel labelRight = new JLabel("     Right : " + (char)setupKey.getKeyRight());
    	final JLabel labelLeft = new JLabel("     Left : " + (char)setupKey.getKeyLeft());
    	final JLabel labelTurn = new JLabel("     Turn : " + (char)setupKey.getKeyTurn());
    	final JLabel labelDrop = new JLabel("     Drop : " + (char)setupKey.getKeyDrop());
    	
    	/* DEFINITION DES BOUTONS */
    	
    	final JButton buttonRight = new JButton(str + "right");
    	buttonRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event)
			{
				final JDialog test = new JDialog();
				test.add(new JLabel("   Appuyer sur une touche"));
				KeyListener kl = new KeyListener()
				{
					
					@Override
					public void keyTyped(KeyEvent e)
					{
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void keyReleased(KeyEvent e)
					{
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void keyPressed(KeyEvent e)
					{
						setupKey.setKeyRight(e.getKeyCode());
						labelRight.setText("     Right : " + (char)setupKey.getKeyRight());
						test.dispose();
						
					}
				};
				test.addKeyListener(kl);
				test.setVisible(true);
		    	test.setSize(200, 70);
		    	test.setLocationRelativeTo(null);
			}
    	});
    	final JButton buttonLeft = new JButton(str + "left");
    	buttonLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event)
			{
				final JDialog test = new JDialog();
				test.add(new JLabel("   Appuyer sur une touche"));
				KeyListener kl = new KeyListener()
				{
					
					@Override
					public void keyTyped(KeyEvent e)
					{
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void keyReleased(KeyEvent e)
					{
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void keyPressed(KeyEvent e)
					{
						setupKey.setKeyLeft(e.getKeyCode());
						labelLeft.setText("     Left : " + (char)setupKey.getKeyLeft());
						test.dispose();
						
					}
				};
				test.addKeyListener(kl);
				test.setVisible(true);
		    	test.setSize(200, 70);
		    	test.setLocationRelativeTo(null);
			}
    	});
    	final JButton buttonTurn = new JButton(str + "turn");
    	buttonTurn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event)
			{
				final JDialog test = new JDialog();
				test.add(new JLabel("   Appuyer sur une touche"));
				KeyListener kl = new KeyListener()
				{
					
					@Override
					public void keyTyped(KeyEvent e)
					{
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void keyReleased(KeyEvent e)
					{
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void keyPressed(KeyEvent e)
					{
						setupKey.setKeyTurn(e.getKeyCode());
						labelTurn.setText("     Turn : " + (char)setupKey.getKeyTurn());
						test.dispose();
						
					}
				};
				test.addKeyListener(kl);
				test.setVisible(true);
		    	test.setSize(200, 70);
		    	test.setLocationRelativeTo(null);
			}
    	});
    	final JButton buttonDrop = new JButton(str + "drop");
    	buttonDrop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event)
			{
				final JDialog test = new JDialog();
				test.add(new JLabel("   Appuyer sur une touche"));
				KeyListener kl = new KeyListener()
				{
					
					@Override
					public void keyTyped(KeyEvent e)
					{
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void keyReleased(KeyEvent e)
					{
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void keyPressed(KeyEvent e)
					{
						setupKey.setKeyDrop(e.getKeyCode());
						labelDrop.setText("     Drop : " + (char)setupKey.getKeyDrop());
						test.dispose();
						
					}
				};
				test.addKeyListener(kl);
				test.setVisible(true);
		    	test.setSize(200, 70);
		    	test.setLocationRelativeTo(null);
			}
    	});    	
    	
		frame = new JDialog(this, "Configuration des commandes");
    	frame.setVisible(true);
    	frame.setSize(400, 300);
    	frame.setLocationRelativeTo(null);

    	frame.setModal(true);
    	
    	JPanel setupPanel = new JPanel();
    	JPanel buttonPane = new JPanel();
    	GridLayout gl = new GridLayout(4, 2);
    	gl.setVgap(10);
    	gl.setVgap(10);
    	setupPanel.add(labelRight);
    	setupPanel.add(buttonRight);
    	setupPanel.add(labelLeft);
    	setupPanel.add(buttonLeft);
    	setupPanel.add(labelTurn);
    	setupPanel.add(buttonTurn);
    	setupPanel.add(labelDrop);
    	setupPanel.add(buttonDrop);
    	
    	JButton ok = new JButton("  OK  ");
    	ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event)
			{
				frame.dispose();
			}
    	});

    	buttonPane.add(ok);
    	setupPanel.setLayout(gl);
    	
    	frame.getContentPane().add(new JPanel(), BorderLayout.EAST);
    	frame.getContentPane().add(new JLabel("Ecrire en majuscule"), BorderLayout.NORTH);
    	frame.getContentPane().add(setupPanel, BorderLayout.CENTER);
    	frame.getContentPane().add(buttonPane, BorderLayout.SOUTH);
    }
    
    private void initMenu() {
        
        MenuHandler mH = new MenuHandler();
        
        JMenuBar menu = new JMenuBar(); 
        setJMenuBar(menu);
        
        JMenu mJetris = new JMenu();
        menu.add(mJetris);
        mJetris.setText("Jetris");
        mJetris.setMnemonic('J');
        {
            jetrisRestart = new JMenuItem("Restart");
            mJetris.add(jetrisRestart);
            setKeyAcceleratorMenu(jetrisRestart, 'R',0);
            jetrisRestart.addActionListener(mH);
            jetrisRestart.setMnemonic('R');
            
            jetrisPause = new JMenuItem("Pause");
            mJetris.add(jetrisPause);
            setKeyAcceleratorMenu(jetrisPause, 'P',0);
            jetrisPause.addActionListener(mH);
            jetrisPause.setMnemonic('P');
            
            mJetris.addSeparator();
            
            jetrisSetup = new JMenuItem("Setup");
            mJetris.add(jetrisSetup);
            setKeyAcceleratorMenu(jetrisSetup, 'S',0);
            jetrisSetup.addActionListener(mH);
            jetrisSetup.setMnemonic('S');
            
            mJetris.addSeparator();
            
            jetrisExit = new JMenuItem("Exit");
            mJetris.add(jetrisExit);
            setKeyAcceleratorMenu(jetrisExit, KeyEvent.VK_ESCAPE, 0);
            jetrisExit.addActionListener(mH);
            jetrisExit.setMnemonic('X');
        }
        
        JMenu mHelp = new JMenu();
        menu.add(mHelp);
        mHelp.setText("Help");
        mHelp.setMnemonic('H');
        {
            helpJetris = new JMenuItem("Jetris Help");
            mHelp.add(helpJetris);
            setKeyAcceleratorMenu(helpJetris, KeyEvent.VK_F1 ,0);
            helpJetris.addActionListener(mH);
            helpJetris.setMnemonic('J');
            
            helpAbout = new JMenuItem("About");
            mHelp.add(helpAbout);
            helpAbout.addActionListener(mH);
            helpAbout.setMnemonic('A');
        }
    }
    
    private void setKeyAcceleratorMenu(JMenuItem mi, int keyCode, int mask) {
        KeyStroke ks = KeyStroke.getKeyStroke(keyCode, mask);
        mi.setAccelerator(ks);
    }

    private JPanel getPlayPanel() {
        playPanel = new JPanel();
        playPanel.setLayout(new GridLayout(20,10));
        playPanel.setPreferredSize(new Dimension(15*CELL_H, 30*CELL_H));

        cells = new JPanel[20][10];
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                cells[i][j] = new JPanel();
                cells[i][j].setBackground(new Color(55,55,55));
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY)); /* grille des lignes */
                playPanel.add(cells[i][j]);
            }
        }
        return playPanel;
    }
    
    public TetrisGrid getTG() {
    	
    	return tg;
    }
    
    private JPanel getCopyrightPanel() {
        JPanel r = new JPanel(new BorderLayout());
        
        BoxLayout rL = new BoxLayout(r,BoxLayout.X_AXIS);
        r.setLayout(rL);
        r.setBorder(new EtchedBorder());
        r.add(Box.createRigidArea(new Dimension(30,0)));
        
        JLabel jL = new JLabel("JETRIS : Ferric, Le Barbe, Meunier, Carozzani, Furon  ");
        jL.setFont(font);
        
        r.add(jL);
        
        return r;
    }
    
    static Image loadImage(String imageName) {
        try {
            Image im = ImageIO.read(new BufferedInputStream(
                    new ResClass().getClass().getResourceAsStream(imageName)));
            return im;
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }
    
    private synchronized void nextMove() {
        f.setOffset(nextX, nextY);
        
        if(tg.addFigure(f)) {
            dropNext();
            f.setOffset(nextX, nextY);
            paintTG();
        } else {
            clearOldPosition();
        }
        paintNewPosition();
    }
    
    private void clearOldPosition() {
        for (int j = 0; j < 4; j++) {
            cells[f.arrY[j]+f.offsetYLast][f.arrX[j]+f.offsetXLast].setBackground(new Color (55, 55, 55));
            cells[f.arrY[j]+f.offsetYLast][f.arrX[j]+f.offsetXLast].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        }
    }
    
    private void paintNewPosition() {
        for (int j = 0; j < 4; j++) {
            cells[f.arrY[j]+f.offsetY][f.arrX[j]+f.offsetX].setBackground(f.getGolor());
            cells[f.arrY[j]+f.offsetY][f.arrX[j]+f.offsetX].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        } 
    }
    
    private void paintTG() {
        int i = 0;
        Color c;
        for (int[] arr : tg.gLines) {
            for (int j = 0; j < arr.length; j++) {
                if(arr[j]!= 0) {
                    switch (arr[j]) {
                    case Figure.I: c = Figure.COL_I; break;
                    case Figure.T: c = Figure.COL_T; break;
                    case Figure.O: c = Figure.COL_O; break;
                    case Figure.J: c = Figure.COL_J; break;
                    case Figure.L: c = Figure.COL_L; break;
                    case Figure.S: c = Figure.COL_S; break;
                    default: c = Figure.COL_Z; break;
                    }
                    cells[i][j].setBackground(c);
                    cells[i][j].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                } else {
                    cells[i][j].setBackground(new Color (55, 55, 55));
                    cells[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
                } 
            }
            i++;
        }
    }
    
    private void showNext(Figure f) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
            	mainLayout.next[i][j].setBackground(new Color (55, 55, 55));
            	mainLayout.next[i][j].setBorder(BorderFactory.createEmptyBorder());
            }
        }
        
        for (int j = 0; j < f.arrX.length; j++) {
        	mainLayout.next[f.arrY[j]][f.arrX[j]].setBackground(f.getGolor());
        	mainLayout.next[f.arrY[j]][f.arrX[j]].setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        }
    }
    
    private void dropNext() {
        if(isGameOver) return;
        nextX = 4;
        nextY = 1;

        mainLayout.score.setText(""+tg.getScore());
        mainLayout.lines.setText(""+tg.getLines());
        mainLayout.levelLabel.setText(tg.getLevel()+" / " + tg.MAXLEVEL);

        f = fNext;
        fNext = ff.getRandomFigure();
        showNext(fNext);

        isGameOver = tg.isGameOver(f);
        

        isNewFigureDroped = true;
    }
    
    private void moveLeft() {
        if(isGameOver || isPause) return;
        if(nextX-1 >= 0) {
            if (tg.isNextMoveValid(f,f.offsetX-1,f.offsetY)) {
                nextX--;
                nextMove();
            }
        }
    }
    
    private void moveRight() {
        if(isGameOver || isPause) return;
        if(f.getMaxRightOffset()+1 < 10) {
            if (tg.isNextMoveValid(f,f.offsetX+1,f.offsetY)) {
                nextX++;
                nextMove();
            }
        }
    }
    
    private synchronized void moveDown() {
        if(isGameOver || isPause) return;
        nextY++;
        nextMove();
    }
    
    private synchronized void moveDrop() {
        if(isGameOver || isPause) return;
        
        f.offsetYLast = f.offsetY;
        f.offsetXLast = f.offsetX;
        clearOldPosition();
        
        while(tg.isNextMoveValid(f, f.offsetX, f.offsetY)) {
            f.setOffset(f.offsetX, f.offsetY+1);
        }

        
        tg.addFigure(f);
        paintTG();
        dropNext();
        nextMove();   
    }
    
    private synchronized void rotation() {
        if(isGameOver || isPause) return;
        for (int j = 0; j < f.arrX.length; j++) {
            cells[f.arrY[j]+f.offsetY][f.arrX[j]+f.offsetX].setBackground(new Color (55, 55, 55));
            cells[f.arrY[j]+f.offsetY][f.arrX[j]+f.offsetX].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        }
        f.rotationRight();
        if(!tg.isNextMoveValid(f,f.offsetX,f.offsetY)) {
            f.rotationLeft();
        }
        nextMove();
    }
    
    private synchronized void pause() {
        isPause = !isPause;
    }

    private void restart() {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 10; j++) {
                tg.gLines.get(i)[j] = 0;
                cells[i][j].setBackground(new Color (55, 55, 55));
                cells[i][j].setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            }
        } 
        ff.resetCounts();
        isGameOver = false;
        isPause = false;
        fNext = ff.getRandomFigure();
        tt.resetTime();
        mainLayout.time.setText("00:00:00");
        tg.resetStats();
        dropNext();
        nextMove();
    }
    
    private void doHelp() {
        if(helpDialog == null) helpDialog = new HelpDialog(this);
        helpDialog.show();
    }
    
    private void doAbout() {
        if(about == null) 
        	setAboutPanel();
        JOptionPane.showMessageDialog(this,about,"ABOUT", 
                JOptionPane.PLAIN_MESSAGE, 
                new ImageIcon(loadImage("jetris.png")));
    }
    
    private void setAboutPanel() {
        about = new JPanel();
        about.setLayout(new BoxLayout(about, BoxLayout.Y_AXIS));
        JLabel jl = new JLabel("<HTML><B>"+NAME+"</B> Copyright (c) 2006 Nikolay G. Georgiev</HTML>");
        jl.setFont(font);
        about.add(jl);
        about.add(Box.createVerticalStrut(10));
        
        about.add(Box.createVerticalStrut(20));
        
        jl = new JLabel("<HTML>This program is released under the Mozilla Public License 1.1</HTML>");
        jl.setFont(font);
        about.add(jl);
        
        about.add(Box.createVerticalStrut(10));
        about.add(Box.createVerticalStrut(20));
        
        jl = new JLabel("<HTML>This program has been modified by 5 students of the ENSICAEN <BR>for their school project in 2013 : "
        		+ "<BR><BR> Pierre-Louis FURON"
        		+ "<BR> Laurent FERRIC"
        		+ "<BR> Gaëtan LE BARBE"
        		+ "<BR> Robin CARROZZANI"
        		+ "<BR> Guillaume MEUNIER</HTML>");
        jl.setFont(font);
        about.add(jl);
        
    }

    private class MenuHandler implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            try {
                JMenuItem tmp = (JMenuItem) e.getSource();
                if (tmp == jetrisRestart) {
                    restart();
                } else if (tmp == jetrisPause) {
                    pause();
                } else if (tmp == jetrisSetup) {
                    configureButtons();
                } else if (tmp == jetrisExit) {
                    System.exit(0);
                } else if (tmp == helpJetris) {
                    doHelp();
                }else if (tmp == helpAbout) {
                    doAbout();
                }
            } catch (Exception exc) {
                exc.printStackTrace(System.out);
            }
        }
    }

	public boolean isGameOver()
	{
		return isGameOver;
	}

	public Figure getFigure()
	{
		return f;
	}
}
