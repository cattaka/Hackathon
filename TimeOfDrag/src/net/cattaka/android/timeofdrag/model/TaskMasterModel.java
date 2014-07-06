
package net.cattaka.android.timeofdrag.model;

import net.cattaka.android.timeofdrag.data.RoutineTime;
import net.cattaka.util.gendbhandler.Attribute;
import net.cattaka.util.gendbhandler.GenDbHandler;
import net.vvakame.util.jsonpullparser.annotation.JsonKey;
import net.vvakame.util.jsonpullparser.annotation.JsonModel;

/**
 * Created by cattaka on 14/06/15.
 */
@JsonModel(decamelize = true)
@GenDbHandler(find = {
    ":seq+"
})
public class TaskMasterModel {
    @JsonKey
    @Attribute(primaryKey = true)
    private long seq;

    @JsonKey
    private String title;

    @JsonKey
    private String detail;

    @JsonKey
    private RoutineTime routineTime;

    private long nextAlertTime;

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public RoutineTime getRoutineTime() {
        return routineTime;
    }

    public void setRoutineTime(RoutineTime routineTime) {
        this.routineTime = routineTime;
    }

    public long getNextAlertTime() {
        return nextAlertTime;
    }

    public void setNextAlertTime(long nextAlertTime) {
        this.nextAlertTime = nextAlertTime;
    }
}
