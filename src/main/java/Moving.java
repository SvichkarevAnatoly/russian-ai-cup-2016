import model.Game;
import model.Move;
import model.Wizard;

import java.util.Random;

public class Moving {
    private final Wizard self;
    private final Move move;
    private final Game game;

    private final GameState gameState;

    private static final Random random = new Random();
    private static int callTimes = 0;
    private static boolean isLeft;

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
            gameState.isMoving = true;
        } else {
            gameState.isOnlyTurning = true;
        }
    }

    public void goBackward(Point point) {
        final double sx = self.getX();
        final double sy = self.getY();
        final double px = point.getX();
        final double py = point.getY();
        final Point angleAim = new Point(sx + (sx - px), sy + (sy - py));

        double angle = self.getAngleTo(angleAim.getX(), angleAim.getY());

        move.setTurn(angle);
        move.setSpeed(-game.getWizardBackwardSpeed());
        gameState.isMoving = true;
    }

    public void goSomewhere() {
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

    public void goStrafe() {
        // чтобы двойной сдвиг был
        if (callTimes < 2) {
            callTimes++;
        } else {
            isLeft = random.nextBoolean();
            callTimes = 0;
        }

        if (isLeft) {
            move.setStrafeSpeed(game.getWizardStrafeSpeed());
        } else {
            move.setStrafeSpeed(-game.getWizardStrafeSpeed());
        }
    }

    public void goToNextWaypoint(Point nextWaypoint) {
        gameState.isMoving = true;
        goTo(nextWaypoint);
    }
}
