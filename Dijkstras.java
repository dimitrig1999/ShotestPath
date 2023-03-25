package com.company;

import java.util.ArrayList;

class Dijkstras {
    private double distance = 0;
    private ArrayList<Integer> path;

    public void dijkstra(double[][] adjacencyMatrix, int startVertex, int endVertex) {
        int nVertices = adjacencyMatrix[0].length;
        double[] shortestDistances = new double[nVertices];
        boolean[] added = new boolean[nVertices];

        for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }

        shortestDistances[startVertex] = 0;
        int[] parents = new int[nVertices];
        parents[startVertex] = -1; // means no parent

        for (int i = 1; i < nVertices; i++) {
            int nearestVertex = -1;
            double shortestDistance = Integer.MAX_VALUE; // infinite initialization

            for (int vertexIndex = 0; vertexIndex < nVertices; vertexIndex++) {
                if (!added[vertexIndex] && shortestDistances[vertexIndex] < shortestDistance) {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }

            added[nearestVertex] = true;

            for (int vertexIndex = 0;
                 vertexIndex < nVertices;
                 vertexIndex++) {
                double edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];

                if (edgeDistance > 0 && ((shortestDistance + edgeDistance) < shortestDistances[vertexIndex])) {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance + edgeDistance;
                }
            }
        }

        path = new ArrayList<>();
        generatePath(endVertex, parents);

        distance = shortestDistances[endVertex];
    }

    private void generatePath(int currentVertex, int[] parents) {
        // -1 means no parent
        if (currentVertex == -1) {
            return;
        }
        path.add(currentVertex);
        generatePath(parents[currentVertex], parents);
    }

    public double getDistance() {
        return distance;
    }

    public ArrayList<Integer> getPath() {
        return path;
    }
}
