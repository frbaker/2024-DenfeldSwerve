// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.auto.pathing.AutoRotationSource;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.IntakeSubsystem.intakePosition;

public class Intake extends Command implements AutoRotationSource{
  IntakeSubsystem intake;
  /**
   * Runs the intake until the sensor is activated
   * @param intake
   */
  public Intake(IntakeSubsystem intake) {
    addRequirements(intake);
    this.intake = intake;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.setIntake();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return intake.getIntakeSensor();
  }

  @Override
  public Rotation2d getGoalRotation() {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getGoalRotation'");
  }
}
