import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by inksmallfrog on 10/2/15.
 */
public class ClassInfo implements Comparable{
    private String classId;
    private String className;
    private String classType;
    private float classCredit;
    private String teacherName;
    private String classSchool;
    private String studyType;
    private int schoolYear;
    private String term;
    private float score;

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public float getClassCredit() {
        return classCredit;
    }

    public void setClassCredit(float classCredit) {
        this.classCredit = classCredit;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getClassSchool() {
        return classSchool;
    }

    public void setClassSchool(String classSchool) {
        this.classSchool = classSchool;
    }

    public String getStudyType() {
        return studyType;
    }

    public void setStudyType(String studyType) {
        this.studyType = studyType;
    }

    public int getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(int schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void LoadInfo(Elements infos){
        Element info = infos.first();
        setClassId(info.text());

        info = info.nextElementSibling();
        setClassName(info.text());

        info = info.nextElementSibling();
        setClassType(info.text());

        info = info.nextElementSibling();
        setClassCredit(Float.parseFloat(info.text()));

        info = info.nextElementSibling();
        setTeacherName(info.text());

        info = info.nextElementSibling();
        setClassSchool(info.text());

        info = info.nextElementSibling();
        setStudyType(info.text());

        info = info.nextElementSibling();
        setSchoolYear(Integer.parseInt(info.text()));

        info = info.nextElementSibling();
        setTerm(info.text());

        info = info.nextElementSibling();
        if(info.text().isEmpty()){
            setScore(-1.0f);
        }
        else{
            setScore(Float.parseFloat(info.text()));
        }
    };

    @Override
    public int compareTo(Object o){
        ClassInfo otherClass = (ClassInfo)o;
        return Float.compare(this.score, otherClass.getScore());
    }
}