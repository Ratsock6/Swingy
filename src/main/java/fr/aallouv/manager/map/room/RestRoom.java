package fr.aallouv.manager.map.room;

import fr.aallouv.App;
import fr.aallouv.manager.map.EMapRoom;

import java.util.Scanner;

public class RestRoom extends Room {

    private boolean trigger;

    public RestRoom(int x, int y) {
        super(x, y, EMapRoom.REST.getSymbol(), EMapRoom.REST.getName(), EMapRoom.REST.getDescription());
        trigger = false;
    }

    @Override
    public void onEnter() {
        if (!trigger) {
            App.getApp().getGameManager().printMessage("Do you want to activate the room's effect and be healed to 100%? Or come back later.");
            App.getApp().getGameManager().printMessage("y) yes");
            App.getApp().getGameManager().printMessage("n) no");
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String input = scanner.nextLine();
                if (input.equalsIgnoreCase("n")) {
                    break;
                } else if (input.equalsIgnoreCase("y")) {
                    trigger = true;
                    App.getApp().getGameManager().getHero().setHealth(App.getApp().getGameManager().getHero().getMaxHealth());
                    break;
                }
            }
        } else {
            App.getApp().getGameManager().printMessage("You have already used the power of this room.");
        }

    }
}
