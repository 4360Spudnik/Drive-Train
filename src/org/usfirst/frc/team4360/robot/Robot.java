package org.usfirst.frc.team4360.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team4360.robot.commands.ExampleCommand;
import org.usfirst.frc.team4360.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;

	Command autonomousCommand;
	SendableChooser chooser;
	CameraServer server;

	private SpeedController motor1;
	private SpeedController motor2;
	private SpeedController motor3;
	private SpeedController motor4;
	private SpeedController motor5;
	private SpeedController motor6;
	private SpeedController motor7;

	private Joystick stick1;
	private Joystick stick2;


	private final double k_updatePeriod = 0.005;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	public void robotInit() {
		oi = new OI();
		chooser = new SendableChooser();
		chooser.addDefault("Default Auto", new ExampleCommand());
		//        chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);

		motor1 = new Talon(1);	
		motor2 = new Talon(2);
		motor3 = new Talon(3);
		motor4 = new Talon(4);
		motor5 = new Talon(5);
		motor6 = new Talon(6);
		motor7 = new Talon(7);

		stick1 = new Joystick(0);
		stick2 = new Joystick(1);
		
		server = CameraServer.getInstance();
		server.setQuality(50);
		server.startAutomaticCapture("cam0");
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	
	
	public void disabledInit(){

	}

	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString code to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the chooser code above (like the commented example)
	 * or additional comparisons to the switch structure below with additional strings & commands.
	 */
	public void autonomousInit() {
		autonomousCommand = (Command) chooser.getSelected();


		if (autonomousCommand != null) autonomousCommand.start();

		motor1.set(0.5);
		motor2.set(0.5);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	public void autonomousPeriodic() {
		Scheduler.getInstance().run(); //Motor Speeds
		motor1.set(0.5);
		motor2.set(0.5);


	}

	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to 
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null) autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		while (isOperatorControl() && isEnabled()) {
			// Set the motor's output.
			// This takes a number from -1 (100% speed in reverse) to +1 (100% speed going forward)

			if(stick1.getRawButton(1)){      
				motor1.set(stick2.getY()*-1);
				motor2.set(stick1.getY()*-1);
			} else{
				motor1.set(stick2.getY()*0.5*-1);
				motor2.set(stick1.getY()*0.5*-1);
			}
			Timer.delay(k_updatePeriod);	// wait 5ms to the next update


			if(stick2.getRawButton(3)){//elevation
				motor3.set(1);
			} else if(stick2.getRawButton(2)){
				motor3.set(-1);
			} else {
				motor3.set(0.00);
			}

			if(stick1.getRawButton(1)){//shooting
				motor4.set(-1);
				motor5.set(-1);
			} else{
				if(stick2.getRawButton(4)){
					motor4.set(0.5);
					motor5.set(0.5);
				} else if(stick2.getRawButton(5)){
					motor4.set(-0.5);
					motor5.set(-0.5);
				} else {
					motor4.set(0);
					motor5.set(0);
				}
					
					if(stick2.getRawButton(3)){//Arm
						motor6.set(0.5);
						motor7.set(0.5);
					} else if(stick2.getRawButton(4)){
						motor6.set(-0.5);
						motor7.set(-0.5);
					} 
				}
			}

}



	/**
	 * This function is called periodically during test mode
	 */
	public void testPeriodic() {
		LiveWindow.run();
	}


	public void operatorControl() {
		while (isOperatorControl() && isEnabled()) {
			// Set the motor's output.
			// This takes a number from -1 (100% speed in reverse) to +1 (100% speed going forward)

				motor1.set(stick1.getY()*0.5);
				motor2.set(stick2.getY()*0.5);
			}
			Timer.delay(k_updatePeriod);	// wait 5ms to the next update
						
	}
}