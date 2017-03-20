package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class UXOHardwareMap {
    DcMotor motorBack;
    DcMotor motorRight;
    DcMotor motorLeft;
    DcMotor motorFront;
    DcMotor motorSweeper;
    DcMotor motorFlicker;
    Servo servoPatio;

    HardwareMap hwMap;
    public void init(HardwareMap UXOhwmap){
        hwMap = UXOhwmap;
        motorLeft = hwMap.dcMotor.get("motorBackLeft");
        motorRight = hwMap.dcMotor.get("motorFrontRight");
        motorFront = hwMap.dcMotor.get("motorFrontLeft");
        motorBack = hwMap.dcMotor.get("motorBackRight");
        motorSweeper = hwMap.dcMotor.get("motorSweeper");
        motorFlicker = hwMap.dcMotor.get("motorFlicker");
        servoPatio = hwMap.servo.get("servoPatio");
        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorBack.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorSweeper.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFlicker.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorFlicker.setDirection(DcMotor.Direction.REVERSE);
        motorSweeper.setDirection(DcMotor.Direction.REVERSE);
    }
}