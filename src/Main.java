import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter semester start date (YYYY-MM-DD): ");
        LocalDate startDate = LocalDate.parse(sc.nextLine());

        System.out.print("Enter semester end date (YYYY-MM-DD): ");
        LocalDate endDate = LocalDate.parse(sc.nextLine());

        SemesterCalendar calendar = new SemesterCalendar(startDate, endDate);

        // Add fixed holidays
        LocalDate[] fixedHolidays = {
                LocalDate.parse("2025-08-15"), LocalDate.parse("2025-08-16"),
                LocalDate.parse("2025-09-05"), LocalDate.parse("2025-10-01"),
                LocalDate.parse("2025-10-02"), LocalDate.parse("2025-10-20"),
                LocalDate.parse("2025-10-22"), LocalDate.parse("2025-10-23"),
                LocalDate.parse("2025-10-28"), LocalDate.parse("2025-11-05"),
                LocalDate.parse("2025-12-25")
        };
        for (LocalDate h : fixedHolidays) calendar.addHoliday(h);

        // Add vacation period
        LocalDate startVac = LocalDate.parse("2025-12-25");
        LocalDate endVac = LocalDate.parse("2026-01-01");
        LocalDate date = startVac;
        while (!date.isAfter(endVac)) {
            calendar.addHoliday(date);
            date = date.plusDays(1);
        }

        System.out.print("Enter number of courses: ");
        int numCourses = sc.nextInt();
        sc.nextLine();

        Map<String, Course> courses = new HashMap<>();

        for (int i = 0; i < numCourses; i++) {
            System.out.println("\nCourse " + (i + 1) + ":");

            System.out.print("Enter course name: ");
            String name = sc.nextLine();

            System.out.print("Enter classes attended so far: ");
            int attended = sc.nextInt();

            System.out.print("Enter lectures delivered so far: ");
            int delivered = sc.nextInt();

            System.out.print("Enter course credit (classes per week): ");
            int credit = sc.nextInt();
            sc.nextLine();

            Course c = new Course(name, attended, delivered, credit);
            courses.put(name, c);

            // Automatically assign weekdays
            calendar.addCourseSchedule(name, credit);
        }

        LocalDate today = LocalDate.now();

        System.out.println("\n===== Attendance Report =====");
        for (String courseName : courses.keySet()) {
            Course c = courses.get(courseName);

            int remainingLectures = calendar.getRemainingLectures(courseName, today);

            System.out.println("\nCourse: " + c.name);
            System.out.println("Attended: " + c.attended);
            System.out.println("Delivered: " + c.delivered);
            System.out.println("Current Percentage: " + c.percentage + "%");
            System.out.println("Maximum skips now: " + c.maxSkipsNow());
            System.out.println("Remaining lectures in semester: " + remainingLectures);
            System.out.println("Classes to attend in remaining lectures to stay >=75%: "
                    + c.classesToAttendFuture(remainingLectures));
        }

        sc.close();


    }
}
