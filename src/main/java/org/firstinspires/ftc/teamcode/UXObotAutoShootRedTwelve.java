package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="RedShoot12UXO")
public class UXObotAutoShootRedTwelve extends UXOAbstract {
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        robot.servoPatio.setPosition(0.1);
        waitForStart();
        WaitMillis(12000);
        DriveBackward(0.2,3000);
        robot.servoPatio.setPosition(0.2);
        Shoot(1);
        WaitMillis(1000);
        robot.motorFlicker.setPower(0);
        robot.servoPatio.setPosition(0);
        WaitMillis(1000);
        Shoot(1);
        WaitMillis(1000);
        robot.motorFlicker.setPower(-1);
        DriveBackwardRight(0.6, 1800);
        robot.motorFlicker.setPower(0);
        DriveRight(0.6, 4000);
    }
}
