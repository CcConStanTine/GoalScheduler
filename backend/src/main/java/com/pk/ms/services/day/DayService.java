package com.pk.ms.services.day;

import com.pk.ms.dao.day.IDayRepository;
import com.pk.ms.dto.day.DayInputDTO;
import com.pk.ms.entities.day.Day;
import com.pk.ms.exceptions.EntityAlreadyExistException;
import com.pk.ms.exceptions.NotValidDataException;
import com.pk.ms.services.month.MonthService;
import com.pk.ms.services.week.WeekService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class DayService {

    private final IDayRepository dayRepo;

    private final WeekService weekService;

    private final MonthService monthService;

    public DayService(IDayRepository dayRepo, @Lazy WeekService weekService, @Lazy MonthService monthService) {
        this.dayRepo = dayRepo;
        this.weekService = weekService;
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

    public Day createDay(long monthId, long weekId, DayInputDTO reqDayInputDTO) {
        if(!dataContainProperMonth(monthId, reqDayInputDTO))
            throw new NotValidDataException(reqDayInputDTO);
        if(existsInWeek(weekId, reqDayInputDTO))
            throw new EntityAlreadyExistException(reqDayInputDTO.getDayName());
        if(existsInMonthByDate(monthId, reqDayInputDTO))
            throw new EntityAlreadyExistException(reqDayInputDTO.getDayDate());

        return saveDay(new Day(reqDayInputDTO.getDayDate(), reqDayInputDTO.getDayName(),
                weekService.getWeekById(weekId), monthService.getMonthById(monthId)));
    }

    public Day getActualDay(long monthId, LocalDate date) {
        return dayRepo.findByMonthIdAndDayDate(monthId, Date.valueOf(date));
    }

}
