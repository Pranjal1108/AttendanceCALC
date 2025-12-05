import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

class SemesterCalendar {
    LocalDate startDate;
    LocalDate endDate;
    Set<LocalDate> holidays;
    Map<String, List<DayOfWeek>> courseDays;

    SemesterCalendar(LocalDate start, LocalDate end) {
        this.startDate = start;
        this.endDate = end;
        this.holidays = new HashSet<>();
        this.courseDays = new HashMap<>();
    }

    void addHoliday(LocalDate holiday) {
        holidays.add(holiday);
    }

    void addCourseSchedule(String courseName, int classesPerWeek) {
        List<DayOfWeek> dayList = new ArrayList<>();
        DayOfWeek[] weekdays = DayOfWeek.values(); // MONDAY to SUNDAY
        int count = 0;
        for (DayOfWeek day : weekdays) {
            if (day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY) { // skip weekends
                dayList.add(day);
                count++;
                if (count >= classesPerWeek) break;
            }
        }
        courseDays.put(courseName, dayList);
    }

    int getRemainingLectures(String courseName, LocalDate today) {
        List<DayOfWeek> classDays = courseDays.get(courseName);
        int lectures = 0;

        LocalDate date = today.plusDays(1);
        while (!date.isAfter(endDate)) {
            if (!holidays.contains(date) && classDays.contains(date.getDayOfWeek())) {
                lectures++;
            }
            date = date.plusDays(1);
        }
        return lectures;
    }
}
