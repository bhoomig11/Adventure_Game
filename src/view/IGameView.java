package view;

import java.util.List;

public interface IGameView {
  DescriptionPanel getDescriptionPanel();
  void startView();
  NavigationPanel getNavigationPanel();
  InventoryPanel getInventoryPanel();
  void updateView();
  void showPopUp(String s);
  String[] getRoomItems();

  void showSelectionDialog(String s, String[] roomItems);
}
