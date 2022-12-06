package com.robosoft.lorem.service;

import com.robosoft.lorem.routeResponse.Location;

public interface LocationService {



    //get distance required to travel from start to end
    double getDistance(Location start,Location end);


    //get time required to travel from start to end
    long getDuration(Location start,Location end);
}
