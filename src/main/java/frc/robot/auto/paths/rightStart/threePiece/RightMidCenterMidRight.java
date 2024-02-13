// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto.paths.rightStart.threePiece;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.auto.pathing.pathObjects.Path;
import frc.robot.auto.pathing.pathObjects.PathPoint;

/**
 * Example pure pursuit path, showing a basic
 * and readable implementation.
 */
public class RightMidCenterMidRight extends Path {
    /**
     * Drives the robot 1.5 meters along the x
     * axis (which should be forward, if the settings
     * are properly configured within the PathingConstants
     * class), at the robots maximum speed.
     */
    public RightMidCenterMidRight() {
        /**
         * Initializes Path super with the given
         * set of points, the first point passed
         * into super is the first point along the path.
         */
        super(            
            new PathPoint(
                new Translation2d(0,1.622),               // Position (meters)
                new Rotation2d(Math.toRadians(0)),     // Rotation (rad)
                5,    // Speed (m/s)
                new PrintCommand("Shoot")       // Command 
            ),
            new PathPoint(
                new Translation2d(8.108,2.433),               // Position (meters)
                new Rotation2d(Math.toRadians(0)),     // Rotation (rad)
                5,    // Speed (m/s)
                new PrintCommand("Shoot")       // Command 
            ),
            new PathPoint(
                new Translation2d(8.108,0.811),               // Position (meters)
                new Rotation2d(Math.toRadians(Math.PI/2)),     // Rotation (rad)
                1,    // Speed (m/s)
                new PrintCommand("Shoot")       // Command 
            )
        );
    }

}