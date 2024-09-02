package gallium.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Deadline extends Task {

    protected String desc;
    protected String by;
    protected String date;
    protected String time;

    public Deadline(String description) throws ParseException {
        super(description);
        try {
            if (description.startsWith("deadline ")) {
                this.desc = description.split("deadline ")[1].split(" /by")[0];
                this.by = description.split("/by ")[1];
                String dateString = by.split(" ")[0];
                this.date = LocalDate.parse(dateString).format(DateTimeFormatter.ofPattern("MMM d yyyy"));
                SimpleDateFormat inputTime = new SimpleDateFormat("HHmm");
                SimpleDateFormat outputTime = new SimpleDateFormat("hh:mm a");
                Date time24 = inputTime.parse(by.split(" ")[1]);
                this.time = outputTime.format(time24);
            } else if (description.startsWith("[D][ ] ")) {
                String[] parts = description.split("\\[D\\]\\[ \\] ");
                this.desc = parts[1].split(" \\(by:")[0];
                String[] byParts = parts[1].split("\\(by: ");
                this.by = byParts[1].split("\\)")[0];
                this.isDone = false;
                this.date = by.split(", ")[0];
                this.time = by.split(", ")[1];
            } else if (description.startsWith("[D][X] ")) {
                String[] parts = description.split("\\[D\\]\\[X\\] ");
                this.desc = parts[1].split(" \\(by:")[0];
                String[] byParts = parts[1].split("\\(by: ");
                this.by = byParts[1].split("\\)")[0];
                this.isDone = true;
                this.date = by.split(", ")[0];
                this.time = by.split(", ")[1];
            }
        } catch (ParseException e) {
            throw e;
        }
    }

    public String toString() {
        return "[D]" + this.getStatusIcon() + this.desc + " (by: "
                + this.date + ", " + this.time + ")";
    }

    public String getDate() {
        return this.date;
    }

    public String getTime() {
        return this.time;
    }
}
