/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;


/**
 * This OpMode uses the common Pushbot hardware class to define the devices on the robot.
 * All device access is managed through the HardwarePushbot class.
 * The code is structured as a LinearOpMode

 * This particular OpMode executes a POV Game style Teleop for a PushBot
 * In this mode the left stick moves the robot FWD and back, the Right stick turns left and right.
 * It raises and lowers the claw using the Gampad Y and A buttons respectively.
 * It also opens and closes the claws slowly using the left and right Bumper buttons.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Tutorial_Autonomous")

public class Tutorial_Autonomous extends LinearOpMode {
       /* Declare OpMode members. */
    DcMotor motorLeft;
    DcMotor motorRight;
    //Servo armServo;
    //double motorRpm = 0;
    //double gearRatio = 0.5;
    //double wheelDiameter = 12.56637061459172;
    //double wheelRpm = 0;
    long inchesPerMinute = 0;
    long inchesPerMillis = inchesPerMinute/60000;
    double drivePower = 1;
    @Override public void runOpMode() {
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");

        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        // motorLeft.setDirection(DcMotor.Direction.REVERSE);
        // motorRight.setDirection(DcMotor.Direction.REVERSE);

        /** Servo Initialization EXAMPLE:
         * armServo = hardwareMap.servo.get("armServo");
         * armServo.setPosition(.8);
         */
        waitForStart();
        telemetry.addData("Say", "All systems are go!");
        //GO!!!
        Drive(1, 1, 1000);
        Drive(-1,-1, 1000);
        Brake(1000);
        DriveForward(drivePower, 1000);
        DriveBackwards(drivePower, 1000);
        TankLeft(drivePower, 1000);
        TankRight(drivePower, 1000);
        PivotLeft(drivePower, 1000);
        PivotLeft(-drivePower, 1000);
        PivotRight(drivePower, 1000);
        PivotRight(-drivePower, 1000);
        telemetry.addData("Say", "I am done.");
    }
    public void WaitMillis (long millis){
        try {
            Thread.sleep(millis);
        }
        catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }
    //public void WaitInches (long inches){
       // WaitMillis(inches/inchesPerMillis);
  //  }
    public void DriveForward(double power, long millis){
        motorLeft.setPower(power);
        motorRight.setPower(power);
        WaitMillis(millis);
    }
    public void DriveBackwards(double power, long millis){
        motorLeft.setPower(-power);
        motorRight.setPower(-power);
        WaitMillis(millis);
    }
    public void TankRight(double power, long millis){
        motorLeft.setPower(power);
        motorRight.setPower(-power);
        WaitMillis(millis);
    }
    public void TankLeft(double power, long millis){
        motorLeft.setPower(-power);
        motorRight.setPower(power);
        WaitMillis(millis);
    }
    public void PivotRight(double power, long millis){
        motorLeft.setPower(power);
        motorRight.setPower(0);
        WaitMillis(millis);
    }
    public void PivotLeft(double power, long millis){
        motorLeft.setPower(0);
        motorRight.setPower(power);
        WaitMillis(millis);
    }
    public void Drive(double left, double right, long millis){
        motorLeft.setPower(left);
        motorRight.setPower(right);
        WaitMillis(millis);
    }
    public void Brake(long millis){
        motorLeft.setPower(0);
        motorRight.setPower(0);
        WaitMillis(millis);
    }
}
