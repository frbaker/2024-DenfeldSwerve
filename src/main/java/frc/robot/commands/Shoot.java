// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.List;

import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Controls;
import frc.robot.subsystems.AprilTagOdometry;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.swerve.SwerveModule;

public class Shoot extends Command {

  Turret turret;
  Controls controls;
  SwerveDrive swerveDrive;

  /** Creates a new Shoot. */
  public Shoot(
    Turret turret, 
    Controls controls, 
    SwerveDrive swerveDrive
  ) {
    this.turret = turret;
    this.controls = controls;
    this.swerveDrive = swerveDrive;
    addRequirements(turret, swerveDrive);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // Grab target position from april tag
    Translation2d targetPose = new Translation2d();
    if (DriverStation.getAlliance().isPresent() && DriverStation.getAlliance().get() == Alliance.Red) {
      targetPose = AprilTagOdometry.aprilTagFieldLayout.getTagPose(4).get().getTranslation().toTranslation2d();
    } else {
      targetPose = AprilTagOdometry.aprilTagFieldLayout.getTagPose(7).get().getTranslation().toTranslation2d();
    }
    // Calculate distance
    double distance = targetPose.getDistance(swerveDrive.getPosition().getTranslation());
    
    //get flywheels are up to speed
    boolean atShooterSpeed = turret.setFlyWheelSpeed(Constants.Turret.flyWheelSpeed);
    //aim shooter
    double angle = -controls.operate.getThrottle() * Constants.Turret.aimRangeFrom0; // todo: implement april tags
    turret.setAngle(angle);

    SmartDashboard.putNumber("Distance to target", distance);
    SmartDashboard.putNumber("Shooter Angle", angle);

    //aim drive train
    ChassisSpeeds speeds = ChassisSpeeds.fromFieldRelativeSpeeds(
      new ChassisSpeeds(
        controls.getForward() * SwerveModule.maxMetersPerSecond,
        controls.getLateral() * SwerveModule.maxMetersPerSecond,
        controls.getTurn() * SwerveModule.maxRadPerSecond
      ), 
      SwerveDrive.navxGyro.getRotation2d()
    );
    swerveDrive.drive(speeds);
    //if flywheels up to speed, shooter aimed, drive train aimed, then feed in
    if (atShooterSpeed && turret.atTargetAngle() && controls.getOperatorButton(4).getAsBoolean()) {
      turret.feed();
    }
    else {
      turret.stopFeed();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    turret.stopShooter();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
