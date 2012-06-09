//once the beacon is detected, the robot stops.
package org.wintrisstech.erik.iaroc;

import android.os.SystemClock;
import dalvik.bytecode.Opcodes;
import ioio.lib.api.IOIO;
import ioio.lib.api.exception.ConnectionLostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.wintrisstech.irobot.ioio.IRobotCreateInterface;

/**
 * A Ferrari is an implementation of the IRobotCreateInterface.
 *
 * @author Erik
 */
public class JackMagic extends Ferrari
{
    private static final String TAG = "Ferrari";
    private static final int RED_BUOY_CODE = 248;
    private static final int GREEN_BUOY_CODE = 244;
    private static final int FORCE_FIELD_CODE = 242;
    private static final int BOTH_BUOY_CODE = 252;
    private static final int RED_BUOY_FORCE_FIELD_CODE = 250;
    private static final int GREEN_BUOY_FORCE_FIELD_CODE = 246;
    private static final int BOTH_BUOY_FORCE_FIELD_CODE = 254;
    /*
     * The maze can be thought of as a grid of quadratic cells, separated by
     * zero-width walls. The cell width includes half a pipe diameter on each
     * side, i.e the cell edges pass through the center of surrounding pipes.
     * <p> Row numbers increase northward, and column numbers increase eastward.
     * <p> Positions and direction use a reference system that has its origin at
     * the west-most, south-most corner of the maze. The x-axis is oriented
     * eastward; the y-axis is oriented northward. The unit is 1 mm. <p> What
     * the Ferrari knows about the maze is:
     */
    private final static int NUM_ROWS = 12;
    private final static int NUM_COLUMNS = 4;
    private final static int CELL_WIDTH = 712;
    /*
     * State variables:
     */
    private int speed = 300; // The normal speed of the Ferrari when going straight
    // The row and column number of the current cell. 
    private int row;
    private int column;
    private boolean running = true;
    private final static int SECOND = 1000; // number of millis in a second
    private int[] c =
    {
        60, 200
    };
    private int[] e =
    {
        64, 200
    };
    private int[] g =
    {
        67, 200
    };

    /**
     * Constructs a Ferrari, an amazing machine!
     *
     * @param ioio the IOIO instance that the Ferrari can use to communicate
     * with other peripherals such as sensors
     * @param create an implementation of an iRobot
     * @param dashboard the Dashboard instance that is connected to the Ferrari
     * @throws ConnectionLostException
     */
    public JackMagic(IOIO ioio, IRobotCreateInterface create, Dashboard dashboard) throws ConnectionLostException
    {
        super(ioio, create, dashboard);
    }

    /**
     * Main method that gets the Ferrari running.
     *
     */
    public void run()
    {
        dashboard.speak("i am jack version 10");
        int distance = 0;
        int angle = 0;
        while (distance < 600)
        {
            try
            {
                driveDirect(100, 200);
                readSensors(SENSORS_GROUP_ID6);
                distance = getDistance() + distance;
                angle = getAngle() + angle;
            } catch (ConnectionLostException ex)
            {
                Logger.getLogger(JackMagic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        while (angle > 0)
        {
            try
            {
                readSensors(SENSORS_GROUP_ID6);
                angle = getAngle() + angle;
                driveDirect(100, -100);

            } catch (ConnectionLostException ex)
            {
                Logger.getLogger(JackMagic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try
        {
            driveDirect(0, 0);
        } catch (ConnectionLostException ex)
        {
            Logger.getLogger(JackMagic.class.getName()).log(Level.SEVERE, null, ex);
        }
        try
        {
            readSensors(SENSORS_GROUP_ID6);//Resets all counters in the Create to 0
        } catch (ConnectionLostException ex)
        {
            Logger.getLogger(JackMagic.class.getName()).log(Level.SEVERE, null, ex);
        }
        distance = getDistance();
        angle = getAngle();
        while (true)
        {
            dashboard.log(angle + "<-- angle");
            dashboard.log(distance + "<-- distance");
            SystemClock.sleep(1000);
            try
            {
                readSensors(SENSORS_GROUP_ID6);
            } catch (ConnectionLostException ex)
            {
                Logger.getLogger(JackMagic.class.getName()).log(Level.SEVERE, null, ex);
            }
            angle = getAngle();
            distance = getDistance();
        }
//        try
//        {
//            //StateControllerInterface jackStateController = new StateControllerVic(delegate, dashboard);
//            //jackStateController.startStateController();
//            StateControllerInterface jackStateController = new StateControllerJackBasic(delegate, dashboard);
//            jackStateController.startStateController();
//            //wallHugger();
//            //readBeacon();
//        } catch (Exception ex)
//        {
//            dashboard.log("problem: " + ex.getMessage());
//        }
//        dashboard.log("Run completed.");
//        setRunning(false);
//        shutDown();
//        setRunning(false);
    }

    private void wallHugger()
    {

        dashboard.speak("hugging wall");
        while (true)
        {
            try
            {
                readSensors(SENSORS_BUMPS_AND_WHEEL_DROPS);
                driveDirect(500, 500);
                if (isBumpRight())
                {
                    driveDirect(-500, 500);
                    SystemClock.sleep(300);
                    driveDirect(500, 500);
                }
            } catch (ConnectionLostException ex)
            {
            }
        }
    }
//    public void readBeacon()
//    {
//        try
//        {
//            dashboard.log("hahahahhah");
//            dashboard.speak("Ha Ha Ha Ha Ha");
//            readSensors(SENSORS_INFRARED_BYTE);
//            if (getInfraredByte() != 255)
//            {
//                dashboard.log("Sensing Sensing Sensing");
//                dashboard.speak("Sensing Sensing Sensing");
//                this.demo(1);
//            }
//            //    private static final int RED_BUOY_CODE = 248;
//            //    private static final int GREEN_BUOY_CODE = 244;
//            //    private static final int FORCE_FIELD_CODE = 242;
//            //    private static final int BOTH_BUOY_CODE = 252;
//            //    private static final int BOTH_BUOY_FORCE_FIELD_CODE = 254;
//            //    private static final int GREEN_BUOY_FORCE_FIELD_CODE = 246;
//            //    private static final int BOTH_BUOY_FORCE_FIELD_CODE = 254;
//        } catch (ConnectionLostException ex)
//        {
//            dashboard.log("Reading infrared sensors!");
//        }
//    }
}
//hello people
