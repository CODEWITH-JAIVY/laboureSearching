package com.labourseSearching.Service.Geo;

import java.util.List;

public interface RedisGeoService {

    void save(String key, Long id, double lat, double lng);

    void remove(String key, Long id);

    List<NearbyResult> findNearby(
            String key,
            double lat,
            double lng,
            double radiusKm,
            int page,
            int size
    );
}