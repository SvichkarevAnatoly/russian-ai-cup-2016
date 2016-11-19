public class History {
    private static final int CAPACITY = 100;

    private FixedSizeLinkedList<StateShot> states = new FixedSizeLinkedList<>(CAPACITY);

    public void add(StateShot stateShot) {
        states.add(stateShot);
    }

    public GameState getGameState() {
        return states.getLast().gameState;
    }

    public WizardParams getWizardParams() {
        return states.getLast().wizardParams;
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
}
