public class Exam {
    private final int math;
    private final int science;
    private final int english;
    private double passOrFail;

    Exam(int math, int science, int english){
        this.math=math;
        this.science=science;
        this.english=english;
    }
    Exam(int math, int science, int english, double passOrFail){
        this.math=math;
        this.science=science;
        this.english=english;
        this.passOrFail=passOrFail;
    }

    public int getMath() {
        return math;
    }

    public int getScience() {
        return science;
    }

    public int getEnglish() {
        return english;
    }

    public double getPassOrFail() {
        return passOrFail;
    }

    public void setPassOrFail(double passOrFail) {
        this.passOrFail = passOrFail;
    }
}
