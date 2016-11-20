import model.LivingUnit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class History {
    private FixedSizeLinkedList<StateShot> states = new FixedSizeLinkedList<>(Const.HISTORY_CAPACITY);

    public void add(StateShot stateShot) {
        states.add(stateShot);
    }

    public GameState getGameState() {
        return states.getLast().gameState;
    }

    public WizardParams getWizardParams() {
        return states.getLast().wizardParams;
    }

    public OtherState getOtherState() {
        return states.getLast().otherState;
    }

    public GameState getPreviousGameState() {
        return states.get(states.size() - 2).gameState;
    }

    public WizardParams getPreviousWizardParams() {
        return states.get(states.size() - 2).wizardParams;
    }

    public boolean isEmpty() {
        return states.isEmpty();
    }

    public int size() {
        return states.size();
    }

    public List<GameState> getLastGameStates(int size) {
        final int fromIndex = states.size() - size;
        final int toIndex = states.size();
        final List<StateShot> lastStateShots = states.subList(fromIndex, toIndex);
        final List<GameState> lastGameStates = new ArrayList<>();
        for (StateShot lastStateShot : lastStateShots) {
            lastGameStates.add(lastStateShot.gameState);
        }
        return lastGameStates;
    }

    public LivingUnit getLastEnemy() {
        final Iterator<StateShot> iterator = states.descendingIterator();
        while (iterator.hasNext()) {
            final LivingUnit lastEnemy = iterator.next().otherState.lastEnemy;
            if (lastEnemy != null) {
                return lastEnemy;
            }
        }
        return null;
    }
}
