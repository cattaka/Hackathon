
package net.cattaka.android.timeofdrag.fragment;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.cattaka.android.timeofdrag.MainActivity;
import net.cattaka.android.timeofdrag.R;
import net.cattaka.android.timeofdrag.R.layout;
import net.cattaka.android.timeofdrag.core.IBaseFragment;
import net.cattaka.android.timeofdrag.model.TaskMasterModel;
import net.cattaka.android.timeofdrag.util.NotificationUtil;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.preview.support.v4.app.NotificationManagerCompat;
import android.preview.support.wearable.notifications.RemoteInput;
import android.preview.support.wearable.notifications.WearableNotifications;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainFragment extends IBaseFragment implements OnItemClickListener {
    private static final int NOTIFICATION_ID = 1;

    private static class TaskMasterAdapter extends ArrayAdapter<TaskMasterModel> {
        public TaskMasterAdapter(Context context, List<TaskMasterModel> models) {
            super(context, layout.item_task_master, models);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(layout.item_task_master,
                        null);
            }
            TaskMasterModel model = getItem(position);
            String routineTime = model.getRoutineTime().getLabel(getContext().getResources());
            Calendar nextTime = Calendar.getInstance();
            nextTime.setTimeInMillis(model.getNextAlertTime());
            Date date = new Date();
            date.setTime(model.getNextAlertTime());
            TextView text1 = (TextView)convertView.findViewById(R.id.textView1);
            TextView text2 = (TextView)convertView.findViewById(R.id.textView2);
            TextView text3 = (TextView)convertView.findViewById(R.id.textView3);
            text1.setText(model.getTitle());
            text2.setText(model.getDetail());
            text3.setText(String.valueOf(date) + "(" + routineTime + ")");

            return convertView;
        }
    }

    private ListView mTaskMasterList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);

        mTaskMasterList = (ListView)view.findViewById(R.id.taskMasterList);
        mTaskMasterList.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        List<TaskMasterModel> models = getDbHelper().findTaskMasters();
        TaskMasterAdapter adapter = new TaskMasterAdapter(getContext(), models);
        mTaskMasterList.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TaskMasterModel model = (TaskMasterModel)parent.getItemAtPosition(position);

        Intent viewIntent = new Intent(getContext(), MainActivity.class);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(getContext(), 0, viewIntent, 0);

        Resources res = getContext().getResources();

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(
                getContext()).setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(model.getTitle()).setContentText(model.getDetail())
                .setContentIntent(viewPendingIntent);
        NotificationManagerCompat notificationManager = NotificationManagerCompat
                .from(getContext());
        WearableNotifications.Builder wearableBuilder = new WearableNotifications.Builder(
                notificationBuilder);
        {
            RemoteInput remoteInput = new RemoteInput.Builder(NotificationUtil.EXTRA_REPLY)
                    .setLabel(res.getString(R.string.label_reply_answer))
                    .setChoices(res.getStringArray(R.array.items_reply)).build();
            WearableNotifications.Action action = new WearableNotifications.Action.Builder(
                    R.drawable.ic_full_reply, res.getString(R.string.label_reply_answer),
                    NotificationUtil.getExamplePendingIntent(getContext(),
                            R.string.label_reply_answer)).addRemoteInput(remoteInput).build();
            wearableBuilder.addAction(action);
        }

        notificationManager.notify(NOTIFICATION_ID, wearableBuilder.build());
    }
}
