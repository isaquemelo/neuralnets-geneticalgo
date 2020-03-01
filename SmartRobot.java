package SmartRobots;

import SmartRobots.Utility.NeuralNetwork;
import SmartRobots.Utility.robotStatus;
import robocode.*;

import java.awt.*;

public class SmartRobot extends AdvancedRobot {
    private NeuralNetwork nn = new NeuralNetwork(4,4, 2, 5);
    private double seeRobot = 0;
    private int lifeTime = 0;
    private int enemyBulletHitCounter = 0;
    private int onScannedRobotCounter = 0;
    private int missedBulletsCounter = 0;
    private int wallCollisions = 0;
    private double maxBattleTime = 300; // turns
    private int firedBullets = 0;

    public void run() {
        System.out.println(getName());
        double[] input;

        setColors(Color.red, Color.blue, Color.green);

        while(true) {
            // AI prediction

            input = new double[] {
                    this.seeRobot,
                    getHeadingRadians() / (2*Math.PI),
                    getGunHeadingRadians() / (2*Math.PI),
                    getRadarHeadingRadians() / (2*Math.PI),
            };


            double[][] output = nn.predict(input).getData();
            //System.out.println("Output: " + Arrays.deepToString(output));


            // Run action
            setAhead(output[0][0] * 100);
            setTurnRightRadians(output[1][0] * 2 * Math.PI);
            setTurnGunRightRadians(output[2][0] * 2 * Math.PI);
            setTurnRadarRightRadians(output[3][0] * 2 * Math.PI);
            if (output[4][0] > 0.5) {
                setFire(Rules.MAX_BULLET_POWER / 2);
                this.firedBullets++;
            }

            seeRobot = 0;
            lifeTime++;

            execute();
        }

    }

    public void onScannedRobot(ScannedRobotEvent e) {
        this.seeRobot = 1;
        this.onScannedRobotCounter++;
    }

    @Override
    public void onBulletHit(BulletHitEvent event) {
        super.onBulletHit(event);
        this.enemyBulletHitCounter++;
    }

    @Override
    public void onHitWall(HitWallEvent event) {
        super.onHitWall(event);
        this.wallCollisions++;
    }

    @Override
    public void onBulletMissed(BulletMissedEvent event) {
        super.onBulletMissed(event);
        this.missedBulletsCounter++;
    }

    @Override
    public void onRoundEnded(RoundEndedEvent event) {
        //out.println("Score: " + new robotStatus(this).getTotalScore());

    }

    @Override
    public void onWin(WinEvent event) {
        this.enemyBulletHitCounter += 50;
        out.println("The round has ended to me, VITORYYYYYYYYYYYYYYYYYYY");
    }

    @Override
    public void onDeath(DeathEvent event) {
        out.println("The round has ended to me");
    }

    public int getLifeTime() {
        return lifeTime;
    }

    public int getEnemyBulletHitCounter() {
        return enemyBulletHitCounter;
    }

    public int getOnScannedRobotCounter() {
        return onScannedRobotCounter;
    }

    public int getMissedBulletsCounter() {
        return missedBulletsCounter;
    }

    public int getWallCollisions() {
        return wallCollisions;
    }

    public double getMaxBattleTime() {
        return maxBattleTime;
    }

    public int getFiredBullets() {
        return firedBullets;
    }
}