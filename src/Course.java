import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

class Course {
    String name;
    int attended;
    int delivered;
    int classesPerWeek;
    double percentage;

    Course(String name, int attended, int delivered, int classesPerWeek) {
        this.name = name;
        this.attended = attended;
        this.delivered = delivered;
        this.classesPerWeek = classesPerWeek;
        this.percentage = (attended * 100.0) / delivered;
    }

    int maxSkipsNow() {
        double x = (attended - 0.75 * delivered) / 0.75;
        return x < 0 ? 0 : (int) Math.floor(x);
    }

    int classesToAttendFuture(int remainingLectures) {
        int y = 0;
        while (((attended + y) * 100.0) / (delivered + remainingLectures) < 75) {
            y++;
        }
        return y;
    }
}
