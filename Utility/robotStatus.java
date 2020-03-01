package SmartRobots.Utility;
import SmartRobots.SmartRobot;


public class robotStatus {
    SmartRobot robot;

    public robotStatus(SmartRobot robot) {
        this.robot = robot;
    }


    public String getName() {
        return robot.getName();
    }

    public double getTotalScore() {
        return (this.getTotalSurvivalScore() / robot.getMaxBattleTime()   +
                (double) robot.getEnemyBulletHitCounter() / robot.getOnScannedRobotCounter() == 0? 99999999 : robot.getOnScannedRobotCounter() +
                (double) robot.getEnemyBulletHitCounter() / robot.getFiredBullets() == 0? 99999999 : robot.getFiredBullets() +
                robot.getEnergy()) - ((double) robot.getMissedBulletsCounter() / robot.getFiredBullets() == 0? 99999999 : robot.getFiredBullets() +
                (double) robot.getWallCollisions() / robot.getLifeTime());
    }

    public double getTotalSurvivalScore() {
        return robot.getLifeTime();
    }

}