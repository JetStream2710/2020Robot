package frc.robot.util;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

public class MotorFactory {

    private MotorFactory() {}

    private static final Logger logger = new Logger(MotorFactory.class.getName());

    public static WPI_TalonSRX makeTalon(int id, String name){
        WPI_TalonSRX talon = new WPI_TalonSRX(id);
        talon.setSafetyEnabled(false);
        talon.setNeutralMode(NeutralMode.Brake);
        talon.configVoltageCompSaturation(12);
        talon.enableVoltageCompensation(true);   
        logger.detail("created " + name + " talon with " + id);
        return talon;
    }

    public static WPI_VictorSPX makeVictor(int id, String name){
        WPI_VictorSPX victor = new WPI_VictorSPX(id);
        victor.setSafetyEnabled(false);
        victor.setNeutralMode(NeutralMode.Brake);
        victor.configVoltageCompSaturation(12);
        victor.enableVoltageCompensation(true);   
        logger.detail("created " + name + " victor with " + id);
        return victor;
    }
}