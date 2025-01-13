package com.sky.service;

import com.sky.vo.TurnoverReportVO;

import java.time.LocalDate;

public interface ReportService {
    // 统计营业额数据
    TurnoverReportVO getTurnoverReport(LocalDate begin, LocalDate end);
}
