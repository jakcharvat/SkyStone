package org.firstinspires.ftc.teamcode.prefs;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * This is a class for globally defining all sensors and motors on a robot. Use this
 * class to access all the robot's sensors and motors.
 */
public class RobotSetup {

    /**
     * Choose the type of robot the program will be run on. The class initializer
     */
    private BaseType _baseType = BaseType.fourWheeler;

    private TwoWheelerSetup _twoWheelerSetup;
    private FourWheelerSetup _fourWheelerSetup;

    /**
     * Initialize the robot to work with the default base type set up in the [RobotSetup] class
     *
     * @param hardwareMap`A reference to the hardwareMap variable provided by LinearOpMode
     */
    public RobotSetup(HardwareMap hardwareMap) {
        init(hardwareMap);
    }

    /**
     * Initialize the robot to work with a base type other than the default one set up in
     * the [RobotSetup] class
     *
     * @param hardwareMap A reference to the hardwareMap variable provided by LinearOpMode
     * @param baseType A member of the [BaseType] enum, specifying which config to initialize this class to work with
     */
    public RobotSetup(HardwareMap hardwareMap, BaseType baseType) {
        this._baseType = baseType;

        init(hardwareMap);

    }

    /**
     * Shared logic for initializing the class based on the [baseType] variable
     *
     * @param hardwareMap A reference to the hardwareMap variable provided by LinearOpMode
     */
    private void init(HardwareMap hardwareMap) {
        if (_baseType == BaseType.twoWheeler) {
            _twoWheelerSetup = new TwoWheelerSetup(hardwareMap);
        } else if (_baseType == BaseType.fourWheeler) {
            _fourWheelerSetup = new FourWheelerSetup(hardwareMap);
        }
    }

    public BaseType baseType() {
        return _baseType;
    }

    public TwoWheelerSetup twoWheelerSetup() {
        return _twoWheelerSetup;
    }

    public FourWheelerSetup fourWheelerSetup() {
        return _fourWheelerSetup;
    }
}
