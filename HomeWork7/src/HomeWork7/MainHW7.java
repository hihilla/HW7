package HomeWork7;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class MainHW7 {
	
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;

		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}

		return inputReader;
	}
	
	private static Instances loadData(String fileName) throws IOException{
		BufferedReader datafile = readDataFile(fileName);

		Instances data = new Instances(datafile);
		data.setClassIndex(data.numAttributes() - 1);
		return data;
	}

	public static Instances convertImgToInstances(BufferedImage image) {
		Attribute attribute1 = new Attribute("alpha");
		Attribute attribute2 = new Attribute("red");
		Attribute attribute3 = new Attribute("green");
		Attribute attribute4 = new Attribute("blue");
		ArrayList<Attribute> attributes = new ArrayList<Attribute>(4);
		attributes.add(attribute1);
		attributes.add(attribute2);
		attributes.add(attribute3);
		attributes.add(attribute4);
		Instances imageInstances = new Instances("Image", attributes, image.getHeight() * image.getWidth());

		int[][] result = new int[image.getHeight()][image.getWidth()];
		int[][][] resultARGB = new int[image.getHeight()][image.getWidth()][4];

		for (int col = 0; col < image.getWidth(); col++) {
			for (int row = 0; row < image.getHeight(); row++) {
				int pixel = image.getRGB(col, row);

				int alpha = (pixel >> 24) & 0xff;
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				result[row][col] = pixel;
				resultARGB[row][col][0] = alpha;
				resultARGB[row][col][1] = red;
				resultARGB[row][col][2] = green;
				resultARGB[row][col][3] = blue;

				Instance iExample = new DenseInstance(4);
				iExample.setValue((Attribute) attributes.get(0), alpha);// alpha
				iExample.setValue((Attribute) attributes.get(1), red);// red
				iExample.setValue((Attribute) attributes.get(2), green);// green
				iExample.setValue((Attribute) attributes.get(3), blue);// blue
				imageInstances.add(iExample);
			}
		}

		return imageInstances;

	}


	public static BufferedImage convertInstancesToImg(Instances instancesImage, int width, int height) {
		final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int index = 0;
		for (int col = 0; col < width; ++col) {
			for (int row = 0; row < height; ++row) {
				Instance instancePixel = instancesImage.instance(index);
				int pixel = ((int) instancePixel.value(0) << 24) | (int) instancePixel.value(1) << 16
						| (int) instancePixel.value(2) << 8 | (int) instancePixel.value(3);
				image.setRGB(col, row, pixel);
				index++;
			}
		}
		return image;
	}

	public static void main(String[] args) throws Exception {
		kMeansImpl();
		PCAImpl();
	}

	/**
	 * K-means section
	 * @throws IOException
	 */
	private static void kMeansImpl() throws IOException {
		// open messi.jpg and covert it to an instances object.
		BufferedImage messiImage = ImageIO.read(new File("messi.jpg"));
		int height = messiImage.getHeight();
		int width = messiImage.getWidth();
		// convert the image to Instances 
		Instances messiInstances = convertImgToInstances(messiImage);
		int[] possibleK = {2,3,5,10,25,50,100,256};
		for (int k : possibleK) {
			// run the K-Means algorithm on instances 
			KMeans kMeans = new KMeans(k);
			kMeans.buildClusterModel(messiInstances);
			// quantize 
			Instances quantInstances = kMeans.quantize(messiInstances);
			// convert the quantized instances object back to an image
			BufferedImage outputImage = convertInstancesToImg(quantInstances, width, height);
			// save the resulting image
			String fileName = "messi" + k + ".jpg";
			ImageIO.write(outputImage, "jpg", new File(fileName));
			// provide a graphical representation of the total error as a 
			// function of the iteration number
			if (k == 5) {
				// WHAT?!?!?!?!
			}
		}
	}
	
	/**
	 * PCA section
	 * @throws Exception 
	 */
	private static void PCAImpl() throws Exception {
		// load the libras.txt data set.
		Instances librasData = loadData("libras.txt");
		// For each number of principal components
		for (int i = 13; i <= 90; i++) {
			// create a PrincipalComponents object 
			PrincipalComponents pca = new PrincipalComponents();
			// set the number of principal components
			pca.setNumPrinComponents(i);
			pca.setTransformBackToOriginal(true);
			pca.buildEvaluator(librasData);
			Instances transformedData = pca.transformedData(librasData);
			double dist = calcAvgDistance(librasData, transformedData);
			// print this average distance to the console
			System.out.println(dist);
		}	
	}
	
	/**
	 * Calculates the average Euclidean distance between the original 
	 * data set and the transformed data set.
	 * Iterates over all instances in the transformed and original set 
	 * and for each corresponding pair of instances, it should measure 
	 * the Euclidean distance between them and then average over the 
	 * number of instances
	 * @param original
	 * @param transformed
	 * @return The average distance between the instances.
	 */
	private static double calcAvgDistance(Instances original, Instances transformed) {
		double distance = 0;
		for (Instance orig : original) {
			distance += calcAvgDistance(orig, transformed);
		}
		return distance / (double) original.numInstances();
	}
	
	private static double calcEuclidDistance(Instance first, Instance second) {
		double distance = 0;
		for (int i = 0; i < first.numAttributes(); i++) {
			double tmp = first.value(i) - second.value(i);
			distance += Math.pow(tmp, 2);
		}
		return Math.pow(distance, 0.5);
	}
	
	private static double calcAvgDistance(Instance instance, Instances instances) {
		double distance = 0;
		for (Instance other : instances) {
			distance += calcEuclidDistance(instance, other);
		}
		return distance / (double) instances.numInstances();
	}
}

