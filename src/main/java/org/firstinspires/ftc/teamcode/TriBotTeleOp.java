package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;



import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

@TeleOp(name="TriBotTeleOp")
public class TriBotTeleOp extends LinearOpMode {
    DcMotor motorLeft;
    DcMotor motorRight;
    DcMotor motorSlapshot;
    boolean shot = false;
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
        motorSlapshot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorRight.setDirection(DcMotor.Direction.REVERSE);
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
            if (gamepad2.right_bumper) {
                motorSlapshot.setPower(0);
                int startPosition = motorSlapshot.getCurrentPosition();
                telemetry.addData("Start P", startPosition);
                telemetry.update();
                motorSlapshot.setPower(1);
                while (motorSlapshot.getCurrentPosition()<startPosition-300) {
                    // Waiting...
                    telemetry.addData("Current P", motorSlapshot.getCurrentPosition());
                    telemetry.update();
                }
                motorSlapshot.setPower(0);
            } else if (gamepad2.left_bumper) {
                motorSlapshot.setPower(0);
                int startPositionr = motorSlapshot.getCurrentPosition();
                telemetry.addData("Start P", startPositionr);
                telemetry.update();
                motorSlapshot.setPower(-1);
                while(motorSlapshot.getCurrentPosition()>startPositionr+300){
                    // Waiting...
                    telemetry.addData("Current P", motorSlapshot.getCurrentPosition());
                    telemetry.update();
                }
                motorSlapshot.setPower(0);
            }
            motorLeft.setPower(left);
            motorRight.setPower(right);
            idle();
        }
    }
    public void TurnTicks(double power, int distance, DcMotor motor){
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setTargetPosition(distance);
        motor.setPower(power);
        while(motor.isBusy()&&opModeIsActive()){
            // wait for motor to reach position
        }
        motor.setPower(0);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
    public void SetPower(double left, double right){
        motorLeft.setPower(left);
        motorRight.setPower(right);
    }
    public void StopDriving(){
        SetPower(0,0);
    }
    public void Shoot(){
        motorSlapshot.setPower(0);
        int startPosition = motorSlapshot.getCurrentPosition();
        telemetry.addData("Start P", startPosition);
        telemetry.update();
        motorSlapshot.setPower(-1);
        while (motorSlapshot.getCurrentPosition() > startPosition + 300) {
            // Waiting...
            telemetry.addData("Current P", motorSlapshot.getCurrentPosition());
            telemetry.update();
        }
        motorSlapshot.setPower(0);
    }
    public void Retract(){
        motorSlapshot.setPower(0);
        int startPosition = motorSlapshot.getCurrentPosition();
        telemetry.addData("Start P", startPosition);
        telemetry.update();
        motorSlapshot.setPower(1);
        while(motorSlapshot.getCurrentPosition()<startPosition-300){
            // Waiting...
            telemetry.addData("Current P", motorSlapshot.getCurrentPosition());
            telemetry.update();
        }
        motorSlapshot.setPower(0);
    }
}
