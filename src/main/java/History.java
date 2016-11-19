import java.util.Deque;

public class History {
    private static final int CAPACITY = 100;

    private Deque<StateShot> states = new FixedSizeLinkedList<>(CAPACITY);

    public void add(StateShot stateShot) {
        states.add(stateShot);
    }

    public GameState getGameState() {
        return states.getLast().gameState;
    }

    public WizardParams getWizardParams() {
        return states.getLast().wizardParams;
    }

    public boolean isEmpty() {
        return states.isEmpty();
    }

    public int size() {
        return states.size();
    }
}
