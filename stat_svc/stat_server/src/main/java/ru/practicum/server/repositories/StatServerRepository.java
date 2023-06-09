package ru.practicum.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.server.models.EndpointHit;
import ru.practicum.server.models.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatServerRepository extends JpaRepository<EndpointHit, Long> {
    @Query(value = "select new ru.practicum.server.models.ViewStats(h.app, h.uri, count(h.ip)) " +
            "from  EndpointHit h " +
            "where h.timestamp between :start and :end " +
            "and h.uri in (:uris) " +
            "group by h.app, h.uri " +
            "order by count(distinct h.ip) desc")
    List<ViewStats> getStatsByUrisAndIp(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("uris") List<String> uris);

    @Query(value = "select new ru.practicum.server.models.ViewStats(h.app, h.uri, count(h.ip)) " +
            "from  EndpointHit h " +
            "where h.timestamp between :start and :end " +
            "and h.uri in (:uris) " +
            "group by h.app, h.uri " +
            "order by count(h.ip) desc")
    List<ViewStats> getStatsByUris(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, @Param("uris") List<String> uris);
}