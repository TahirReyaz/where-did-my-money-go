package com.tahir.where_did_my_money_go.stats.service.impl;

import com.tahir.where_did_my_money_go.expense.repository.ExpenseRepository;
import com.tahir.where_did_my_money_go.stats.dto.*;
import com.tahir.where_did_my_money_go.stats.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

        private final ExpenseRepository expenseRepository;

        @Override
        public OverviewStatsDTO getOverview(UUID userId, LocalDate start, LocalDate end) {

                LocalDateTime startDt = start.atStartOfDay();
                LocalDateTime endDt = end.atTime(23, 59, 59);

                BigDecimal total = expenseRepository.getTotalSpending(userId, startDt, endDt);
                Long count = expenseRepository.getTransactionCount(userId, startDt, endDt);
                BigDecimal highest = expenseRepository.getHighestExpense(userId, startDt, endDt);

                BigDecimal avg = (count == 0 || total == null)
                                ? BigDecimal.ZERO
                                : total.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);

                return OverviewStatsDTO.builder()
                                .totalSpending(total != null ? total : BigDecimal.ZERO)
                                .averageDailySpending(avg)
                                .highestExpense(highest != null ? highest : BigDecimal.ZERO)
                                .totalTransactions(count)
                                .build();
        }

        @Override
        public List<TimeSeriesDTO> getTimeSeries(UUID userId, LocalDate start, LocalDate end) {

                return expenseRepository.getDailyTimeSeries(
                                userId,
                                start.atStartOfDay(),
                                end.atTime(23, 59, 59))
                                .stream()
                                .map(obj -> TimeSeriesDTO.builder()
                                                .date((LocalDate) obj[0])
                                                .totalAmount((BigDecimal) obj[1])
                                                .build())
                                .collect(Collectors.toList());
        }

        @Override
        public List<CategoryStatsDTO> getCategoryDistribution(UUID userId, LocalDate start, LocalDate end) {

                List<Object[]> raw = expenseRepository.getCategoryStats(
                                userId,
                                start.atStartOfDay(),
                                end.atTime(23, 59, 59));

                BigDecimal total = raw.stream()
                                .map(r -> (BigDecimal) r[1])
                                .reduce(BigDecimal.ZERO, BigDecimal::add);

                return raw.stream()
                                .map(r -> {
                                        BigDecimal amount = (BigDecimal) r[1];
                                        double percentage = total.compareTo(BigDecimal.ZERO) == 0
                                                        ? 0
                                                        : amount.divide(total, 4, RoundingMode.HALF_UP)
                                                                        .multiply(BigDecimal.valueOf(100))
                                                                        .doubleValue();

                                        return CategoryStatsDTO.builder()
                                                        .categoryName((String) r[0])
                                                        .totalAmount(amount)
                                                        .percentage(percentage)
                                                        .build();
                                })
                                .collect(Collectors.toList());
        }
}