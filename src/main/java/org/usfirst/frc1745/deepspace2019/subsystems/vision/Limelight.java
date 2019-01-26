/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc1745.deepspace2019.subsystems.vision;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Add your docs here.
 */
public class Limelight extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  @Override
  public void initDefaultCommand() {

    // do nothing
  }

  //Constants to be adjusted for calculations
  private double KpAim = -0.1f;
  private double KpDistance = -0.1f;
  private double min_aim_command = 0.05f;

  //Get Network Table
  public NetworkTable getLimelightNetworkTable() {
    return NetworkTableInstance.getDefault().getTable("limelight"); 
  }

  //Uses NetworkTable values to calculate speed
  public double[] calcSpeed(NetworkTable table) {

    double tx = table.getEntry("tx").getDouble(0.0);
    double ty = table.getEntry("ty").getDouble(0.0);

    double headingError = -tx;
    double distanceError = -ty;
    double steeringAdjust = 0.0f;

    if (tx > 1.0) {
      steeringAdjust = KpAim * headingError - min_aim_command;
    } else if (tx < 1.0) {
      steeringAdjust = KpAim * headingError + min_aim_command;
    }

    double distanceAdjust = KpDistance * distanceError;

    double leftDelta = steeringAdjust + distanceAdjust;
    double rightDelta = -(steeringAdjust + distanceAdjust);
    
    //Sets left and right delta values in an array
    return new double[] { leftDelta, rightDelta };

  }

  //Getters and setters
  //Aim Constant
  public double getKpAim() {
    return KpAim;
  }
  
  public double setKpAim(double KpAim) {
    this.KpAim = KpAim;
    return KpAim;
  }
  //Distance Contants
  public double getKpDistance() {
    return KpDistance;
  }

  public double setKpDistance(double KpDistance) {
    this.KpDistance = KpDistance;
    return KpDistance;
  }
  //Minimum Aim Constant
  public double getMinAimCommand() {
    return min_aim_command;
  }

  public double setMinAimCommand(double min_aim_command) {
    this.min_aim_command = min_aim_command;
    return min_aim_command;
  }
}
