package trc3543.trcscoutingapp.data;

import trc3543.trcscoutingapp.fragments.AutonomousFragment;
import trc3543.trcscoutingapp.fragments.EndgameFragment;
import trc3543.trcscoutingapp.fragments.TeleOpFragment;

public class AppInfo
{
    //
    // General app settings.
    //
    public static final String DATA_FOLDER_NAME = "TrcScoutingApp";
    public static final String SETTINGS_FILENAME = "app_settings.json";
    public static final String CSV_HEADER = "matchNum, teamNum, matchType, alliance, initLineCrossed, autonomousLowerCells, autonomousOuterCells, autonomousInnerCells, autonomousMissedCells, teleopLowerCells, teleopOuterCells, teleopInnerCells, teleopMissedCells, shieldStage1, shieldStage2, shieldStage3, controlPanelRotated, controlPanelPositioned, generatorSwitchParked, generatorSwitchHanging, generatorSwitchSupportingMechanism, generatorSwitchLevel, notes";
    public static final String VERSION_NUMBER = "1.4.0-frc";
    public static final int YEAR_NUMBER = 2020;

    //
    // SetMatchInfo and child Fragment settings.
    //
    public static final int NUM_PAGES = 3;
    public static final String[] TAB_NAMES = {"Autonomous", "Teleoperated", "Endgame"};
    public static final Class<?>[] FRAGMENT_CLASSES = {AutonomousFragment.class, TeleOpFragment.class, EndgameFragment.class};
}
