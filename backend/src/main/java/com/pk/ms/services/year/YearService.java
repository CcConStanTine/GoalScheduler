package com.pk.ms.services.year;

import com.pk.ms.dao.year.IYearRepository;
import com.pk.ms.dto.month.MonthBasicInfoDTO;
import com.pk.ms.dto.week.WeekBasicInfoDTO;
import com.pk.ms.dto.year.YearBasicInfoDTO;
import com.pk.ms.dto.year.YearInputDTO;
import com.pk.ms.dto.year.YearWithBasicMonthAndWeekDTO;
import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.week.Week;
import com.pk.ms.entities.year.Year;
import com.pk.ms.exceptions.EntityAlreadyExistException;
import com.pk.ms.mappers.month.MonthMapService;
import com.pk.ms.mappers.week.WeekMapService;
import com.pk.ms.mappers.year.YearMapService;
import com.pk.ms.services.month.MonthService;
import com.pk.ms.services.schedule.ScheduleService;
import com.pk.ms.services.week.WeekService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class YearService {

    private final IYearRepository yearRepo;

    private final YearMapService yearMapService;

    private final ScheduleService scheduleService;

    private final MonthService monthService;

    private final MonthMapService monthMapService;

    private final  WeekService weekService;

    private final WeekMapService weekMapService;

    public YearService(IYearRepository yearRepo, YearMapService yearMapService, ScheduleService scheduleService, MonthService monthService, MonthMapService monthMapService, WeekService weekService, WeekMapService weekMapService) {
        this.yearRepo = yearRepo;
        this.yearMapService = yearMapService;
        this.scheduleService = scheduleService;
        this.monthService = monthService;
        this.monthMapService = monthMapService;
        this.weekService = weekService;
        this.weekMapService = weekMapService;
    }

    public Year getYearById(long id) {
        return yearRepo.findById(id);
    }

    public Year saveYear(Year year) {
        return yearRepo.save(year);
    }

    public List<Year> getAllByScheduleId(long id) {
        return yearRepo.findAllByScheduleId(id);
    }

    public void deleteYear(long id) {
        yearRepo.deleteById(id);
    }

    public Year getActualYear(LocalDate date, long scheduleId) {
        return yearRepo.findByScheduleIdAndYearNumber(scheduleId, date.getYear());
    }

    public boolean existsByScheduleIdAndYearNumber (long scheduleId, YearInputDTO yearInputDTO) {
        if (yearRepo.findByScheduleIdAndYearNumber(scheduleId, yearInputDTO.getYearNumber()) != null)
            return true;
        else
            return false;
    }

    public Year createYear(long scheduleId, YearInputDTO yearInputDTO) {
        if (existsByScheduleIdAndYearNumber(scheduleId, yearInputDTO)) {
            throw new EntityAlreadyExistException(yearInputDTO.getYearNumber());
        }
        return saveYear(new Year(yearInputDTO.getYearNumber(), scheduleService.getScheduleById(scheduleId)));
    }

    public List<YearBasicInfoDTO> getYears(long scheduleId) {
        List<Year> yearList = getAllByScheduleId(scheduleId);
        List<YearBasicInfoDTO> yearBasicInfoDTOList = new ArrayList<>();
        for(Year year : yearList)
            yearBasicInfoDTOList.add(yearMapService.mapToDTO(year));
        return yearBasicInfoDTOList;
    }

    public YearWithBasicMonthAndWeekDTO getYear(@PathVariable("year_id") long yearId) {
        Year year = getYearById(yearId);
        List<Month> monthList = monthService.getMonthsByYearId(yearId);
        List<Week> weekList = weekService.getWeeksByMonthId(yearId);
        List<MonthBasicInfoDTO> monthBasicInfoDTOList = new ArrayList<>();
        List<WeekBasicInfoDTO> weekBasicInfoDTOList = new ArrayList<>();

        for (Month month : monthList)
            monthBasicInfoDTOList.add(monthMapService.mapToDTO(month));
        for (Week week : weekList)
            weekBasicInfoDTOList.add(weekMapService.mapToDTO(week));

        return new YearWithBasicMonthAndWeekDTO(year.getYearId(), year.getYearNumber(), year.isLeapYear(),
                year.getDaysAmount(), monthBasicInfoDTOList, weekBasicInfoDTOList, year.getYearPlansList());
    }

}
