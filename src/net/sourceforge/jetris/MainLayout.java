package net.sourceforge.jetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class MainLayout extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	protected JPanel[][] next;
	protected JLabel score;
	protected JLabel levelLabel;
	protected JLabel lines;
	protected JLabel time;
	protected Font font;
	protected Font testfont;
	
	public MainLayout (JPanel playPannel) {
		super();
		
		font = new Font("", Font.BOLD, 15);
		testfont = new Font("", Font.BOLD, 20);
		
		this.setSize(400,300);
		this.setBackground(Color.DARK_GRAY/*black*/);
		
		JPanel cell1 = new JPanel();
		
		JPanel cell14 = new JPanel();
		JPanel cell15 = new JPanel();
		JPanel cell17 = new JPanel();
		JPanel cell18 = new JPanel();
		JPanel cell19 = new JPanel();
		JPanel cell21 = new JPanel();
		JPanel cell22 = new JPanel();
		JPanel cell23 = new JPanel();
		JPanel cell24 = getNextTetromino();
		
		JPanel cell25 = new JPanel();
		JPanel cell26 = new JPanel();
		JPanel cell27 = new JPanel();
		JPanel cell28 = new JPanel();
		JPanel cell29 = new JPanel();
		JPanel cell30 = getScore();

		JPanel cell31 = new JPanel();
		JPanel cell32 = new JPanel();
		JPanel cell33 = new JPanel();
		JPanel cell34 = new JPanel();
		JPanel cell35 = new JPanel();
		JPanel cell36 = getLevel();
		
		JPanel cell37 = new JPanel();
		JPanel cell38 = new JPanel();
		JPanel cell39 = new JPanel();
		JPanel cell40 = new JPanel();
		JPanel cell41 = new JPanel();
		JPanel cell42 = getLines();
		
		JPanel cell43 = new JPanel();
		JPanel cell44 = new JPanel();
		JPanel cell45 = new JPanel();
		JPanel cell46 = getTime();
		JPanel cell47 = new JPanel();
		
		JPanel cell48 = getHelpPanel();
		JPanel cell49 = new JPanel();
		JPanel cell50 = new JPanel();
		JPanel cell51 = new JPanel();
		
		
		
		Color c = new Color(55, 55, 55);
		
		cell1.setBackground(Color.DARK_GRAY);
		
		cell14.setBackground(Color.DARK_GRAY);
		cell15.setBackground(Color.white);

		cell17.setBackground(Color.white);
		cell18.setBackground(Color.DARK_GRAY);
		cell19.setBackground(Color.white);
		cell21.setBackground(Color.white);
		cell22.setBackground(Color.DARK_GRAY);
		cell23.setBackground(Color.white);
		cell24.setBackground(c);
		cell25.setBackground(Color.white);
		cell26.setBackground(Color.white);
		cell27.setBackground(Color.DARK_GRAY);
		cell28.setBackground(Color.gray);
		cell29.setBackground(Color.gray);
		cell30.setBackground(c);
		cell31.setBackground(Color.gray);
		cell32.setBackground(Color.gray);
		cell33.setBackground(Color.DARK_GRAY);
		cell34.setBackground(Color.gray);
		cell35.setBackground(Color.gray);
		cell36.setBackground(c);
		cell37.setBackground(Color.gray);
		cell38.setBackground(Color.gray);
		cell39.setBackground(Color.DARK_GRAY);
		cell40.setBackground(Color.gray);
		cell41.setBackground(Color.gray);
		cell42.setBackground(c);
		cell43.setBackground(Color.gray);
		cell44.setBackground(Color.gray);
		cell45.setBackground(Color.DARK_GRAY);
		cell46.setBackground(c);
		cell46.setBorder(BorderFactory.createLineBorder(Color.gray, 10));
		cell47.setBackground(Color.DARK_GRAY);
		cell48.setBackground(Color.DARK_GRAY);
		cell49.setBackground(Color.white);
		cell50.setBackground(Color.DARK_GRAY);
		cell51.setBackground(Color.DARK_GRAY);
		
		
		cell1.setPreferredSize(new Dimension (20, 20));
		cell14.setPreferredSize(new Dimension (20, 300));
		cell15.setPreferredSize(new Dimension (220, 10));
		
		cell18.setPreferredSize(new Dimension (20, 100));
		cell19.setPreferredSize(new Dimension (10, 400));
		cell21.setPreferredSize(new Dimension (10, 400));
		
		cell22.setPreferredSize(new Dimension (400, 100));
		cell24.setPreferredSize(new Dimension (400, 130));
		cell27.setPreferredSize(new Dimension (10, 20));
		cell30.setPreferredSize(new Dimension (150, 35));
		cell33.setPreferredSize(new Dimension (10, 20));

		cell36.setPreferredSize(new Dimension (150, 35));
		
		cell39.setPreferredSize(new Dimension (10, 20));

		cell42.setPreferredSize(new Dimension (150, 35));
		cell45.setPreferredSize(new Dimension (150, 20));
		cell46.setPreferredSize(new Dimension (150, 55));
		cell47.setPreferredSize(new Dimension (150, 75));
		cell51.setPreferredSize(new Dimension (10, 20));
		
				
		//************************  ************************
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		//************************ Positionnement des composants ************************
		
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = GridBagConstraints.REMAINDER; /* 13 de largeur */
		this.add(cell1, gbc);
		
		//******************************************
		
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 21;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.VERTICAL;
		this.add(cell14, gbc);
		
		gbc.gridx = 1;
		gbc.gridheight = 1;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(cell15, gbc);
		
		gbc.gridx = 4;
		gbc.gridheight = 20;
		gbc.gridwidth = 5;
		gbc.fill = GridBagConstraints.VERTICAL;
		this.add(cell22, gbc);
		
		gbc.gridx = 9;
		gbc.gridheight = 1;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(cell17, gbc);
		
		gbc.gridx = 12;
		gbc.gridheight = 21;
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		this.add(cell18, gbc);
		
		//******************************************	
		
		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridheight = 18;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.VERTICAL;
		this.add(cell19, gbc);	
		
		gbc.gridx = 2;
		gbc.fill = GridBagConstraints.NONE;
		this.add(playPannel, gbc);
		
		gbc.gridx = 3;
		gbc.fill = GridBagConstraints.VERTICAL;
		this.add(cell21, gbc);
		
		gbc.gridx = 9;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.VERTICAL;
		this.add(cell23, gbc);
		
		gbc.gridx = 10;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		this.add(cell24, gbc);		
		
		gbc.gridx = 11;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.VERTICAL;
		this.add(cell25, gbc);
		
		gbc.gridy = 3;
		gbc.gridx = 9;
		gbc.gridheight = 1;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(cell26, gbc);
		
		gbc.gridy = 4;
		this.add(cell27, gbc);	
		
		gbc.gridy = 5;
		this.add(cell28, gbc);	
		
		gbc.gridy = 6;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.fill = GridBagConstraints.VERTICAL;
		this.add(cell29, gbc);	
		
		gbc.gridx ++;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(cell30, gbc);	
		
		gbc.gridx ++;
		gbc.fill = GridBagConstraints.VERTICAL;
		this.add(cell31, gbc);	
		
		gbc.gridx -=2;
		gbc.gridy = 7;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(cell32, gbc);	
		
		gbc.gridy = 8;
		this.add(cell33, gbc);	
		
		gbc.gridy = 9;
		this.add(cell34, gbc);	
		
		gbc.gridy = 10;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.VERTICAL;
		this.add(cell35, gbc);	
		
		gbc.gridx++;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(cell36, gbc);	

		gbc.gridx++;
		gbc.fill = GridBagConstraints.VERTICAL;
		this.add(cell37, gbc);	
		
		gbc.gridx -=2;
		gbc.gridy = 11;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(cell38, gbc);	
		
		gbc.gridy = 12;
		this.add(cell39, gbc);	
		
		gbc.gridy = 13;
		this.add(cell40, gbc);
		
		gbc.gridy = 14;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.VERTICAL;
		this.add(cell41, gbc);
		
		gbc.gridx++;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(cell42, gbc);
		
		gbc.gridx++;
		gbc.fill = GridBagConstraints.VERTICAL;
		this.add(cell43, gbc);
		
		gbc.gridx -= 2;
		gbc.gridy = 15;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(cell44, gbc);	
		
		gbc.gridy = 16;
		this.add(cell45, gbc);	
		
		gbc.gridy = 17;
		this.add(cell46, gbc);	
		
		gbc.gridy = 18;
		gbc.fill = GridBagConstraints.BOTH;
		this.add(cell47, gbc);	
		
		gbc.gridy = 19;
		this.add(cell48, gbc);	
		
		gbc.gridy++;
		gbc.gridx = 1;
		gbc.gridwidth = 3;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		this.add(cell49, gbc);
		
		gbc.gridx = 9;
		gbc.gridwidth = 4;
		this.add(cell50, gbc);
		
		gbc.gridy++;
		gbc.gridx = 1;
		gbc.gridwidth = 11;
		this.add(cell51, gbc);
		
	}
	
	private JPanel getNextTetromino() {
		JPanel r = new JPanel();
		
        BoxLayout rL = new BoxLayout(r,BoxLayout.Y_AXIS);
        JPanel ajout = new JPanel();
        ajout.setBackground(new Color (55, 55, 55));
        r.setLayout(rL);
        r.add(ajout);
        /*r.setBorder(new EtchedBorder());*/	/* m�thode int�r�ssante, donc � retenir */
        Dimension ra = new Dimension(5, 0);
        next = new JPanel[4][4];
        JPanel nextP = new JPanel();
        nextP.setLayout(new GridLayout(4,4));
        Dimension d = new Dimension(5*18, 5*18);
        nextP.setMinimumSize(d);
        nextP.setPreferredSize(d);
        nextP.setMaximumSize(d);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                next[i][j] = new JPanel();
                nextP.add(next[i][j]);
            }
        }
        
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(ra));
        jp.add(Box.createHorizontalGlue());
        r.add(jp);
        nextP.setBorder(BorderFactory.createLineBorder(new Color (55, 55, 55), 1));
        r.add(nextP);
        
        r.add(Box.createRigidArea(new Dimension(100, 10)));
		
		return r;
	}
	
	private JPanel getScore (){
		JPanel jp = new JPanel();
        JLabel titre = null;
        
        score = new JLabel("0");
        score.setForeground(Color.white);    
        
        titre = new JLabel("SCORE : ");
        titre.setForeground(Color.white);        
        
        titre.setFont(testfont);
        score.setFont(testfont);
        jp.add(titre);
        jp.add(score);

		return jp;
	}
	
	private JPanel getLevel (){
		JPanel jp = new JPanel();
        JLabel titre = null;
        
        levelLabel = new JLabel("1");
        levelLabel.setForeground(Color.white);
        
        titre = new JLabel ("LEVEL : ");
        titre.setForeground(Color.white);
        titre.setFont(testfont);
        levelLabel.setFont(testfont);
        jp.add(titre);
        jp.add(levelLabel);
        
		return jp;
	}
	
	private JPanel getLines (){
		JPanel jp = new JPanel();
        JLabel titre = null;
        
        lines = new JLabel("0");
        lines.setForeground(Color.white);

        titre = new JLabel ("LINES : ");
        titre.setForeground(Color.white);
        titre.setFont(testfont);
        lines.setFont(testfont);
        jp.add(titre);
        jp.add(lines);
        
		return jp;
	}
	
	private JPanel getTime (){
		JPanel jp = new JPanel();
        JLabel titre = null;
        
        time = new JLabel("00:00:00");
        time.setForeground(Color.white);
        
        titre = new JLabel ("TIME : ");
        titre.setForeground(Color.white);
        titre.setFont(testfont);
        time.setFont(testfont);
        jp.add(titre);
        jp.add(time);
        return jp;
	}
	
	private JPanel getHelpPanel() {
		JPanel r = new JPanel();
        BoxLayout rL = new BoxLayout(r,BoxLayout.Y_AXIS);
        r.setLayout(rL);
        
        r.add(addHelpPanel("Default inputs : "));
		r.add(addHelpPanel("O or \u2190 - Left\n"));
        r.add(addHelpPanel("T or \u2192 - Right"));
        r.add(addHelpPanel("V or \u2191 - Rotate\n"));
        r.add(addHelpPanel("\u2193 - Down\n"));
        r.add(addHelpPanel("; or Space - Drop\n"));
        r.add(addHelpPanel("P - Pause\n"));
        r.add(addHelpPanel("R - Restart"));
        return r;
	}
    
    private JPanel addHelpPanel(String help) {
        JPanel jp = new JPanel();
        jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
        jp.add(Box.createRigidArea(new Dimension(150,20)));
        JLabel jL = new JLabel(help);
        jL.setFont(font);
        jL.setForeground(Color.GRAY);
        jp.add(jL);
        jp.add(Box.createHorizontalGlue());

        jp.setBackground(Color.DARK_GRAY);
        return jp;
    }
}





















