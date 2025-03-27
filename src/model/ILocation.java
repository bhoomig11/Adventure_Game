package model;

import java.util.Map;

public interface ILocation extends IGameObject {
  String getRoomNumber();
  Map getExits();
  String getPuzzleName();
  String getMonsterName();
  String getItems();
  String getFixtures();
  String getPictureName();
}
