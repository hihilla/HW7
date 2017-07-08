package HomeWork7;

import java.util.Random;

import weka.core.Instance;
import weka.core.Instances;

public class KMeans {
	Instances centroids;
	int k;

	public KMeans(int k) {
	}

//	public KMeans(Instances trainingData, Instances centroids, int k) {
//		this.k = k;
//		this.trainingData = trainingData;
//		this.centroids = centroids;
//	}

	/**
	 * This method is building the KMeans object. It should initialize centroids
	 * (by calling initializeCentroids) and run the K-Means algorithm (which
	 * means to call findKMeansCentroids methods).
	 * 
	 * @param instances
	 */
	public void buildClusterModel(Instances instances) {

	}

	/**
	 * Initialize the centroids by selecting k random instances from the
	 * training set and setting the centroids to be those instances.
	 * 
	 * @param instances
	 */
	public void initializeCentroids(Instances instances, int k) {
		Instances randomInstances = new Instances(instances);
		randomInstances.randomize(new Random());
		this.centroids = new Instances(instances, instances.size());
		this.centroids.clear();

		for (int i = 0; i < k; i++) {
			this.centroids.add(randomInstances.instance(i));
		}
	}
	/**
	 * the function returns all instances that belong to a given
	 * centroid's index.
	 * @param instances
	 * @param indexOFCentroid
	 * @return
	 */
	public int[] findCluster(Instances instances){
		
		int numOfInstances = instances.numInstances();
		int numOfClusters = centroids.numInstances();
		int[] indexesOfCentroids = new int[numOfInstances];
		double minDistance = Double.MAX_VALUE;
		
		// goes through all instances, checks the distances from all centroids
		// and chooses the closest centroid of each instance.
		for (int i = 0; i < numOfInstances; i++) {
			for (int j = 0; j < numOfClusters; j++){
				double tempDistance = calcSquaredDistanceFromCentroid(instances.get(i),
						centroids.get(j));
				if (tempDistance < minDistance){
					minDistance = tempDistance;
					indexesOfCentroids[i] = j;
				}
			}
			// before starting a new instance, the distance is maximal
			minDistance = Double.MAX_VALUE;
		}
		
		return indexesOfCentroids;
	}

	/**
	 * Should find and store the centroids according to the KMeans algorithm.
	 * Your stopping condition for when to stop iterating can be either when the
	 * centroids have not moved much from their previous location, the cost
	 * function did not change much, or you have reached a preset number of
	 * iterations. In this assignment we will only use the preset number option.
	 * A good preset number of iterations is 40. Use one or any combination of
	 * these methods to determine when to stop iterating
	 * 
	 * @param instances
	 */
	public void findKMeansCentroids(Instances instances) {
		int numOfIterations = 40;
		int numOfCentroids = centroids.numInstances();
		double[] lastDistanceCentroids = new double[numOfCentroids];
		
		
		// all previous locations of the centroid...
		for (int i = 0; i < lastDistanceCentroids.length; i++) {
			lastDistanceCentroids[i] = Double.MAX_VALUE;
		}
		
		for (int i = 0; i < numOfIterations; i++){
			if (centroid)
		}
	}

	/**
	 * Input: 2 Instance objects � one is an instance from the dataset and
	 * one is a centroid (if you're using different data structure for the
	 * centroid, feel free to change the input). b. Output: should calculate the
	 * squared distance between the input instance and the input centroid
	 * 
	 * @param dataSetInstance
	 * @param centroid
	 * @return
	 */
	public double calcSquaredDistanceFromCentroid(Instance dataSetInstance, 
			Instance centroid) {

		return 0;
	}

	/**
	 * Input: Instance Output: the index of the closest centroid to the input
	 * instance
	 * 
	 * @param instance
	 * @return
	 */
	public int findClosestCentroid(Instance instance) {
		return 0;
	}

	/**
	 * Output: should replace every instance in Instances by the centroid to
	 * which it is assigned (closest centroid) and return the new Instances
	 * object.
	 * 
	 * @param instances
	 * @return
	 */
	public Instances quantize(Instances instances) {
		return null;
	}

	/**
	 * 	Output: should be the average within set sum of squared errors. 
	 * 	That is it should calculate the average squared distance of every 
	 *  instance from the centroid to which it is assigned. This is Tr(Sc) from class,
	 *  divided by the number of instances. 
	 *  Return the double value of the WSSSE. 
	 * @param instances
	 * @return
	 */
	public double calcAvgWSSSE(Instances instances) {
		return 0;
	}
}