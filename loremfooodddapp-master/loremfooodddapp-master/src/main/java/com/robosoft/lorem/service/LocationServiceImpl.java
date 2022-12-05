package com.robosoft.lorem.service;


import com.robosoft.lorem.routeResponse.Location;
import com.robosoft.lorem.routeResponse.Root;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//Prajwal

@Service
public class LocationServiceImpl implements LocationService{


    @Value("${openRoute.apiKey}")
    String apiKey;

    @Value("${openRoute.driving}")
    String drivingType;


    @Value("${cooking.time}")
    int cookingTime;

    RestTemplate restTemplate;


    public LocationServiceImpl() {
        restTemplate = new RestTemplateBuilder().build();
    }

    public Root getLocationDetails(Location start,Location end) {

        String url = "https://api.openrouteservice.org/v2/directions/"+drivingType+"?api_key="+apiKey+"&start="+start.getLongitude()+","+start.getLatitude()+"&end="+end.getLongitude()+","+end.getLatitude();
        return restTemplate.getForObject(url,Root.class);
    }


    @Override
    public double getDistance(Location start, Location end) {
        Root root  =  this.getLocationDetails(start, end);
        return (root.getFeatures().get(0).getProperties().getSegments().get(0).getDistance())/1000;
    }

    @Override
    public long getDuration(Location start, Location end) {
        Root root  =  this.getLocationDetails(start, end);
        return (long)((root.getFeatures().get(0).getProperties().getSegments().get(0).getDuration())/60)+cookingTime;
    }
}
