package net.cattaka.android.timeofdrag.data;

import android.content.res.Resources;

import net.cattaka.android.timeofdrag.R;

import java.util.Calendar;

/**
 * Created by cattaka on 14/06/15.
 */
public enum RoutineTime {
    HOUR_0(0),
    HOUR_1(1),
    HOUR_2(2),
    HOUR_3(3),
    HOUR_4(4),
    HOUR_5(5),
    HOUR_6(6),
    HOUR_7(7),
    HOUR_8(8),
    HOUR_9(9),
    HOUR_10(10),
    HOUR_11(11),
    HOUR_12(12),
    HOUR_13(13),
    HOUR_14(14),
    HOUR_15(15),
    HOUR_16(16),
    HOUR_17(17),
    HOUR_18(18),
    HOUR_19(19),
    HOUR_20(20),
    HOUR_21(21),
    HOUR_22(22),
    HOUR_23(23),
    ;

    private int hour;

    private RoutineTime(int hour) {
        this.hour = hour;
    }

    public String getLabel(Resources res) {
        return res.getString(R.string.label_routine_time, hour);
    }

    public void nextSchedule(Calendar src) {
        Calendar result = Calendar.getInstance();
        int h = src.get(Calendar.HOUR);
        src.set(Calendar.HOUR, hour);
        src.set(Calendar.MINUTE, 0);
        src.set(Calendar.SECOND, 0);
        if (h >= hour) {
            src.add(Calendar.DAY_OF_MONTH, 1);
        }
    }
}

