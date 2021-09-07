package dao;

import model.HealthInspector;

import java.util.List;

public interface HealthInspectorDao {

    List<String> getHealthInspectorList();
    List<Integer> getListOfInspectorId();
    HealthInspector getHealthInspectorInfo(String userName, String userPass);
    Boolean addNewHealthInspector(HealthInspector inspector);
}
