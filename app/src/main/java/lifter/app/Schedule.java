package lifter.app;

public class Schedule {
    String id;
    String email;
    String day;
    String from;
    String to;

    public Schedule() {

    }

    public Schedule(String id, String email, String day, String from, String to) {
        this.id = id;
        this.email = email;
        this.day = day;
        this.from = from;
        this.to = to;
    }

    public String getId() { return id; }

    public String getEmail() {return email;}

    public String getDay() { return day; }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}

