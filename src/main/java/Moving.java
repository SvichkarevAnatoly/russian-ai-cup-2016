import model.Game;
import model.Move;
import model.Wizard;

import java.util.Random;

public class Moving {
    private final Wizard self;
    private final Move move;
    private final Game game;

    private final GameState gameState;

    public Moving(Wizard self, Move move, Game game, GameState gameState) {
        this.self = self;
        this.move = move;
        this.game = game;
        this.gameState = gameState;
    }

    /**
     * Простейший способ перемещения волшебника.
     */
    public void goTo(Point point) {
        double angle = self.getAngleTo(point.getX(), point.getY());

        move.setTurn(angle);

        if (StrictMath.abs(angle) < game.getStaffSector() / 4.0D) {
            move.setSpeed(game.getWizardForwardSpeed());
        } else {
            gameState.isOnlyTurning = true;
        }
    }

    public void goOpposite(Point point) {
        double angle = self.getAngleTo(point.getX(), point.getY());

        move.setTurn(angle);
        move.setSpeed(-game.getWizardBackwardSpeed());
    }

    public void goSomewhere() {
        final Random random = new Random();

        if (random.nextBoolean()) {
            move.setStrafeSpeed(game.getWizardStrafeSpeed());
        } else {
            move.setStrafeSpeed(-game.getWizardStrafeSpeed());
        }

        if (random.nextBoolean()) {
            move.setSpeed(game.getWizardForwardSpeed());
        } else {
            move.setSpeed(-game.getWizardBackwardSpeed());
        }
    }

    public void goToNextWaypoint(Point nextWaypoint) {
        gameState.isMoving = true;
        goTo(nextWaypoint);
    }
}
