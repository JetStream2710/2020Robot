package frc.robot.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import edu.wpi.first.hal.can.CANJNI;

public class CANDeviceFinder {
    private static final Logger logger = new Logger(CANDeviceFinder.class.getName());
    
    private final ArrayList<String> deviceList = new ArrayList<>();
    private final Set<Integer> srxs = new TreeSet<>();
    private final Set<Integer> spxs = new TreeSet<>();
    private final Set<Integer> pcms = new TreeSet<>();
    private final Set<Integer> maxs = new TreeSet<>();
    private boolean pdpIsPresent = false;

    public CANDeviceFinder() {
    }

    // Call this infrequently, do not call periodically.
    public void debug() {
        logger.warning("Finding CANBus devices...");
        find();
        for (String device : deviceList) {
            logger.warning(device);
        }
        logger.warning("Finding CANBus devices finished.");
    }

    public boolean isPDPPresent() {
        return pdpIsPresent;
    }

    public boolean isSRXPresent(int i) {
        return srxs.contains(i);
    }

    public boolean isSPXPresent(int i) {
        return spxs.contains(i);
    }

    public boolean isPCMPresent(int i) {
        return pcms.contains(i);
    }

    public boolean isMAXPresent(int i) {
        return maxs.contains(i);
    }
    
    public boolean isDevicePresent(String s) {
    	return deviceList.contains(s);
    }

    public List<String> getDeviceList() {
        return deviceList;
    }
    
    /**
     * Poll for received framing to determine if a device is present. This is
     * meant to be used once initially (and not periodically) since this steals
     * cached messages from the robot API.
     */
    private void find() {
        deviceList.clear();
        maxs.clear();
        spxs.clear();
        srxs.clear();
        pcms.clear();
        pdpIsPresent = false;

        // get timestamp0 for each device
        long pdp0_timeStamp0 = checkMessage(0x08041400, 0);
        long[] pcm_timeStamp0 = new long[63];
        long[] srx_timeStamp0 = new long[63];
        long[] spx_timeStamp0 = new long[63];
        long[] max_timeStamp0 = new long[63];
        for (int i = 0; i < 63; ++i) {
            pcm_timeStamp0[i] = checkMessage(0x09041400, i);
            srx_timeStamp0[i] = checkMessage(0x02041400, i);
            spx_timeStamp0[i] = checkMessage(0x01041400, i);
            max_timeStamp0[i] = checkMessage(0x02051800, i);
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // get timestamp1 for each device
        long pdp0_timeStamp1 = checkMessage(0x08041400, 0);
        long[] pcm_timeStamp1 = new long[63];
        long[] srx_timeStamp1 = new long[63];
        long[] spx_timeStamp1 = new long[63];
        long[] max_timeStamp1 = new long[63];
        for (int i = 0; i < 63; ++i) {
            pcm_timeStamp1[i] = checkMessage(0x09041400, i);
            srx_timeStamp1[i] = checkMessage(0x02041400, i);
            spx_timeStamp1[i] = checkMessage(0x01041400, i);
            max_timeStamp1[i] = checkMessage(0x02051800, i);
        }

        /*
         * compare, if timestamp0 is good and timestamp1 is good, and they are
         * different, device is healthy
         */
        if (pdp0_timeStamp0 >= 0 && pdp0_timeStamp1 >= 0
                && pdp0_timeStamp0 != pdp0_timeStamp1) {
            deviceList.add("PDP 0");
            pdpIsPresent = true;
        }

        for (int i = 0; i < 63; ++i) {
            if (pcm_timeStamp0[i] >= 0 && pcm_timeStamp1[i] >= 0
                    && pcm_timeStamp0[i] != pcm_timeStamp1[i]) {
                deviceList.add("PCM " + i);
                pcms.add(i);
            }
        }
        for (int i = 0; i < 63; ++i) {
            if (srx_timeStamp0[i] >= 0 && srx_timeStamp1[i] >= 0
                    && srx_timeStamp0[i] != srx_timeStamp1[i]) {
                deviceList.add("SRX " + i);
                srxs.add(i);
            }
        }
        for (int i = 0; i < 63; ++i) {
            if (spx_timeStamp0[i] >= 0 && spx_timeStamp1[i] >= 0
                    && spx_timeStamp0[i] != spx_timeStamp1[i]) {
                deviceList.add("SPX " + i);
                spxs.add(i);
            }
        }
        for (int i = 0; i < 63; ++i) {
            if (max_timeStamp0[i] >= 0 && max_timeStamp1[i] >= 0
                    && max_timeStamp0[i] != max_timeStamp1[i]) {
                deviceList.add("MAX " + i);
                maxs.add(i);
            }
        }
    }

    private ByteBuffer targetID = ByteBuffer.allocateDirect(4);
    private ByteBuffer timeStamp = ByteBuffer.allocateDirect(4);

    // helper function to get last received message for a given ID
    private long checkMessage(int fullId, int deviceID) {
        try {
            targetID.clear();
            targetID.order(ByteOrder.LITTLE_ENDIAN);
            targetID.asIntBuffer().put(0, fullId | deviceID);

            timeStamp.clear();
            timeStamp.order(ByteOrder.LITTLE_ENDIAN);
            timeStamp.asIntBuffer().put(0, 0x00000000);

            CANJNI.FRCNetCommCANSessionMuxReceiveMessage(
                    targetID.asIntBuffer(), 0x1fffffff, timeStamp);

            long retval = timeStamp.getInt();
            retval &= 0xFFFFFFFF; /* undo sign-extension */
            return retval;
        } catch (Exception e) {
            return -1;
        }
    }
}