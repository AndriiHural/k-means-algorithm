# k-means-algorithm
K-means (MacQueen, 1967) is one of the simplest unsupervised learning algorithms that solve the well known clustering problem.

The Algorithm
  1. Place K points into the space represented by the objects that are being clustered. These points represent initial group centroids.
  2. Assign each object to the group that has the closest centroid.
  3. When all objects have been assigned, recalculate the positions of the K centroids.
  4. Repeat Steps 2 and 3 until the centroids no longer move. This produces a separation of the objects into groups from which the metric to be minimized can be calculated.
  
Input
The first line of the file contains two integers, separated by a tab character. The first one is number of clusters K and the second one is available points N.
The rest of the file contains N rows, each row contains two real numbers separated by a tab character, which are the coordinates of the points on the square.
### Input
| x | y |
| ------ | ------ |
| 2   |4 |
| 1   |0 | 
| 0   |0 | 
| 4   |0 | 
| 4   |1 | 
### Output

| X      | Y    | Amount  |
| ------ |:----:| -------:|
| 0,5    | 0    | 2       |
| 4      | 0,5  | 2       |
