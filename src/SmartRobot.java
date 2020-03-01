import robocode.*;
import robocode.util.Utils;
import java.awt.Color;


/**
 * SmartRobot - a sample robot by Mathew Nelson.
 * <p>
 * This robot moves to a corner, then swings the gun back and forth.
 * If it dies, it tries a new corner in the next round.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */

public class SmartRobot extends AdvancedRobot {
    private NeuralNetwork nn = new NeuralNetwork(4,3,5);
    private double seeRobot = 0;
    private int lifeSpan = 0;

    public void run() {
        System.out.println(getName());

        double[] input;

        setColors(Color.red, Color.blue, Color.green);

        while(true) {
            // AI prediction
            if (lifeSpan % 700 == 0) {
                input = new double[] {
                        this.seeRobot,
                        0 / (2*Math.PI),
                        0 / (2*Math.PI),
                        0 / (2*Math.PI),
                };

                Matrix output = nn.predict(input);
                //System.out.println("Output: " + output);
            }



            lifeSpan++;

            // Run action
//            setAhead();
//
//            setTurnRight();
//
//            setTurnGunRight();
//
//            setTurnRadarRight();
//
//            setFire();

            //turnGunRight(Double.POSITIVE_INFINITY);
        }

    }

    public void onScannedRobot(ScannedRobotEvent e) {


    }

}






//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
