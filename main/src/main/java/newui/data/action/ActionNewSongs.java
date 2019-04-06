package newui.data.action;

import com.old.interesting.music.models.Track;

import java.util.List;

/**
 * Created by sjning
 * created on: 2019/2/13 上午10:35
 * description:
 */
public class ActionNewSongs {
    public static final int TYPE_HOME = 0;
    public static final int TYPE_LIST = 1;
    public boolean isOK;
    public List<Track> trackList;
    public int type;
    public String from;
}
