package com.pk.ms.services.month;

import com.pk.ms.dao.month.MonthRepository;
import com.pk.ms.dto.month.MonthBasicInfoDTO;
import com.pk.ms.entities.month.Month;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.mappers.month.MonthMapService;
import com.pk.ms.services.year.YearService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MonthService {

    private final MonthRepository repository;

    private final MonthMapService monthMapService;

    private final YearService yearService;

    public MonthService(MonthRepository repository, MonthMapService monthMapService, YearService yearService) {
        this.repository = repository;
        this.monthMapService = monthMapService;
        this.yearService = yearService;
    }

    public Month getMonthById(long monthId) {
        return getNotNullMonthById(monthId);
    }

    public List<MonthBasicInfoDTO> getMonthDTOsByYearId(long yearId) {
        List<Month> monthList = repository.findAllByYearId(yearId);
        return mapToDTOs(monthList);
    }

    public List<MonthBasicInfoDTO> getMonthDTOsByLocalDate(LocalDate date) {
        long yearId = yearService.getYearByYearNumber(date.getYear()).getYearId();
        List<Month> monthList = repository.findAllByYearId(yearId);
        return mapToDTOs(monthList);
    }

    public MonthBasicInfoDTO getMonthDTOById(long monthId) {
        return mapToDTO(getNotNullMonthById(monthId));
    }

    public MonthBasicInfoDTO getMonthDTOByLocalDate(LocalDate date) {
        return mapToDTO(getNotNullMonthByLocalDate(date));
    }

    private Month getNotNullMonthById(long monthId) {
        return repository.findById(monthId).orElseThrow(ResourceNotAvailableException::new);
    }

    private Month getNotNullMonthByLocalDate(LocalDate date) {
        return repository.findByYearIdAndMonthName(
                yearService.getYearByYearNumber(date.getYear()).getYearId(), date.getMonthValue())
                .orElseThrow(ResourceNotAvailableException::new);
    }

    private MonthBasicInfoDTO mapToDTO(Month month) {
        return monthMapService.mapToDTO(month);
    }

    private List<MonthBasicInfoDTO> mapToDTOs(List<Month> monthList) {
        List<MonthBasicInfoDTO> monthBasicInfoDTOList = new ArrayList<>();
        for (Month month : monthList)
            monthBasicInfoDTOList.add(mapToDTO(month));
        return monthBasicInfoDTOList;
    }
}
