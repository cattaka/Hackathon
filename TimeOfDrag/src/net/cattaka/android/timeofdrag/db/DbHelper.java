
package net.cattaka.android.timeofdrag.db;

import java.util.Calendar;
import java.util.List;

import net.cattaka.android.timeofdrag.model.TaskMasterModel;
import net.cattaka.android.timeofdrag.model.handler.TaskMasterModelHandler;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by cattaka on 14/06/15.
 */
public class DbHelper extends SQLiteOpenHelper {
    public static String DB_NAME = "my.db";

    public DbHelper(Context context) {
        this(context, DB_NAME);
    }

    public DbHelper(Context context, String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TaskMasterModelHandler.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public List<TaskMasterModel> findTaskMasters() {
        SQLiteDatabase db = getReadableDatabase();
        try {
            return TaskMasterModelHandler.findOrderBySeqAsc(db, -1);
        } finally {
            db.close();
        }
    }

    public void registerTaskMasters(List<TaskMasterModel> models) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            for (TaskMasterModel model : models) {
                TaskMasterModelHandler.insert(db, model);
            }
        } finally {
            db.close();
        }
    }

    public void updateNextAlertTime() {
        SQLiteDatabase db = getWritableDatabase();
        try {
            List<TaskMasterModel> models = TaskMasterModelHandler.findOrderBySeqAsc(db, -1);
            for (TaskMasterModel model : models) {
                if (model.getRoutineTime() != null) {
                    Calendar cal = Calendar.getInstance();
                    model.getRoutineTime().nextSchedule(cal);
                    model.setNextAlertTime(cal.getTimeInMillis());
                }
                TaskMasterModelHandler.update(db, model);
            }
        } finally {
            db.close();
        }
    }
}
