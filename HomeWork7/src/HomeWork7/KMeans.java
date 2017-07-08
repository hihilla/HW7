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
		initializeCentroids(instances);
		findKMeansCentroids(instances);
	}

	/**
	 * Initialize the centroids by selecting k random instances from the
	 * training set and setting the centroids to be those instances.
	 * 
	 * @param instances
	 */
	public void initializeCentroids(Instances instances) {
		Instances randomInstances = new Instances(instances);
		randomInstances.randomize(new Random());
		this.centroids = new Instances(instances, instances.size());
		this.centroids.clear();

		for (int i = 0; i < this.k; i++) {
			this.centroids.add(randomInstances.instance(i));
		}
	}
	/**
	 * the function returns all array size all instances,
	 * says to which cluster (according to centroids)
	 * each instance belogs to
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
		
		for (int i = 0; i < numOfIterations; i++) {
			//needed function
		}
	}
	
	public void updateIthCentroid(Instances instances, int[] indexesOfCentroids,
									int indexOfCentoid){
		
		Instances cluster = new Instances(instances, instances.size());
		cluster.clear();
		Instance tempCentroid = centroids.get(indexOfCentoid);
		
		// adds all instances that belong to the cluster to the cluster itself
		for (int i = 0; i < instances.numInstances(); i++) {
			if (indexesOfCentroids[i] == indexOfCentoid){
				cluster.add(instances.get(i));
			}
		}
		
		// finds the mean value of every attribute (RGB...)
		// sums all values for each attribute of the instances in 
		// the cluster, dives by the size of cluster and that is
		// the value of the attribute of the new centroid
		for (int i = 0; i < cluster.numAttributes(); i++) {
			int sumOfAllValues = 0;
			for (int j = 0; j < cluster.numInstances(); j++) {
//				tempCentroid.attribute(j).
			}
		}
	}

	/**
	 * Calculate the squared distance between the input instance and the input
	 * centroid
	 * @param dataSetInstance - an instance from the dataset 
	 * @param centroid - a centroid 
	 * @return distance between 2 instances
	 */
	public double calcSquaredDistanceFromCentroid(Instance dataSetInstance, 
													Instance centroid) {
		double distance = 0;
		for (int i = 0; i < dataSetInstance.numAttributes(); i++) {
			double tempDist = dataSetInstance.value(i) - centroid.value(i);
			distance += Math.pow(tempDist, 2);
		}
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
	 * Calculate the average within set sum of squared errors. That is it should 
	 * calculate the average squared distance of every instance from the centroid 
	 * to which it is assigned. This is Tr(Sc) from class, divided by the number 
	 * of instances.  
	 * @param instances
	 * @return double value of the WSSSE. 
	 */
	public double calcAvgWSSSE(Instances instances) {
		double Tr = 0;
		for (Instance instance : instances) {
			int closestCentroidIndex = findClosestCentroid(instance);
			Instance closestCentroid = this.centroids.instance(closestCentroidIndex);
			double squaredDistance = calcSquaredDistanceFromCentroid(instance, closestCentroid);
			Tr += squaredDistance;
		}
		
		return Tr / (double) instances.numInstances();
	}
}