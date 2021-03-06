package com.spartronics4915.atlas.commands;

import com.spartronics4915.atlas.Logger;
import com.spartronics4915.atlas.RobotMap;
import com.spartronics4915.atlas.subsystems.Harvester;
import com.spartronics4915.atlas.subsystems.Launcher;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command is to keep motors safety happy, and is also an example of the
 * boilerplate/logger type code you need for a command.
 */
public class ExtendPneumatics extends Command
{

    private Harvester mHarvester;
    private Launcher mLauncher;

    private boolean interruptionFinish = false;

    public ExtendPneumatics()
    {
        mHarvester = Harvester.getInstance();
        mLauncher = Launcher.getInstance();
        requires(mHarvester);
    }

    @Override
    protected void initialize()
    {
        interruptionFinish = false;

        if (mHarvester.isHarvesterUp()) 
        {
            mHarvester.extendPneumatics();
            Logger.info("Starting Extend Pneumatics...");
        } 
        else 
        {
            Logger.info("Harvester is already down");
        }
    }

    @Override
    protected void execute()
    {
        mHarvester.setWheelSpeed(RobotMap.kHarvesterIntakeWheelSpeed);
        mLauncher.stopLauncherWindingMotor();
    }

    @Override
    protected boolean isFinished()
    {
        return mHarvester.isHarvesterDown() || interruptionFinish;
    }

    @Override
    protected void end()
    {
        Logger.info("Extend Pneumatics done.");
    }

    @Override
    protected void interrupted()
    {
        interruptionFinish = true;
        isFinished();
    }
}
