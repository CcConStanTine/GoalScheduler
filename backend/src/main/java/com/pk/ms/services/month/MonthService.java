package com.pk.ms.services.month;

import com.pk.ms.dao.month.IMonthRepository;
import com.pk.ms.dto.day.DayBasicInfoDTO;
import com.pk.ms.dto.month.MonthInputDTO;
import com.pk.ms.dto.month.MonthWithBasicDayDTO;
import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.month.Month;
import com.pk.ms.exceptions.EntityAlreadyExistException;
import com.pk.ms.mappers.day.DayMapService;
import com.pk.ms.services.day.DayService;
import com.pk.ms.services.year.YearService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MonthService {

    private final IMonthRepository monthRepo;

    private final YearService yearService;

    private final DayService dayService;

    private final DayMapService dayMapService;

    public MonthService(IMonthRepository monthRepo, @Lazy YearService yearService, DayService dayService, DayMapService dayMapService) {
        this.monthRepo = monthRepo;
        this.yearService = yearService;
        this.dayService = dayService;
        this.dayMapService = dayMapService;
    }

    public Month saveMonth(Month month) {
        return monthRepo.save(month);
    }

    public Month getMonthById(long id) {
        return monthRepo.findById(id);
    }

    public List<Month> getMonthsByYearId(long id) { return monthRepo.findAllByYearId(id); }

    public void delete(long id) { monthRepo.deleteById(id); }

    public Month getActualMonth(LocalDate date, long yearId) {
        return monthRepo.findByYearIdAndMonthName(yearId, date.getMonthValue());
    }

    public boolean existsByYearIdAndMonthName(long yearId, MonthInputDTO monthInputDTO) {
        if (monthRepo.findByYearIdAndMonthName(yearId, monthInputDTO.getMonthName().getMonthNumber()) != null)
            return true;
        else
            return false;
    }

    public Month createMonth(long yearId, MonthInputDTO monthInputDTO) {
        if (existsByYearIdAndMonthName(yearId, monthInputDTO))
            throw new EntityAlreadyExistException(monthInputDTO.getMonthName());
        return saveMonth(new Month(monthInputDTO.getMonthName(),
                yearService.getYearById(yearId)));
    }


    public MonthWithBasicDayDTO getMonth(long monthId) {
        Month month = getMonthById(monthId);
        List<Day> dayList = dayService.getDaysByMonthId(monthId);
        List<DayBasicInfoDTO> dayBasicInfoDTOList = new ArrayList<>();
        for(Day day : dayList)
            dayBasicInfoDTOList.add(dayMapService.mapToDTO(day));
        return new MonthWithBasicDayDTO(month.getMonthId(), month.getMonthName(), month.getDaysAmount(),
                dayBasicInfoDTOList, month.getMonthPlansList());
    }

}
