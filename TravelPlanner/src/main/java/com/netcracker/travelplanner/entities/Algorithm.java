package com.netcracker.travelplanner.entities;

import java.util.*;

public class Algorithm {
    private boolean stopSearch = false;
    private List<Way> ways = new ArrayList<>();

    private void startSearch(List<Edge> edges, String startPoint, String destinationPoint) {
        for (Edge edge : edges) {
            if (startPoint.equals(edge.getStartPoint())) {
                ways.add(new Way(edge, edge.getWeight()));
            }
        }
        while (!stopSearch) {
            int size = ways.size();
            for (int i = 0; i < size; i++) {
                for (Edge edge : edges) {
                    if (ways.get(i).getFoundWay().get(ways.get(i).getFoundWay().size() - 1).getDestinationPoint().equals(edge.getStartPoint())) {
                        List<Edge> tempWay = new LinkedList<>();
                        tempWay.addAll(ways.get(i).getFoundWay());
                        tempWay.add(edge);
                        ways.add(new Way(tempWay, edge.getWeight() + ways.get(i).getWeight()));
                        ways.get(i).setNeedDelete(true);
                    }
                }
            }
            for (int i = 0; i < size; i++) {
                if (ways.get(i).isNeedDelete()) {
                    ways.remove(i);
                    i--;
                }
            }
            stopSearch = true;
            for (Way way : ways) {
                if (!way.getFoundWay().get(way.getFoundWay().size()-1).getDestinationPoint().equals(destinationPoint)){
                    stopSearch = false;
                    break;
                }
            }
        }
    }
    public List<Edge> findWay(List<Edge> edges, String startPoint, String destinationPoint){
        startSearch(edges, startPoint, destinationPoint);
        double minWeight = 1.0/0.0;
        List<Edge> minWay = new LinkedList<>();

        for (Way way : ways) {
            System.out.println(way.toString());
            if (way.getWeight() <  minWeight){
                minWeight = way.getWeight();
                minWay = way.getFoundWay();
            }
        }

        return minWay;
    }
}