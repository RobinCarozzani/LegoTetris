package net.sourceforge.jetris;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;

public class TetrisGrid implements Serializable{
    
	private static final long serialVersionUID = 8818986785763755443L;

	static final String DAT_FILE = "JETRIS.DAT";
    
    LinkedList<int[]> gLines;
    private int lines;
    private int score;
    private int[] dropLines;
    private int level;
    
    TetrisGrid() {
        gLines = new LinkedList<int[]>();
        for (int i = 0; i < 20; i++) {
            gLines.add(new int[10]);
        }
        lines = score = 0;
        dropLines = new int[4];
    }
    
    boolean addFigure(Figure f) {
        for (int j = 0; j < f.arrX.length; j++) {
            if(f.arrY[j]+f.offsetY >= 20) {
                f.setOffset(f.offsetXLast,f.offsetYLast);
                addFiguretoGrid(f);
                eliminateLines();
                return true;
            }
            if(gLines.get(f.arrY[j]+f.offsetY)[f.arrX[j]+f.offsetX] != 0) {
                f.setOffset(f.offsetXLast,f.offsetYLast);
                addFiguretoGrid(f);
                eliminateLines();
                return true;
            }
        }
        return false;
    }
    
    boolean isNextMoveValid(Figure f, int xOffset, int yOffset) {
        boolean b = true;
        try {
            for (int j = 0; j < f.arrX.length; j++) {
                if(gLines.get(f.arrY[j]+yOffset)[f.arrX[j]+xOffset] != 0) {
                    b = false;
                } 
            }
            return b;
        } catch (Exception e) {
            return false;
        }
    }
    
    private void addFiguretoGrid(Figure f) {
        for (int j = 0; j < f.arrX.length; j++) {
            gLines.get(f.arrY[j]+f.offsetY)[f.arrX[j]+f.offsetX] = f.getGridVal();
        }
    }
    
    private void eliminateLines() {
        int lines = 0;
        for (Iterator<int[]> iter = gLines.iterator(); iter.hasNext();) {
            int[] el = (int[]) iter.next();
            boolean isFull = true;
            for (int j = 0; j < 10; j++) {
                if(el[j]==0) isFull = false;
            }
            if(isFull) {
                iter.remove();
                lines++;
            }
        }

        switch (lines) {
        case 1: score +=  100 +  5*level; break;
        case 2: score +=  400 + 20*level; break;
        case 3: score +=  900 + 45*level; break;
        case 4: score += 1600 + 80*level; break;
        }
        
        this.lines += lines;
        
        level = this.lines / 10;
        //level = 20;
        if(level > 20) level = 20;
        
        if (lines > 0) {
            dropLines[lines-1]++;
        }

        for (int i = 0; i < lines; i++) {
            gLines.add(0,new int[10]);
        }
    }
    
    boolean isGameOver(Figure f) {
        
        return !isNextMoveValid(f, 4, 0);
    }
    
    int getLevel() { return level;}
    
    int getLines() { return lines;}
    
    int getScore() { return score;}
    
    int[] getDropLines() { return dropLines; }
    
    void resetStats() {
        lines = score = level = 0;
        for (int i = 0; i < dropLines.length; i++) {
            dropLines[i] = 0;
        }
    }
    
    public String toString() {
        
        StringBuffer sb = new StringBuffer();
        for (int[] arr : gLines) {
            for (int j = 0; j < arr.length; j++) {
                sb.append(arr[j]);
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
