package com.pk.ms.services.day;

import com.pk.ms.dao.day.IDayRepository;
import com.pk.ms.dto.day.DayInputDTO;
import com.pk.ms.entities.day.Day;
import com.pk.ms.services.month.MonthService;
import com.pk.ms.services.week.WeekService;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class DayService {

    private final IDayRepository dayRepo;

    private WeekService weekService;

    private MonthService monthService;

    public DayService(IDayRepository dayRepo) {
        this.dayRepo = dayRepo;
    }

    public void setWeekService(WeekService weekService) {
        this.weekService = weekService;
    }

    public void setMonthService(MonthService monthService) {
        this.monthService = monthService;
    }

    public Day saveDay(Day day) {
        return dayRepo.save(day);
    }

    public Day getDayById(long id) {
        return dayRepo.findById(id);
    }

    public List<Day> getDaysByWeekId(long id) {
        return dayRepo.getDaysByWeekId(id);
    }

    public List<Day> getDaysByMonthId(long id) {
        return dayRepo.getDaysByMonthId(id);
    }

    public void deleteDay(long id) { dayRepo.deleteById(id); }

    public Day createDay(long monthId, long weekId, DayInputDTO reqDayInputDTO) {
        return saveDay(new Day(reqDayInputDTO.getDayDate(), reqDayInputDTO.getDayName(),
                weekService.getWeekById(weekId), monthService.getMonthById(monthId)));
    }

    public Day getActualDay(long monthId, LocalDate date) {
        return dayRepo.findByMonthIdAndDayDate(monthId, Date.valueOf(date));
    }

}
