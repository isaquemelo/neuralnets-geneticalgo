import robocode.*;
import robocode.util.Utils;
import java.awt.Color;
import java.io.*;
import java.util.Arrays;


public class Robot extends AdvancedRobot {
    private String botName = "Robot";
    public NeuralNetwork brain;


    public Robot() {
        brain = new NeuralNetwork(2, 4, 2);
    }

    public void run() {
        setColors(Color.green, Color.green, Color.green);

        System.out.println("name: " + this.getName());
        double [][] input = new double[][]{
                new double[] {1, 0.3},
        };

        System.out.println(brain.predict(input).toString());

        while (true) turnGunRight(20000);

    }

    public void onScannedRobot(ScannedRobotEvent e) {

    }


    void compile() {
        // Compile code
        String PATH = "C:\\robocode\\robots\\";
        String JARS = "C:\\robocode\\libs\\robocode.jar";


        try {
            execute("javac -cp \"" + JARS + ";C:\\Users\\Isaque Melo\\Desktop\\robocode-nn-ga\\out\\production\\robocode-nn-ga;\" -d " + PATH + " src\\" + this.botName + ".java");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void execute(String command) throws Exception {
        Process process = Runtime.getRuntime().exec(command);
        printMsg(command + " stdout:", process.getInputStream());
        printMsg(command + " stderr:", process.getErrorStream());
        process.waitFor();

        if (process.exitValue() != 0) System.out.println(command + "exited with value " + process.exitValue());
    }

    private static void printMsg(String name, InputStream ins) throws Exception {
        String line = null;
        BufferedReader in = new BufferedReader(new InputStreamReader(ins));
        while ((line = in.readLine()) != null) {
            System.out.println(name + " " + line);
        }
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
