package HomeWork7;

import java.util.Random;

import weka.core.EuclideanDistance;
import weka.core.Instance;
import weka.core.Instances;

public class KMeans {
	Instances centroids;

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
	 * Calculate the squared distance between the input instance and the input
	 * centroid
	 * 
	 * @param dataSetInstance - an instance from the dataset 
	 * @param centroid - a centroid 
	 * @return distance between 2 instances
	 */
	public double calcSquaredDistanceFromCentroid(Instance dataSetInstance, 
													Instance centroid) {
		double distance = new EuclideanDistance().distance(dataSetInstance, 
															centroid);
		return distance;
	}

	/**
	 * Finds the index of the closest centroid to the input instance
	 * 
	 * @param instance
	 * @return index of closest centroid
	 */
	public int findClosestCentroid(Instance instance) {
		int closestCentroidIndex = 0;
		double closestDistance = calcSquaredDistanceFromCentroid(instance, this.centroids.instance(0));
		for (int i = 1; i < this.centroids.numInstances(); i++) {
			double curDistance = calcSquaredDistanceFromCentroid(instance, this.centroids.instance(i));
			if (closestDistance > curDistance) {
				closestDistance = curDistance;
				closestCentroidIndex = i;
			}
		}
		return closestCentroidIndex;
	}

	/**
	 * Replace every instance in Instances by the closest centroid
	 * 
	 * @param instances
	 * @return new set of closest centroids Instances 
	 */
	public Instances quantize(Instances instances) {
		Instances closestCentroids = new Instances(instances, instances.numInstances());
		closestCentroids.clear();
		for (Instance instance : instances) {
			int closestCentroidIndex = findClosestCentroid(instance);
			closestCentroids.add(this.centroids.instance(closestCentroidIndex));
		}
		return closestCentroids;
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