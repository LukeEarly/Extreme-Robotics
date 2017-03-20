package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
@Autonomous(name="TestUXO")
public class UXObotTest extends UXOAbstract {
    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        waitForStart();
        DriveForwardLeft(0.4,4500);
        TankLeft(0.4,1000);
        WaitMillis(500);
        DriveForwardRight(0.4,1000);
        TankLeft(0.4, 500);
        telemetry.addData("Say", "I am done.");
        telemetry.update();
    }
}
