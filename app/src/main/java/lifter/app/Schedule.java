package lifter.app;

public class Schedule {
    String id;
    String email;
    String etDay;
    String fromTime;
    String toTime;



    public Schedule(String id, String email, String etDay, String fromTime, String toTime) {
        this.id = id;
        this.email = email;
        this.etDay = etDay;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public String getId() { return id; }

    public String getEmail() {return email;}

    public String getDay() {
        return etDay;
    }

    public String getFrom() {
        return fromTime;
    }

    public String getTo() {
        return toTime;
    }
}

