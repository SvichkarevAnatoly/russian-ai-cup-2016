public class StateShot {
    public GameState gameState;
    public WizardParams wizardParams;
    public OtherState otherState;

    public StateShot(GameState gameState, WizardParams wizardParams) {
        this.gameState = gameState;
        this.wizardParams = wizardParams;
        otherState = new OtherState();
    }
}
