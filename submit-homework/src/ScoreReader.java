import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by inksmallfrog on 10/2/15.
 */
public class ScoreReader {
    private float averageScore = 0.0f;
    private float gpa = 0.0f;

    private float scoreCount = 0.0f;
    private float gpaCount = 0.0f;
    private float weight = 0.0f;

    List<ClassInfo> classInfos = new ArrayList<>();

    ScoreReader(){}

    ScoreReader(File input){
        LoadInfoFromFile(input);
    }

    public boolean LoadInfoFromFile(File input){
        try {
            Document doc = Jsoup.parse(input, "gb2312");
            Element table = doc.getElementsByTag("table").first();
            Elements items = table.getElementsByTag("tr");

            for(Element item : items){
                ClassInfo classInfo = new ClassInfo();
                Elements infos = item.getElementsByTag("td");
                if(infos.size() == 0){
                    continue;
                }

                classInfo.LoadInfo(infos);
                classInfos.add(classInfo);

                if(classInfo.getScore() >= 0.0f) {
                    CountAll(classInfo.getScore(), classInfo.getClassCredit());
                }
            }

            GenerateAll();

            classInfos.sort(Comparator.<ClassInfo>reverseOrder());
        }
        catch(IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void CountScore(float score, float credit){
        scoreCount += score * credit;
    }

    public void CountGpa(float score, float credit){
        if (score >= 90.0f) {
            gpaCount += credit * 4.0f;
        } else if (score >= 85.0f) {
            gpaCount += credit * 3.7f;
        } else if (score >= 82.0f) {
            gpaCount += credit * 3.3f;
        } else if (score >= 78.0f) {
            gpaCount += credit * 3.0f;
        } else if (score >= 75.0f) {
            gpaCount += credit * 2.7f;
        } else if (score >= 72.0f) {
            gpaCount += credit * 2.3f;
        } else if (score >= 68.0f) {
            gpaCount += credit * 2.0f;
        } else if (score >= 64.0f) {
            gpaCount += credit * 1.5f;
        } else if (score >= 60.0f) {
            gpaCount += credit * 1.0f;
        } else {
            gpaCount += 0.0f;
        }
    }

    public void CountWeight(float credit){
        weight += credit;
    }

    public void CountAll(float score, float credit){
        CountScore(score, credit);
        CountGpa(score, credit);
        CountWeight(credit);
    }

    public void GenerateAverageScore(){
        averageScore = scoreCount / weight;
    }

    public void GenerateGPA(){
        gpa = gpaCount / weight;
    }

    public void GenerateAll(){
        GenerateAverageScore();
        GenerateGPA();
    }

    public float getAverageScore() {
        return averageScore;
    }

    public float getGpa() {
        return gpa;
    }

    public List<ClassInfo> getClassInfos() {
        return classInfos;
    }
}