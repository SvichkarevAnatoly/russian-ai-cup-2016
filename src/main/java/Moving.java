import model.Game;
import model.Move;
import model.Wizard;

public class Moving {
    private Wizard self;
    private Move move;
    private Game game;

    public Moving(Wizard self, Move move, Game game) {
        this.self = self;
        this.move = move;
        this.game = game;
    }

    /**
     * Простейший способ перемещения волшебника.
     */
    public void goTo(Point2D point) {
        double angle = self.getAngleTo(point.getX(), point.getY());

        move.setTurn(angle);

        if (StrictMath.abs(angle) < game.getStaffSector() / 4.0D) {
            move.setSpeed(game.getWizardForwardSpeed());
        }
    }
}
