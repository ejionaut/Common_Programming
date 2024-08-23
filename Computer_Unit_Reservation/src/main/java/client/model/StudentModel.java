package client.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class StudentModel {
    public StudentModel(){}

    public List<String> getSubjects() {
        List<String> subjectList = new ArrayList<>();
        subjectList.add("Computer Programming");
        subjectList.add("Software Engineering");
        subjectList.add("Web Development");
        return subjectList;
    }

    public List<String> getPurpose() {
        List<String> purposeList = new ArrayList<>();
        purposeList.add("Study");
        purposeList.add("Exam");
        purposeList.add("Project");
        return purposeList;
    }

    public List<LocalTime> getAvailableEndTimes(LocalTime startTime) {
        List<LocalTime> availableEndTimes = new ArrayList<>();
        for (int i = startTime.getHour() + 1; i <= 18; i++) {
            availableEndTimes.add(LocalTime.of(i, 0));
        }
        return availableEndTimes;
    }

    public boolean isAvailableUnit(int unitID) {
        return unitID > 0 && unitID <= 50;
    }
}
