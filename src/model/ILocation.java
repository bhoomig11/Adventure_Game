package model;

import java.util.Map;

public interface ILocation extends IGameObject {
  String getRoomNumber();
  Map getPaths();
  String getPuzzleName();
  String getMonsterName();
  String getItemNames();
  String getFixtureNames();
  String getPath(String direction);
}
