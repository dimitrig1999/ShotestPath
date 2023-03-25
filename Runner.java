package com.company;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


class Runner {
    private Graph graph;
    private Dijkstras dijkstras = new Dijkstras();

    boolean ouputFileOpened = false; // to check if output file opened or not first time

    public void startProcess(String inputFile, String outputFile) {

        try {
            File file = new File(inputFile);
            Scanner scnr = new Scanner(file);

            int numberOfNodes = Integer.parseInt(scnr.nextLine());
            graph = new Graph(numberOfNodes);

            while (scnr.hasNextLine()) {
                String line = scnr.nextLine();

                // reading until found number of edges
                if (!line.contains(",")) {
                    break;
                }

                String[] data = line.split(",");

                int vertexId = Integer.parseInt(data[0].strip());
                double latitude = Double.parseDouble(data[1].strip());
                double longitude = Double.parseDouble(data[2].strip());
                double height = Double.parseDouble(data[3].strip());
                String name = data[4].strip();

                // initializing a new vertex object
                Vertex v = new Vertex(latitude, longitude, height, name, vertexId);
                graph.addVertex(v); // adding a new vertex
            }

            while (scnr.hasNextLine()) {
                String line = scnr.nextLine();

                // reading until found number of cases
                if (!line.contains(" ")) {
                    break;
                }

                String[] data = line.split(" ");

                Vertex v1 = graph.getVertexWithID(Integer.parseInt(data[0]));
                Vertex v2 = graph.getVertexWithID(Integer.parseInt(data[1]));
                boolean bidirectional = Integer.parseInt(data[2]) != 1;

                //calculating the distance
                double edgeDistance = Distance.calculateDistance(v1.getLatitude(), v1.getLongitude(), v2.getLatitude(), v2.getLongitude());

                // initializing a new edge object
                Edge e = new Edge(bidirectional, v1.getVertexId(), v2.getVertexId(), edgeDistance);
                graph.addEdge(e);
            }

            while (scnr.hasNextLine()) {
                String line = scnr.nextLine();

                String[] data = line.split(" ");

                int v1ID = Integer.parseInt(data[0]);
                int v2ID = Integer.parseInt(data[1]);

                dijkstras.dijkstra(graph.getAdjMatrix(), v1ID, v2ID);

                double distance = dijkstras.getDistance();
                ArrayList<Integer> path = dijkstras.getPath();

                output(distance, path, v1ID, v2ID, outputFile);

            }

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    // store the path and the distance in file and display it at console
    private void output(double distance, ArrayList<Integer> path, int start, int end, String outputFilename) {
        try {
            FileWriter myWriter;

            if (!ouputFileOpened) {
                myWriter = new FileWriter(outputFilename);
                ouputFileOpened = true;
            } else
                myWriter = new FileWriter(outputFilename, true);


            System.out.println("Shortest path from " + graph.getVertexWithID(start).getName() + " to " + graph.getVertexWithID(end).getName());
            myWriter.write("Shortest path from " + graph.getVertexWithID(start).getName() + " to " + graph.getVertexWithID(end).getName() + "\n");

            System.out.print("Vertices IDs for shortest path: ");
            myWriter.write("Vertices IDs for shortest path: ");

            for (int i = path.size() - 1; i >= 0; i--) {
                if (i == 0) {
                    System.out.println(path.get(i));
                    myWriter.write(path.get(i) + "\n");
                } else {
                    System.out.print(path.get(i) + " -> ");
                    myWriter.write(path.get(i) + " -> ");
                }
            }

            System.out.print("Path with distances: ");
            myWriter.write("Path with distances: ");

            for (int i = path.size() - 1; i >= 0; i--) {
                if (i == 0) {
                    System.out.println(graph.getVertexWithID(path.get(i)).getName());
                    myWriter.write(graph.getVertexWithID(path.get(i)).getName() + "\n");
                } else {
                    System.out.print(graph.getVertexWithID(path.get(i)).getName() + " - " + graph.getDistance(path.get(i), path.get(i - 1)) + "km -> ");
                    myWriter.write(graph.getVertexWithID(path.get(i)).getName() + " - " + graph.getDistance(path.get(i), path.get(i - 1)) + "km -> ");
                }
            }

            System.out.println("Total Distance : " + distance + "km\n");
            myWriter.write("Total Distance : " + distance + "km\n\n");
            myWriter.close();

        } catch (IOException e) {
            System.out.println("An error occurred while opening output file");
            e.printStackTrace();
        }
    }

}

class Distance {
    static final double _eQuatorialEarthRadius = 6378.1370D;
    static final double _d2r = (Math.PI / 180D);

    public static int calculateDistance(double lat1, double long1, double lat2, double long2) {
        return (int) (1000D * distanceInKM(lat1, long1, lat2, long2));
    }

    public static double distanceInKM(double lat1, double long1, double lat2, double long2) {
        double dlong = (long2 - long1) * _d2r;
        double dlat = (lat2 - lat1) * _d2r;
        double a = Math.pow(Math.sin(dlat / 2D), 2D) + Math.cos(lat1 * _d2r) * Math.cos(lat2 * _d2r)
                * Math.pow(Math.sin(dlong / 2D), 2D);
        double c = 2D * Math.atan2(Math.sqrt(a), Math.sqrt(1D - a));

        return _eQuatorialEarthRadius * c;
    }
}
