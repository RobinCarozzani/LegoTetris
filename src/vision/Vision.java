package vision;

import java.util.List;

import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;

import com.atul.JavaOpenCV.Imshow;

public class Vision {
	private Mat img;
	private VideoCapture cap;
	
	public Vision() throws NoWebCamException
	{
		System.loadLibrary("opencv_java247");
        cap = new VideoCapture(0);
        if(!cap.isOpened())
        	throw new NoWebCamException();
        img = new Mat();
        for (int i = 0; i<5; i++)
        	cap.read(img);
	}

	public List<List<Integer>> getJeu() throws BadWebcamException
	{
		if (cap.read(img))
		{
			img = Processing.imgContours(img);

			List<List<Integer>> etatJeu = null;
			etatJeu = Processing.getJeu();
			/*{
				Imshow im = new Imshow("Detection");
				im.showImage(img);
			 }*/
			if (etatJeu != null)
			{
				return etatJeu;
			}
			throw new BadWebcamException("Image lue non exploitable");
		}
		throw new BadWebcamException("Pas d'image lue");
	}
	
	public static void show () throws NoWebCamException {
		System.loadLibrary("opencv_java247");
        VideoCapture cap = new VideoCapture(1);
        if(!cap.isOpened())
        	throw new NoWebCamException();
        Imshow im = new Imshow("Detection");
        Mat img = new Mat();
        while (true) {
        	if (cap.read(img)) {
        		img = Processing.imgContours(img);
        		im.showImage(img);
        		
        		List<List<Integer>> etatJeu = null;
				try
				{
					etatJeu = Processing.getJeu();
				} catch (BadWebcamException e)
				{}
        		/*if (etatJeu != null) {
        			for (int i=0 ; i<etatJeu.size() ; i++) {
        				for (int j=0 ; j<etatJeu.get(i).size() ; j++) {
        					System.out.print(etatJeu.get(i).get(j));
        				}
        				System.out.println();
        			}
        			System.out.println();
        		}*/
        	}
        }
	}
}
