package com.tahir.where_did_my_money_go.stats.service;

import com.tahir.where_did_my_money_go.stats.dto.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface StatsService {

    OverviewStatsDTO getOverview(UUID userId, LocalDate start, LocalDate end);

    List<TimeSeriesDTO> getTimeSeries(UUID userId, LocalDate start, LocalDate end);

    List<CategoryStatsDTO> getCategoryDistribution(UUID userId, LocalDate start, LocalDate end);
}