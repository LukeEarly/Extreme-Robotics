package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="BlueShootUXO")
public class UXObotAutoShootBlue extends UXOAbstract {
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        robot.servoPatio.setPosition(0.1);
        waitForStart();
        DriveBackward(0.2,3100);
        robot.servoPatio.setPosition(0.2);
        Shoot(1);
        WaitMillis(1000);
        robot.motorFlicker.setPower(0);
        robot.servoPatio.setPosition(0);
        WaitMillis(1000);
        Shoot(1);
        WaitMillis(1000);
        robot.motorFlicker.setPower(-1);
        DriveBackward(0.2, 200);
        DriveBackwardLeft(0.6, 2300);
        robot.motorFlicker.setPower(0);
        DriveLeft(0.6, 4000);
    }
}
