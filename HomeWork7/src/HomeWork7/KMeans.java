package HomeWork7;

import weka.core.Instance;
import weka.core.Instances;

public class KMeans {

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
	public void initializeCentroids(Instances instances) {

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

	}

	/**
	 * a. Input: 2 Instance objects � one is an instance from the dataset and
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
	 * 
	 * @param instances
	 * @return
	 */
	public double calcAvgWSSSE(Instances instances) {
		return 0;
	}
}