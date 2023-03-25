package com.company;

import java.util.ArrayList;

class Graph {
    private final double[][] adjMatrix;

    ArrayList<Vertex> vertices = new ArrayList<>();
    ArrayList<Edge> edges = new ArrayList<>();

    // Initialize the matrix
    public Graph(int numVertices) {
        adjMatrix = new double[numVertices][numVertices];
    }

    // Add Vertex
    // first row and first columns contains vertices other contains edges
    public void addVertex(Vertex v) {
        vertices.add(v);
    }

    // Add edges
    public void addEdge(Edge edge) {
        if (edge.isBidirectional()) {
            adjMatrix[edge.getV1ID()][edge.getV2ID()] = edge.getDistance();
            adjMatrix[edge.getV2ID()][edge.getV1ID()] = edge.getDistance();
        } else {
            adjMatrix[edge.getV1ID()][edge.getV2ID()] = edge.getDistance();
        }
        edges.add(edge);
    }

    public Vertex getVertexWithID(int id) {
        for (Vertex v : vertices) {
            if (v.getVertexId() == id) {
                return v;
            }
        }
        return null;
    }

    public double[][] getAdjMatrix() {
        return adjMatrix;
    }

    // get distance between two vertices
    public double getDistance(int i, int j) {
        return adjMatrix[i][j];
    }
}