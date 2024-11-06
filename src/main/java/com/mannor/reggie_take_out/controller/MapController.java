package com.mannor.reggie_take_out.controller;

import com.mannor.reggie_take_out.Utils.CoordinateDistanceCalculator;
import com.mannor.reggie_take_out.common.R;
import org.springframework.web.bind.annotation.*;

@RestController
public class MapController {

    @PostMapping("/getDistence")
    public R<Double> getDistence( double lat1, double lon1, double lat2, double lon2){

        Double distance = CoordinateDistanceCalculator.calculateDistance_Vincenty(lat1, lon1, lat2, lon2);
    /*  double lat1 = 40.7128; // 纬度1
        double lon1 = -74.0060; // 经度1
        double lat2 = 34.0522; // 纬度2
        double lon2 = -118.2437; // 经度2*/
        return R.success(distance);
    }

}
