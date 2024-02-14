// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.Controls;
import frc.robot.ShotProfile;
import frc.robot.subsystems.AprilTagOdometry;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.SwerveDrive;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.IntakeSubsystem.intakePosition;
import frc.robot.subsystems.swerve.SwerveModule;

public class Shoot extends Command {

  Shooter shooter;
  Controls controls;
  SwerveDrive swerveDrive;
  AprilTagOdometry camera;

  PIDController aimingPidController = new PIDController(0.1, 0, 0);

  /** Creates a new Shoot. */
  public Shoot(
    Shooter shooter, 
    Controls controls, 
    SwerveDrive swerveDrive,
    AprilTagOdometry camera
  ) {
    this.shooter = shooter;
    this.controls = controls;
    this.swerveDrive = swerveDrive;
    this.camera = camera;
    
    addRequirements(shooter, swerveDrive);

    aimingPidController.setSetpoint(0);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    // Calculate distance
    double distance = camera.getDistanceToSpeaker();
    // Convert joystick value into a shooter angle
    double angle = -controls.operate.getThrottle() * Constants.Shooter.aimRangeFrom0;
    if (ShotProfile.getHeightFromDistance(distance).isPresent()) {
      //angle = -ShotProfile.getHeightFromDistance(distance).get() * Constants.Turret.aimRangeFrom0;
    }
    
    //get flywheels are up to speed
    boolean atShooterSpeed = shooter.setFlyWheelSpeed(Constants.Shooter.flyWheelSpeed);

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
    if (controls.getOperatorButton(4).getAsBoolean()) {
      shooter.feed();
    }
    else {
      shooter.stopFeed();
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    shooter.stopShooter();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}