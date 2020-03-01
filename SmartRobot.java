package SmartRobots;

import SmartRobots.Utility.NeuralNetwork;
import SmartRobots.Utility.robotStatus;
import robocode.*;

import java.awt.*;
import java.io.*;
import java.util.Arrays;

public class SmartRobot extends AdvancedRobot implements Serializable {
    private NeuralNetwork brain = new NeuralNetwork(4,4, 2, 5);
    private double seeRobot = 0;
    private int lifeTime = 0;
    private int enemyBulletHitCounter = 0;
    private int onScannedRobotCounter = 0;
    private int missedBulletsCounter = 0;
    private int wallCollisions = 0;
    private double maxBattleTime = 300; // turns
    private int firedBullets = 0;
    private double finalResult = -99999999;
    private double fitness = 0;


    public void run() {
        this.loadNeuralNetwork(getName());
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


            double[][] output = brain.predict(input).getData();
            //System.out.println("Output: " + Arrays.deepToString(output));


            // Run action
            setAhead(output[0][0] * 100);
            setTurnRightRadians(output[1][0] * 2 * Math.PI);
            setTurnGunRightRadians(output[2][0] * 2 * Math.PI);
            setTurnRadarRightRadians(output[3][0] * 2 * Math.PI);
            if (output[4][0] > 0) {
                setFire(Rules.MAX_BULLET_POWER / 2);
                this.firedBullets++;
            }

            seeRobot = 0;
            lifeTime++;

            execute();
        }

    }

    private void loadNeuralNetwork(String robot_name) {
        String fileName = robot_name.replace("*", "");

        System.out.println("Attempt to load neural network. File name: " + fileName);

        SmartRobot importedRobot;

        try {
            File fileTest = new File(String.valueOf(this.getDataFile(fileName)));

            if (fileTest.length() == 0) {
                System.out.println("File does not exist, using itself NeuralNet...");
                importedRobot = this;

            } else {

                FileInputStream file = new FileInputStream(this.getDataFile(fileName));
                ObjectInputStream in = new ObjectInputStream(file);

                // Method for deserialization of object
                importedRobot = (SmartRobot) in.readObject();

                in.close();
                file.close();

                System.out.println("Success importing the NN file.");
            }

            this.brain = importedRobot.brain;

        } catch (Exception e) {
            e.printStackTrace();
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
    public void onWin(WinEvent event) {
        this.endOfLife();
    }

    @Override
    public void onDeath(DeathEvent event) {
        this.endOfLife();
    }

    public void endOfLife(){
        out.println("End of life of robot " + this.getName());
        this.finalResult = new robotStatus(this).getTotalScore();
        this.exportRobot();
    }

    public void exportRobot() {
        try {
            File file = getDataFile(this.getName());
            RobocodeFileOutputStream outputStream = new RobocodeFileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(outputStream);
            out.writeObject(this);

            out.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Object has been serialized");
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

    @Override
    public String toString() {
        return "SmartRobot{" +
                "brain=" + brain +
                ", seeRobot=" + seeRobot +
                ", lifeTime=" + lifeTime +
                ", enemyBulletHitCounter=" + enemyBulletHitCounter +
                ", onScannedRobotCounter=" + onScannedRobotCounter +
                ", missedBulletsCounter=" + missedBulletsCounter +
                ", wallCollisions=" + wallCollisions +
                ", maxBattleTime=" + maxBattleTime +
                ", firedBullets=" + firedBullets +
                ", finalResult=" + finalResult +
                ", fitness=" + fitness +
                '}';
    }
}