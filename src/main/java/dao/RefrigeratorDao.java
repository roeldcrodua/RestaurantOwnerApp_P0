package dao;

import model.Food;
import model.HealthInspector;
import model.Refrigerator;
import model.Restaurant;

import java.util.List;

public interface RefrigeratorDao {

    List<Refrigerator> getAllRefrigerator(Integer userId);
    Boolean addNewFridge(Refrigerator fridge);
    Boolean removeFridge(Integer codeId, Integer userId);
    Boolean assignInspector(Integer inspector_id, Integer code_id);
    Boolean removeInspectorAssignInAFridge(Integer inspector_id, Integer code_id);
    List<Integer> getAllAssignRefrigerator(Integer inspectorId);
    List<Integer> getAllInspectorAssignInAFridge(Integer inspectorId);
    Integer getRefrigeratorInfo(Integer code_id);
}
