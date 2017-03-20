package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="Rad6ShootUXO")
public class UXObotAutoShootRadicalSix extends UXOAbstract {
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        robot.servoPatio.setPosition(0.1);
        waitForStart();
        DriveBackward(0.2,3000);
        robot.servoPatio.setPosition(0.2);
        Shoot(1);
        WaitMillis(1000);
        robot.motorFlicker.setPower(0);
        robot.servoPatio.setPosition(0);
        WaitMillis(1000);
        Shoot(1);
        WaitMillis(1000);
        robot.motorFlicker.setPower(0);
    }
}
