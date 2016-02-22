package database;

import android.support.annotation.IdRes;
import android.support.annotation.IntDef;

import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/2/22.
 */
@Table(name = "list_item")
public class ListItem {

    private int id;

    private String value;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
