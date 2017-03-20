package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name="TeleopUXO")
public class UXObotTeleop extends UXOAbstract {
    @Override
    public void runOpMode() {
        double front;
        double back;
        double left;
        double right;
        double turnAmount;
        double max;
        double sweeper;
        double flicker;
        double patio = 0;
        double dpadSpeed = 0.3;
        robot.init(hardwareMap);
        waitForStart();
        while (opModeIsActive()) {
            turnAmount = -gamepad1.right_stick_x;
            left = gamepad1.left_stick_y+turnAmount;
            back = gamepad1.left_stick_x+turnAmount;
            right = -gamepad1.left_stick_y+turnAmount;
            front = -gamepad1.left_stick_x+turnAmount;

            sweeper = gamepad1.left_trigger - gamepad1.right_trigger;
            patio += gamepad2.left_stick_y/20;
            max = Math.max(Math.max(Math.abs(left), Math.abs(right)), Math.max(Math.abs(front), Math.abs(back)));
            if (max > 1.0) {
                left /= max;
                right /= max;
                back /= max;
                front /= max;
            }
            if (gamepad1.dpad_down && !(gamepad1.dpad_up | gamepad1.dpad_right | gamepad1.dpad_left)){
                front = dpadSpeed;
                back = -dpadSpeed;
                left = dpadSpeed;
                right = -dpadSpeed;
            }else if ( gamepad1.dpad_left && !(gamepad1.dpad_up | gamepad1.dpad_right)){
                front = dpadSpeed;
                back = -dpadSpeed;
                left = -dpadSpeed;
                right = dpadSpeed;
            }else if (gamepad1.dpad_right && !gamepad1.dpad_up){
                front = -dpadSpeed;
                back = dpadSpeed;
                left = dpadSpeed;
                right = -dpadSpeed;
            }else if (gamepad1.dpad_up){
                front = -dpadSpeed;
                back = dpadSpeed;
                left = -dpadSpeed;
                right = dpadSpeed;
            }
            if (gamepad2.a){
                flicker = 1;
            }else if (gamepad2.b){
                flicker = 0.75;
            }else if (gamepad2.y) {
                flicker = -0.75;
            }else if (gamepad2.x) {
                flicker = -1;
            }else {
                flicker = 0;
            }
            if (Math.abs(patio)>1){
                patio /= Math.abs(patio);
            }
            robot.motorBack.setPower(back);
            robot.motorLeft.setPower(left);
            robot.motorRight.setPower(right);
            robot.motorFront.setPower(front);
            robot.motorSweeper.setPower(sweeper);
            robot.motorFlicker.setPower(flicker);
            robot.servoPatio.setPosition(patio);
            idle();
        }
    }
}
