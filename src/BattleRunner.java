import robocode.BattleResults;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.BattleCompletedEvent;
import robocode.control.events.BattleErrorEvent;

public class BattleRunner {
    RobocodeEngine engine;
    BattlefieldSpecification battlefield;

    public BattleRunner() {
        engine = new RobocodeEngine(new java.io.File("C:/Robocode"));
        engine.setVisible(true);
        battlefield = new BattlefieldSpecification(800, 600);

        String[] rivalsBatch1 = {
                //"sample.SuperCrazy",
                //"sample.SuperTracker"
                //"sample.SuperTrackFire",
                "sample.SuperRamFire",
                //"ary.micro.Weak 1.2"
                //"sheldor.nano.Sabreur_1.1.1"
                //"sample.Sabreur"
                //"mld.LittleBlackBook_1.69e"
                //"mld.Moebius_2.9.3"
        };

        BattleResults results;


        Robot robot = new Robot();
        robot.compile();


        RobotSpecification[] selectedBots = engine.getLocalRepository( "Robot*, Robot*, Robot*, Robot*");
        BattleSpecification battleSpec = new BattleSpecification(2, battlefield, selectedBots);
        BattleObserver observer = new BattleObserver();

        engine.addBattleListener(observer);

        engine.runBattle(battleSpec, true);
        engine.setVisible(false);

        results = observer.getResults()[0];
        System.out.println(results.getTeamLeaderName());

    }
}


class BattleObserver extends BattleAdaptor {

    robocode.BattleResults[] results;

    public void onBattleCompleted(BattleCompletedEvent e){
        results = e.getIndexedResults();
    }

    public void onBattleError(BattleErrorEvent e){
        System.out.println("Error running battle: " + e.getError());
    }

    public BattleResults[] getResults(){
        return results;
    }

}