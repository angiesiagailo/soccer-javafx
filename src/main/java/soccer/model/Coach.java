package soccer.model;

public class Coach {
    private int coachId;
    private String coachFirstName;
    private String coachLastName;
    private String phoneNumber;
    private String coachEmail;
    
    public Coach(int coachId, String coachFirstName, String coachLastName, String phoneNumber, String coachEmail){
        this.coachId = coachId;
        this.coachFirstName = coachFirstName;
        this.coachLastName = coachLastName;
        this.phoneNumber = phoneNumber;
        this.coachEmail = coachEmail;
    }

    public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId = coachId;
    }

    public String getFirstName() {
        return coachFirstName;
    }

    public void setFirstName(String coachFirstName) {
        this.coachFirstName = coachFirstName;
    }

    public String getLastName() {
        return coachLastName;
    }

    public void setLastName(String coachLastName) {
        this.coachLastName = coachLastName;
    }

    public String getPhone() {
        return phoneNumber;
    }

    public void setPhone(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return coachEmail;
    }

    public void setEmail(String coachEmail) {
        this.coachEmail = coachEmail;
    }
}
