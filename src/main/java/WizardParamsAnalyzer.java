import model.Wizard;

public class WizardParamsAnalyzer {
    private Wizard self;

    public WizardParamsAnalyzer(Wizard self) {
        this.self = self;
    }

    public WizardParams getWizardParams() {
        final WizardParams wizardParams = new WizardParams();

        wizardParams.hp = self.getLife();
        wizardParams.mana = self.getMana();

        wizardParams.point = new Point(self.getX(), self.getY());

        return wizardParams;
    }
}
