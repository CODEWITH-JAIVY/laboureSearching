package com.labourseSearching.Service.Geo;

import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RedisGeoServiceImpl implements RedisGeoService {

    private final StringRedisTemplate redisTemplate;

    public RedisGeoServiceImpl(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // ---------- SAVE (ONLINE LABOUR) ----------
    @Override
    public void save(String key, Long id, double lat, double lng) {
        redisTemplate.opsForGeo().add(
                key,
                new Point(lng, lat),
                id.toString()
        );
    }

    // ---------- REMOVE (OFFLINE LABOUR) ----------
    @Override
    public void remove(String key, Long id) {
        redisTemplate.opsForZSet().remove(key, id.toString());
    }

    // ---------- PAGINATED NEARBY SEARCH ----------
    @Override
    public List<NearbyResult> findNearby(
            String key,
            double lat,
            double lng,
            double radiusKm,
            int page,
            int size
    ) {
        if (page < 0 || size <= 0) {
            return List.of();
        }

        // 🔥 Redis GEO pagination trick
        int limit = (page + 1) * size;

        GeoResults<RedisGeoCommands.GeoLocation<String>> results =
                redisTemplate.opsForGeo().radius(
                        key,
                        new Circle(
                                new Point(lng, lat),
                                new Distance(radiusKm, Metrics.KILOMETERS)
                        ),
                        RedisGeoCommands.GeoRadiusCommandArgs
                                .newGeoRadiusArgs()
                                .includeDistance()
                                .sortAscending()
                                .limit(limit)
                );

        if (results == null || results.getContent().isEmpty()) {
            return List.of();
        }

        int from = page * size;
        int to = Math.min(from + size, results.getContent().size());

        if (from >= to) {
            return List.of();
        }

        return results.getContent()
                .subList(from, to)
                .stream()
                .map(r -> new NearbyResult(
                        Long.parseLong(r.getContent().getName()),
                        r.getDistance().getValue()
                ))
                .toList();
    }
}