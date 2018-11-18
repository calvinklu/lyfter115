package lifter.app;

public class Workout {
    String id;
    String email;
    String muscle;

    public Workout() {

    }

    public Workout(String id, String email, String muscle) {
        this.id = id;
        this.email = email;
        this.muscle = muscle;
    }

    public String getId() { return id; }

    public String getEmail() {return email;}

    public String getMuscle() { return muscle; }

}