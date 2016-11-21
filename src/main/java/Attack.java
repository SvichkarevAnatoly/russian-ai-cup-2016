import model.*;

public class Attack {
    private Wizard self;
    private Game game;

    public Attack(Wizard self, Game game) {
        this.self = self;
        this.game = game;
    }

    public boolean canAttack(LivingUnit unit) {
        // Если видим противника ...
        if (unit != null) {
            double distance = self.getDistanceTo(unit);

            // ... и он в пределах досягаемости наших заклинаний, ...
            if (distance <= self.getCastRange()) {
                return true;
            }
        }
        return false;
    }

    public void attack(Move move, LivingUnit unit) {
        double distance = self.getDistanceTo(unit);
        double angle = self.getAngleTo(unit);

        // ... то поворачиваемся к цели.
        move.setTurn(angle);

        // Если цель перед нами, ...
        if (StrictMath.abs(angle) < game.getStaffSector() / 2.0D) {
            // ... то атакуем.
            move.setAction(ActionType.MAGIC_MISSILE);
            move.setCastAngle(angle);
            move.setMinCastDistance(distance - unit.getRadius() + game.getMagicMissileRadius());
        }
    }

    public void attackWithoutTurn(Move move, LivingUnit unit) {
        double distance = self.getDistanceTo(unit);
        double angle = self.getAngleTo(unit);

        if (StrictMath.abs(angle) < game.getStaffSector() / 2.0D) {
            // ... то атакуем.
            move.setAction(ActionType.MAGIC_MISSILE);
            move.setCastAngle(angle);
            move.setMinCastDistance(distance - unit.getRadius() + game.getMagicMissileRadius());
        }
    }
}
