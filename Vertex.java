package com.company;

class Vertex {
    private final double latitude;
    private final double longitude;
    private final double height;
    private final String name;
    private final int vertexId;

    public Vertex(double latitude, double longitude, double height, String name, int vertexId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.height = height;
        this.name = name;
        this.vertexId = vertexId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public int getVertexId() {
        return vertexId;
    }
}
