package fr.aallouv.manager.map.room;

import fr.aallouv.manager.map.EMapRoom;

public class ChoiceRoom extends Room {

    public ChoiceRoom(int x, int y) {
        super(x, y, EMapRoom.CHOICE.getSymbol(), EMapRoom.CHOICE.getName(), EMapRoom.CHOICE.getDescription());
    }

    @Override
    public void onEnter() {
    }

}
