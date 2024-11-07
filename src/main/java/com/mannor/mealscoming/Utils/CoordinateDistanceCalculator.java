package com.mannor.mealscoming.Utils;

public class CoordinateDistanceCalculator {
    private static final double EARTH_RADIUS = 6371; // 地球半径，单位：千米

    public static Double calculateDistance_Haversine (double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = EARTH_RADIUS * c;

        return distance;
    }
/*        double lat1 = 40.7128; // 纬度1
        double lon1 = -74.0060; // 经度1
        double lat2 = 34.0522; // 纬度2
        double lon2 = -118.2437; // 经度2*/
//        System.out.println("Distance between the two locations: " + distance + " km");

    public static double calculateDistance_Vincenty (double lat1, double lon1, double lat2, double lon2) {
        double deltaLon = Math.toRadians(lon2 - lon1);

        double numerator = Math.sqrt(Math.pow(Math.cos(Math.toRadians(lat2)) * Math.sin(deltaLon), 2) +
                Math.pow(Math.cos(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) -
                        Math.sin(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(deltaLon), 2));

        double denominator = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(deltaLon);

        double deltaSigma = Math.atan2(numerator, denominator);

        return EARTH_RADIUS * deltaSigma;
    }

}
