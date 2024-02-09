// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto.util;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Translation2d;

public class Field {

    /**
     * X distance from blue zero to red zero
     */
    public static final double fieldLengthMeters = 16.54175;

    /**
     * Y distance from red zero to blue zero
     */
    public static final double fieldWidthMeters = 8.22325;

    /**
     * Translates a position relative to the blue 
     * alliance zero to a position relative to a red
     * alliance zero.
     * @param bluePosition
     * @return
     */
    public static Translation2d translateRobotPoseToRed(Translation2d bluePosition) {
        return new Translation2d(
            bluePosition.getX(), 
            fieldWidthMeters - bluePosition.getY()
        );
    }

    /**
     * Translates an april tag position relative to
     * the blue alliance zero to a position relative
     * to a red alliance zero
     * @param bluePosition
     * @return
     */
    public static Pose3d translateAprilTagPoseToRed(Pose3d bluePosition) {
        return new Pose3d(
            fieldLengthMeters - bluePosition.getX(),
            fieldWidthMeters - bluePosition.getY(),
            bluePosition.getZ(),
            bluePosition.getRotation()
        );
    }

}