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

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous(name="TriBotAutoBCBeacon0")

public class TriBotAutoCornerBeaconBlueZero extends LinearOpMode {
    DcMotor motorLeft;
    DcMotor motorRight;
    ColorSensor colorSensor;
    double motorRevTicks = 1440;
    double gearRatio = 0.5;
    double wheelDiameterIn = 12.56637061459172;
    double wheelDiameterCm = 31.91858136106297;
    double wheelRevTicks = motorRevTicks/gearRatio;
    double ticksPerInch = 229.18311804808813395;
    double ticksPerCm = 90.22957403468036431;
    double ticksPerDegrees = 0;
    double drivePower = 1;
    double inchesPerRightTurn = 9.75;
    boolean colorReached = false;
    @Override public void runOpMode() {
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        float hsvValues[] = {0F,0F,0F};
        final float values[] = hsvValues;
        final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(com.qualcomm.ftcrobotcontroller.R.id.RelativeLayout);
        colorSensor = hardwareMap.colorSensor.get("sensor_color");
        colorSensor.enableLed(false);
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

//        motorLeft.setDirection(DcMotor.Direction.REVERSE);
        motorRight.setDirection(DcMotor.Direction.REVERSE);
        waitForStart();
        //GO
        WaitMillis(0);
        //waiting 10 seconds
        DriveBackwards(0.75, InchesToTicks(45));
        PivotRight(0.75, InchesToTicks(-12));
        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);
        while(colorSensor.blue()<1.5&&colorSensor.red()<1.5){
            Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);
            SetPower(-0.5, -0.5);
        }
        SetPower(0,0);
        Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);
        if(colorSensor.red()<colorSensor.blue()&&colorSensor.red()>1){
            DriveBackwards(0.5, InchesToTicks(10));
            TankLeft(0.5, InchesToTicks(inchesPerRightTurn/2));
        }else if(colorSensor.red()>colorSensor.blue()|colorSensor.red()+colorSensor.blue()<2){
            DriveBackwards(0.5, InchesToTicks(6));
            Color.RGBToHSV(colorSensor.red() * 8, colorSensor.green() * 8, colorSensor.blue() * 8, hsvValues);
            telemetry.addData("Clear", colorSensor.alpha());
            telemetry.addData("Red  ", colorSensor.red());
            telemetry.addData("Green", colorSensor.green());
            telemetry.addData("Blue ", colorSensor.blue());
            telemetry.addData("Hue", hsvValues[0]);
            telemetry.update();
            if(colorSensor.red()<colorSensor.blue()&&colorSensor.blue()>1){
                telemetry.addData("Button","2");
                DriveBackwards(0.5, InchesToTicks(10));
                telemetry.addData("Task","Back");
                TankLeft(0.5, InchesToTicks(inchesPerRightTurn/2));
                telemetry.addData("Task","Tank");
            }else{
                telemetry.addData("Debug:", "Neither beacons are blue!");
            }
        }else {
            telemetry.addData("Debug:", "Neither beacons are anything!");
        }
        WaitMillis(10000);
        telemetry.addData("Say", "I am done.");
        telemetry.update();
    }


    public void WaitMillis (long millis){
        try {
            Thread.sleep(millis);
        }
        catch(InterruptedException ex){
            Thread.currentThread().interrupt();
        }
    }
    public void DriveForward(double power, int distance){
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLeft.setTargetPosition(distance);
        motorRight.setTargetPosition(distance);
        SetPower(power,power);
        while(opModeIsActive()&&motorLeft.isBusy() && motorRight.isBusy()){
            // wait for motor to reach position
        }
        StopDriving();
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void DriveBackwards(double power, int distance){
        DriveForward(-power,-distance);
    }
    public void TankRight(double power, int distance){
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeft.setTargetPosition(distance);
        motorRight.setTargetPosition(-distance);
        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        SetPower(power,-power);
        while(opModeIsActive()&& motorLeft.isBusy() && motorRight.isBusy()){
            // wait for motor to reach position
        }
        StopDriving();
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void TankLeft(double power, int distance){
        TankRight(-power,-distance);
    }
    public void PivotRight(double power, int distance){
        motorRight.setPower(0);
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLeft.setTargetPosition(distance);
        motorLeft.setPower(1);
        while(opModeIsActive()&&motorLeft.isBusy()){
            // wait for motor to reach position
        }
        motorLeft.setPower(0);
        motorLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void PivotLeft(double power, int distance){
        motorLeft.setPower(0);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setTargetPosition(distance);
        motorRight.setPower(1);
        while(opModeIsActive()&&motorRight.isBusy()){
            // wait for motor to reach position
        }
        motorRight.setPower(0);
        motorRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
    public void SetPower(double left, double right){
        motorLeft.setPower(left);
        motorRight.setPower(right);
    }
    public void StopDriving(){
        SetPower(0,0);
    }
    public void SetPowerEncoder(double leftPower, double rightPower, int leftTarget, int rightTarget){
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorLeft.setTargetPosition(leftTarget);
        motorRight.setTargetPosition(rightTarget);
        SetPower(leftPower,rightPower);
        while(opModeIsActive()&&(motorLeft.isBusy() || motorRight.isBusy())){
            if (!motorLeft.isBusy()){
                motorLeft.setPower(0);
                motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
            if (!motorRight.isBusy()){
                motorRight.setPower(0);
                motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            }
        }
        StopDriving();
        motorLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public int InchesToTicks(double inches){
        int ticks = (int) Math.round(inches*ticksPerInch);
        return ticks;
    }
    public int DegreesToTicks(double degrees){
        int ticks = (int) Math.round(degrees*ticksPerDegrees);
        return ticks;
    }
}
