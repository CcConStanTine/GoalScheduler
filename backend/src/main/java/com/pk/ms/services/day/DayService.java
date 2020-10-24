package com.pk.ms.services.day;

import com.pk.ms.dao.day.IDayRepository;
import com.pk.ms.dto.day.DayInputDTO;
import com.pk.ms.entities.day.Day;
import com.pk.ms.entities.month.Month;
import com.pk.ms.entities.week.Week;
import com.pk.ms.entities.year.Year;
import com.pk.ms.exceptions.AccessDeniedException;
import com.pk.ms.exceptions.EntityAlreadyExistException;
import com.pk.ms.exceptions.NotValidDataException;
import com.pk.ms.services.month.MonthService;
import com.pk.ms.services.week.WeekService;
import com.pk.ms.services.year.YearService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class DayService {

    private final IDayRepository dayRepo;

    private final YearService yearService;

    private final WeekService weekService;

    private final MonthService monthService;

    public DayService(IDayRepository dayRepo, @Lazy YearService yearService, @Lazy WeekService weekService, @Lazy MonthService monthService) {
        this.dayRepo = dayRepo;
        this.yearService = yearService;
        this.weekService = weekService;
        this.monthService = monthService;
    }

    public Day saveDay(Day day) {
        return dayRepo.save(day);
    }

    public Day getDay(long scheduleId, long dayId) {
        Day day = getDayById(dayId);
        if(hasAccess(scheduleId, day))
            return day;
        else
            throw new AccessDeniedException("This user cannot access this resource. ");
    }

    public Day getDayById(long dayId) {
        return dayRepo.findById(dayId);
    }

    public List<Day> getDaysByWeekId(long id) {
        return dayRepo.getDaysByWeekId(id);
    }

    public List<Day> getDaysByMonthId(long id) {
        return dayRepo.getDaysByMonthId(id);
    }

    public String deleteDay(long scheduleId, long dayId) {
        if (hasAccess(scheduleId, getDayById(dayId))) {
            dayRepo.deleteById(dayId);
            return "Day deleted successfully. ";
        }
        else
            throw new AccessDeniedException("This user cannot delete this resource. ");

    }

    public boolean existsInWeek(long weekId, DayInputDTO dayInputDTO) {
        if (dayRepo.findByWeekIdAndDayName(weekId, dayInputDTO.getDayName().getDayNumber()) != null)
            return true;
        else
            return false;
    }

    public boolean existsInMonthByDate(long monthId, DayInputDTO dayInputDTO) {
        if (dayRepo.findByMonthIdAndDayDate(monthId, dayInputDTO.getDayDate()) != null)
            return true;
        else
            return false;
    }

    public boolean dataContainProperMonth(long monthId, DayInputDTO dayInputDTO) {
        Date date = dayInputDTO.getDayDate();
        LocalDate localDate = date.toLocalDate();
        if (monthService.getMonthById(monthId).getMonthName().getMonthNumber() == localDate.getMonthValue())
            return true;
        else
            return false;
    }

    public Day createDay(long scheduleId, long weekId, DayInputDTO reqDayInputDTO) {
        if(weekService.hasAccess(scheduleId, weekService.getWeekById(weekId))) {
            LocalDate localDate = reqDayInputDTO.getDayDate().toLocalDate();
            Year year = yearService.getActualYear(localDate, scheduleId);
            Month month = monthService.getActualMonth(localDate, year.getYearId());
            long monthId = month.getMonthId();

            if (!dataContainProperMonth(monthId, reqDayInputDTO))
                throw new NotValidDataException(reqDayInputDTO);
            if (existsInWeek(weekId, reqDayInputDTO))
                throw new EntityAlreadyExistException(reqDayInputDTO.getDayName());
            if (existsInMonthByDate(monthId, reqDayInputDTO))
                throw new EntityAlreadyExistException(reqDayInputDTO.getDayDate());

            return saveDay(new Day(reqDayInputDTO.getDayDate(), reqDayInputDTO.getDayName(),
                    weekService.getWeekById(weekId), monthService.getMonthById(monthId)));
        }
        else
            throw new AccessDeniedException("This user cannot create Day in this Week. ");
    }

    public Day getActualDay(long monthId, LocalDate date) {
        return dayRepo.findByMonthIdAndDayDate(monthId, Date.valueOf(date));
    }

    public boolean hasAccess(long scheduleId, Day day) {
        return day.getWeek().getYear().getSchedule().getScheduleId() == scheduleId;
    }
}
