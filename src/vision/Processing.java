package vision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

import com.atul.JavaOpenCV.Imshow;

public class Processing {
	
	private static Mat source, img2;
	private static List<MatOfPoint> zones;
	private static Imshow game = new Imshow("Jeu");
	private static Imshow next = new Imshow("Suivants");
	
	private static Mat img2bw (Mat src) {
		Mat img = new Mat();
		Imgproc.cvtColor(src, img, Imgproc.COLOR_BGR2GRAY);
		Imgproc.threshold(img, img, 120, 255, Imgproc.THRESH_BINARY);
		return img;
	}

	public static void getNext () {
		if (zones.size() > 2) {
			MatOfPoint2f zone2 = new MatOfPoint2f();
			Imgproc.approxPolyDP(new MatOfPoint2f(zones.get(2).toArray()), zone2, 4.0, true);
			double[] coins = zone2.get(0, 0);
			int minCol = (int)coins[0];
			int maxCol = minCol;
			int minLig = (int)coins[1];
			int maxLig = minLig;
			for (int i=1 ; i<zone2.rows() ; i++) {
				coins = zone2.get(i, 0);
				if ((int)coins[0] < minCol) {
					minCol = (int)coins[0];
				}
				if ((int)coins[0] > maxCol) {
					maxCol = (int)coins[0];
				}
				if ((int)coins[1] < minLig) {
					minLig = (int)coins[1];
				}
				if ((int)coins[1] > maxLig) {
					maxLig = (int)coins[1];
				}
			}
			Rect roi = new Rect(minCol, minLig, maxCol-minCol, maxLig-minLig);
			Mat src = new Mat(source.clone(), roi);
			Mat img = new Mat(img2.clone(), roi);
			List<MatOfPoint> contours = new Vector<MatOfPoint>();
			Mat hierarchy = new Mat();
			Imgproc.findContours(img, contours, hierarchy, 3, 2);
			Collections.sort(contours, new ComparaisonContour());
			for (int i=0 ; i<contours.size() ; i++) {
				Imgproc.drawContours(src, contours, i, new Scalar(0, 255, 0), 2);
			}
			//TODO next.showImage(src);
		}
	}

	public static List<List<Integer>> getJeu () throws BadWebcamException {
		List<List<Integer>> res = null;
		if (zones.size() > 1) {
			MatOfPoint2f zone2 = new MatOfPoint2f();
			Imgproc.approxPolyDP(new MatOfPoint2f(zones.get(1).toArray()), zone2, 4.0, true);
			double[] coins = zone2.get(0, 0);
			int minCol = (int)coins[0];
			int maxCol = minCol;
			int minLig = (int)coins[1];
			int maxLig = minLig;
			for (int i=1 ; i<zone2.rows() ; i++) {
				coins = zone2.get(i, 0);
				if ((int)coins[0] < minCol) {
					minCol = (int)coins[0];
				}
				if ((int)coins[0] > maxCol) {
					maxCol = (int)coins[0];
				}
				if ((int)coins[1] < minLig) {
					minLig = (int)coins[1];
				}
				if ((int)coins[1] > maxLig) {
					maxLig = (int)coins[1];
				}
			}
			Rect roi = new Rect(minCol, minLig, maxCol-minCol, maxLig-minLig);
			Mat src = new Mat(source.clone(), roi);
			Mat img = new Mat(img2.clone(), roi);
			List<MatOfPoint> contours = new Vector<MatOfPoint>();
			Mat hierarchy = new Mat();
			Imgproc.findContours(img, contours, hierarchy, 3, 2);
			Collections.sort(contours, new ComparaisonContour());
			if (contours.size() > 2) {
				MatOfPoint2f c = new MatOfPoint2f(contours.get(1).toArray());
				MatOfPoint2f c2 = new MatOfPoint2f(contours.get(2).toArray());
				double[] coord = c2.get(1, 0);
				if (coord == null)
					throw new BadWebcamException("Erreur inconnue");
				Point p = new Point(coord[0], coord[1]);
				while (Imgproc.pointPolygonTest(c, p, false) < 0 && contours.size() > 2) {
					contours.remove(2);
					if (contours.size()<3)
						throw new BadWebcamException("Erreur inconnue");
					c2 = new MatOfPoint2f(contours.get(2).toArray());
					coord = c2.get(1, 0);
					if (coord == null)
						throw new BadWebcamException("Erreur inconnue");
					p = new Point(coord[0], coord[1]);
				}
			}
			/*
			for (int i=0 ; i<3 && i<contours.size() ; i++) {
				Imgproc.drawContours(src, contours, i, new Scalar(0, 255, 0), 2);
			}*/
			int pasLig = (maxLig-minLig)/20;
			int pasCol = (maxCol-minCol)/10;
			if (pasLig > 0 && pasCol > 0 && contours.size()>2) {
				res = new ArrayList<List<Integer>>();
				int k=0;
				MatOfPoint2f c = new MatOfPoint2f(contours.get(1).toArray());
				MatOfPoint2f c2 = new MatOfPoint2f(contours.get(2).toArray());
				for (int i=pasLig/2 ; i<20*pasLig ; i+=pasLig) {
					res.add(new ArrayList<Integer>());
					for (int j=pasCol/2 ; j<10*pasCol ; j+=pasCol) {
						Point p = new Point((double)j, (double)i);
						if (Imgproc.pointPolygonTest(c2, p, false) >= 0) {
							double[] pixel = img2.get(i, j);
							if (pixel!=null && pixel.length > 2 && pixel[0]==0.0 && pixel[1]==0.0 && pixel[2]==0.0) {
								res.get(k).add(0);
								//Core.line(src, p, p, new Scalar(255, 255, 255));
								Core.rectangle(src, new Point((double)j-pasCol/2, (double)i-pasLig/2), new Point((double)j+pasCol/2, (double)i+pasLig/2), new Scalar(255,255,255),-1);
							} else {
								res.get(k).add(2);
								//Core.line(src, p, p, new Scalar(255, 101, 101));
								Core.rectangle(src, new Point((double)j-pasCol/2, (double)i-pasLig/2), new Point((double)j+pasCol/2, (double)i+pasLig/2), new Scalar(0,0,255),-1);
							}
						} else if (Imgproc.pointPolygonTest(c, p, false) >= 0) {
							res.get(k).add(0);
							//Core.line(src, p, p, new Scalar(255, 255, 255));
							Core.rectangle(src, new Point((double)j-pasCol/2, (double)i-pasLig/2), new Point((double)j+pasCol/2, (double)i+pasLig/2), new Scalar(255,255,255),-1);
						} else {
							res.get(k).add(1);
							//Core.line(src, p, p, new Scalar(255, 0, 0));
							Core.rectangle(src, new Point((double)j-pasCol/2, (double)i-pasLig/2), new Point((double)j+pasCol/2, (double)i+pasLig/2), new Scalar(255,0,0),-1);
						}
					}
					k++;
				}
			}
			//TODO 
			game.showImage(src);
		}
		return res;
	}

	public static Mat imgContours (Mat src) {
		source = src.clone();
		Mat img = img2bw(src);
		img2 = img.clone();
		List<MatOfPoint> contours = new Vector<MatOfPoint>();
		Mat hierarchy = new Mat();
		Imgproc.findContours(img, contours, hierarchy, 3, 2);
		zones = new Vector<MatOfPoint>();
		MatOfPoint2f poly = new MatOfPoint2f();
		for (int i=0 ; i<contours.size() ; i++) {
			Imgproc.approxPolyDP(new MatOfPoint2f(contours.get(i).toArray()), poly, 4.0, true);
			if (poly.toList().size() == 4) {
				zones.add(contours.get(i));
			}
		}
		Collections.sort(zones, new ComparaisonContour());
		for (int i=0 ; i<3 && i<zones.size() ; i++) {
			Imgproc.drawContours(src, zones, i, new Scalar(0, 255, 0), 2);
		}
		return src;
	}
}
