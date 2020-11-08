package com.pk.ms.services.month;

import com.pk.ms.dao.month.MonthRepository;
import com.pk.ms.dto.day.DayBasicInfoDTO;
import com.pk.ms.dto.month.MonthBasicInfoDTO;
import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.year.Year;
import com.pk.ms.exceptions.AccessDeniedException;
import com.pk.ms.exceptions.ResourceNotAvailableException;
import com.pk.ms.mappers.day.DayMapService;
import com.pk.ms.mappers.month.MonthMapService;
import com.pk.ms.services.day.DayService;
import com.pk.ms.services.year.YearService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MonthService {

    private final MonthRepository monthRepo;

    private final MonthMapService monthMapService;

    private final YearService yearService;

    public MonthService(MonthRepository monthRepo, MonthMapService monthMapService, YearService yearService) {
        this.monthRepo = monthRepo;
        this.monthMapService = monthMapService;
        this.yearService = yearService;
    }

    public Month getMonthById(long monthId) {
        return getNotNullMonthById(monthId);
    }

    public List<MonthBasicInfoDTO> getMonthDTOsByYearId(long yearId) {
        List<Month> monthList = getMonthsByYearId(yearId);
        List<MonthBasicInfoDTO> monthBasicInfoDTOList = new ArrayList<>();
        for(Month month : monthList)
            monthBasicInfoDTOList.add(mapMonthToDTO(month));
        return monthBasicInfoDTOList;
    }

    public List<MonthBasicInfoDTO> getMonthDTOsByLocalDate(LocalDate date) {
        long yearId = yearService.getYearDTOById(date.getYear()).getYearId();
        List<Month> monthList = getMonthsByYearId(yearId);
        List<MonthBasicInfoDTO> monthBasicInfoDTOList = new ArrayList<>();
        for(Month month : monthList)
            monthBasicInfoDTOList.add(mapMonthToDTO(month));
        return monthBasicInfoDTOList;
    }

    public MonthBasicInfoDTO getMonthDTOById(long monthId) {
        return mapMonthToDTO(getNotNullMonthById(monthId));
    }

    public MonthBasicInfoDTO getMonthDTOByLocalDate(LocalDate date) {
        return mapMonthToDTO(getNotNullMonthByLocalDate(date));
    }



    private List<Month> getMonthsByYearId(long yearId) {
        return monthRepo.findAllByYearId(yearId);
    }

    private Month getNotNullMonthById(long monthId) {
        Month month = getMonthByIdFromRepo(monthId);
        if (isObjectNull(month))
            throw new ResourceNotAvailableException();
        return month;
    }

    private Month getMonthByIdFromRepo(long monthId) {
        return monthRepo.findById(monthId);
    }

    private Month getNotNullMonthByLocalDate(LocalDate date) {
        Month month = getMonthByLocalDateFromRepo(date);
        if (isObjectNull(month))
            throw new ResourceNotAvailableException();
        return month;
    }

    private Month getMonthByLocalDateFromRepo(LocalDate date) {
        return monthRepo.findByYearIdAndMonthNumber(
                yearService.getYearDTOByLocalDate(date).getYearId(),
                date.getMonthValue());
    }

    private MonthBasicInfoDTO mapMonthToDTO(Month month) {
        return monthMapService.mapToDTO(month);
    }

    private boolean isObjectNull(Object object) {
        return object == null;
    }
}
