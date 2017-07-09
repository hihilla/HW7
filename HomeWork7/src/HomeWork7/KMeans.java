package HomeWork7;

import java.util.Random;

import weka.core.Instance;
import weka.core.Instances;

public class KMeans {
	Instances centroids;
	int k;
	int[] clusters;

	public KMeans(int k) {
		this.k = k;
	}

	/**
	 * This method is building the KMeans object. It should initialize centroids
	 * (by calling initializeCentroids) and run the K-Means algorithm (which
	 * means to call findKMeansCentroids methods).
	 * 
	 * @param instances
	 */
	public void buildClusterModel(Instances instances) {
		initializeCentroids(instances);
		findKMeansCentroids(instances);
	}

	/**
	 * Initialize the centroids by selecting k random instances from the
	 * training set and setting the centroids to be those instances.
	 * 
	 * @param instances
	 */
	private void initializeCentroids(Instances instances) {
		Instances randInstances = new Instances(instances);
		randInstances.randomize(new Random());
		this.centroids = new Instances(instances, this.k);
		
		for (int i = 0; i < this.k; i++) {
			this.centroids.add(randInstances.instance(i));
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
	private void findKMeansCentroids(Instances instances) {
		boolean costFuncChanged = false;
		double prevError = Integer.MAX_VALUE;
		double EPSILON = 0.003;
		for (int i = 0; i < 40 && !costFuncChanged; i++) {
			// cluster the points into k clusters.
			assignClusters(instances);
			
			// Recalculate the new cluster center
			reSetCentroids(instances);
			
			// calculate the new error and stop if the cost 
			// function did not change much
			double newError = calcAvgWSSSE(instances);
			if (this.k == 5) {
				System.out.println("iteration: " + i 
									+ " error: " + newError);
			}
			if (Math.abs(prevError - newError) < EPSILON) {
				costFuncChanged = true;
			}
			prevError = newError;
		}
	}

	/**
	 * for every instances to what
	 * cluster in belongs to (according to centroids)
	 * @param instances
	 */
	private void assignClusters(Instances instances){
		int[] clusters = new int[instances.numInstances()];
		for (int i = 0; i < instances.numInstances(); i++) {
			Instance curInstance = instances.instance(i);
			int closestCentroid = findClosestCentroid(curInstance);
			clusters[i] = closestCentroid;
		}
		this.clusters = clusters;
	}
	
	/**
	 * with the given centroid, checks all instances in that cluster
	 * and updates the centroid to be the center of the cluster
	 * @param instances
	 */
	private void reSetCentroids(Instances instances) {
		for (int centroid = 0; centroid < this.k; centroid++) {
			// calculate centroids new values
			double[] avgAttValues = calcCentroidValues(instances, centroid);
			Instance curCentroid = this.centroids.instance(centroid);
			// set centroids new values
			for (int i = 0; i < avgAttValues.length; i++) {
				curCentroid.setValue(curCentroid.attribute(i), avgAttValues[i]);
			}
		}
	}
	
	/**
	 * @param instances
	 * @param clusters
	 * @param centroid
	 */
	private double[] calcCentroidValues(Instances instances, int centroid) {
		int numInstInCluster = 0;
		for (int i = 0; i < clusters.length; i++) {
			if (clusters[i] == centroid) {
				numInstInCluster++;
			}
		}
		double[] sumAttributes = new double[instances.numAttributes()];
		for (int inst = 0; inst < instances.numInstances(); inst++) {
			if (clusters[inst] == centroid) {
				for (int att = 0; att < sumAttributes.length; att++) {
					sumAttributes[att] += instances.instance(inst).value(att);
				}
			}
		}
		double[] avgAttValues = new double[instances.numAttributes()];
		for (int att = 0; att < sumAttributes.length; att++) {
			avgAttValues[att] = sumAttributes[att] / (double) numInstInCluster;
		}
		return avgAttValues;
	}

	/**
	 * Calculate the squared distance between the input instance and the input
	 * centroid
	 * @param dataSetInstance - an instance from the dataset 
	 * @param centroid - a centroid 
	 * @return distance between 2 instances
	 */
	private double calcSquaredDistanceFromCentroid(Instance dataSetInstance, 
													Instance centroid) {
		double sqDistance = 0;
		for (int i = 0; i < dataSetInstance.numAttributes(); i++) {
			sqDistance += Math.pow((dataSetInstance.value(i) - centroid.value(i)), 2);
		}
		return sqDistance;
	}

	/**
	 * Finds the index of the closest centroid to the input instance
	 * 
	 * @param instance
	 * @return index of closest centroid
	 */
	private int findClosestCentroid(Instance instance) {
		double closestDistance = calcSquaredDistanceFromCentroid(instance, this.centroids.instance(0));
		int closestCentroid = 0;
		for (int i = 1; i < this.centroids.numInstances(); i++) {
			double curDistance = calcSquaredDistanceFromCentroid(instance, this.centroids.instance(i));
			if (curDistance < closestDistance) {
				closestDistance = curDistance;
				closestCentroid = i;
			}
		}
		return closestCentroid;
	}

	/**
	 * Replace every instance in Instances by the closest centroid
	 * 
	 * @param instances
	 * @return new set of closest centroids Instances 
	 */
	public Instances quantize(Instances instances) {
		Instances replacedInst = new Instances (instances, instances.numInstances());
		for (int i = 0; i < instances.numInstances(); i++) {
			int centroid = this.clusters[i];
			replacedInst.add(this.centroids.instance(centroid));
		}
		return replacedInst;
	}

	/**
	 * Calculate the average within set sum of squared errors. That is it should 
	 * calculate the average squared distance of every instance from the centroid 
	 * to which it is assigned. This is Tr(Sc) from class, divided by the number 
	 * of instances.  
	 * @param instances
	 * @return double value of the WSSSE. 
	 */
	private double calcAvgWSSSE(Instances instances) {
		double Tr = 0;
		for (int i = 0; i < this.clusters.length; i++) {
			Instance instance = instances.instance(i);
			Instance centroid = this.centroids.instance(clusters[i]);
			double squaredDistance = calcSquaredDistanceFromCentroid(instance, centroid);
			Tr += squaredDistance;
		}
		return Tr / (double) instances.numInstances();
	}
}