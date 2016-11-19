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

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

/**
 * This OpMode uses the common Pushbot hardware class to define the devices on the
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

@TeleOp(name="TriBotTeleOp")
@Disabled
public class TriBotTeleOp extends LinearOpMode {
    DcMotor motorLeft;
    DcMotor motorRight;
    DcMotor motorSlapshot;
    @Override
    public void runOpMode() {
        double left;
        double right;
        double max;
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        motorSlapshot = hardwareMap.dcMotor.get("motorSlapshot");
        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorSlapshot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorSlapshot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        telemetry.addData("Say", "I'm waiting for you to PRESS THE BIG TRIANGLE!!!");
        telemetry.update();
        waitForStart();
        while (opModeIsActive()) {
            left  = -gamepad1.left_stick_y + gamepad1.right_stick_x;
            right = -gamepad1.left_stick_y - gamepad1.right_stick_x;
            max = Math.max(Math.abs(left), Math.abs(right));
            if (max > 1.0) {
                left /= max;
                right /= max;
            }
            if (gamepad1.right_bumper) {
                Shoot();
            }
            motorLeft.setPower(left);
            motorRight.setPower(right);
            idle();
        }
    }
    public void TurnTicks(double power, int distance, DcMotor motor){
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setTargetPosition(distance);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(power);
        while(motor.isBusy()&&opModeIsActive()){
            // wait for motor to reach position
        }
        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void SetPower(double left, double right){
        motorLeft.setPower(left);
        motorRight.setPower(right);

    }
    public void StopDriving(){
        SetPower(0,0);
    }
    public void Shoot(){
//        motorSlapshot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        motorSlapshot.setTargetPosition(45);
//        motorSlapshot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        motorSlapshot.setPower(.5);
//        while(motorSlapshot.isBusy()&&opModeIsActive()){
//            // wait for motor to reach position
//        }
//        motorSlapshot.setPower(0);
//        TurnTicks(.5,-7,motorSlapshot);
//        telemetry.addData("Say", "Done going forward.");
//        telemetry.update();
//        TurnTicks(.5,7,motorSlapshot);
        motorSlapshot.setPower(0);
        int startPosition = motorSlapshot.getCurrentPosition();
        motorSlapshot.setPower(1);
        while(motorSlapshot.getCurrentPosition()<startPosition-140){
            // Waiting...
        }
        motorSlapshot.setPower(0);
    }
}
