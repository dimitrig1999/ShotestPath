package com.company;

class Edge {
    private final boolean bidirectional;
    private final int v1ID; // vertex 1 ID
    private final int v2ID; // vertex 2 ID
    private final double distance;

    public Edge(boolean bidirectional, int v1, int v2, double distance) {
        this.bidirectional = bidirectional;
        this.v1ID = v1;
        this.v2ID = v2;
        this.distance = distance;
    }

    public boolean isBidirectional() {
        return bidirectional;
    }

    public int getV1ID() {
        return v1ID;
    }

    public int getV2ID() {
        return v2ID;
    }

    public double getDistance() {
        return distance;
    }
}