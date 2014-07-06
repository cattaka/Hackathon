
package net.cattaka.android.timeofdrag.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import net.cattaka.android.timeofdrag.R;
import net.cattaka.android.timeofdrag.db.DbHelper;
import net.cattaka.android.timeofdrag.model.TaskMasterModel;
import net.cattaka.android.timeofdrag.model.TaskMasterModelGen;
import net.vvakame.util.jsonpullparser.JsonFormatException;
import android.app.Application;

/**
 * Created by cattaka on 14/06/15.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        createSampleData();
    }

    private void createSampleData() {
        this.deleteDatabase(DbHelper.DB_NAME);
        DbHelper dbHelper = new DbHelper(this);

        InputStream in = getResources().openRawResource(R.raw.sample_data);
        List<TaskMasterModel> models;
        try {
            models = TaskMasterModelGen.getList(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (JsonFormatException e) {
            throw new RuntimeException(e);
        }
        dbHelper.registerTaskMasters(models);
        dbHelper.updateNextAlertTime();
    }
}
