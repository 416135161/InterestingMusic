package newui.data.util;

import android.text.TextUtils;

import com.old.interesting.music.Config;
import com.old.interesting.music.intercepter.HttpUtil;
import com.old.interesting.music.interfaces.StreamService;
import com.old.interesting.music.models.Track;
import com.old.interesting.music.models.songDetailResponse.SongDetailBean;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import newui.data.action.ActionBillSongs;
import newui.data.action.ActionBrowPlayTeam;
import newui.data.action.ActionListPlayTeam;
import newui.data.action.ActionNewSongs;
import newui.data.action.ActionPlayList;
import newui.data.playTeamResponse.PlayTeamBean;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sjning
 * created on: 2019/1/12 上午10:09
 * description:
 */
public final class CloudDataUtil {
    //获取歌单
    public static void getPlayTeamList(int pageSize, final int type, final String from) {
        StreamService ss = HttpUtil.getApiService(Config.API_HOST, null);
        Call<ArrayList<PlayTeamBean>> call;
        if (TextUtils.equals(from, Config.FROM_US) || TextUtils.equals(from, Config.FROM_JAPAN)) {
            call = ss.getPlayTeamList(pageSize, from);
        } else {
            call = ss.getEuropePlayTeamList(pageSize, from);
        }
        call.enqueue(new Callback<ArrayList<PlayTeamBean>>() {

            @Override
            public void onResponse(Call<ArrayList<PlayTeamBean>> call, Response<ArrayList<PlayTeamBean>> response) {
                if (response.isSuccessful() && response.body() != null
                        && !response.body().isEmpty()) {
                    ArrayList<PlayTeamBean> list = response.body();
                    SortUtil.shuffle(list);
                    if (type == ActionBrowPlayTeam.TYPE_TEAM_LIST) {
                        EventBus.getDefault().post(new ActionListPlayTeam(list, from));
                    } else if (type == ActionBrowPlayTeam.TYPE_BROW) {
                        EventBus.getDefault().post(new ActionBrowPlayTeam(list));

                    }
                } else {
                    onFailure(call, null);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<PlayTeamBean>> call, Throwable t) {
                if (type == ActionBrowPlayTeam.TYPE_TEAM_LIST) {
                    EventBus.getDefault().post(new ActionListPlayTeam(null, null));
                } else if (type == ActionBrowPlayTeam.TYPE_BROW) {
                    EventBus.getDefault().post(new ActionBrowPlayTeam(null));
                }
            }

        });
    }

    //获取歌单下的歌曲
    public static void getPlayList(String id, String from) {
        StreamService ss = HttpUtil.getApiService(Config.API_HOST, null);
        Call<ArrayList<SongDetailBean>> call;
        if (TextUtils.equals(from, Config.FROM_US) || TextUtils.equals(from, Config.FROM_JAPAN)){
            call = ss.getPlayList(id);
        }else {
            call = ss.getEuropePlayList(id);
        }

        call.enqueue(new Callback<ArrayList<SongDetailBean>>() {

            @Override
            public void onResponse(Call<ArrayList<SongDetailBean>> call, Response<ArrayList<SongDetailBean>> response) {
                if (response.isSuccessful() && response.body() != null
                        && !response.body().isEmpty()) {
                    ArrayList<SongDetailBean> list = response.body();
                    SortUtil.shuffle(list);
                    EventBus.getDefault().post(new ActionPlayList(list));
                } else {
                    onFailure(null, new Exception("is nothing"));
                }

            }

            @Override
            public void onFailure(Call<ArrayList<SongDetailBean>> call, Throwable t) {
                ActionPlayList action = new ActionPlayList(null);
                action.isOK = false;
                EventBus.getDefault().post(action);

            }

        });
    }


    //获取新歌
    public static void getNewSongs(final int type, final String from) {
        StreamService ss = HttpUtil.getApiService(Config.API_HOST, null);
        Call<ArrayList<SongDetailBean>> call = ss.getNewSongs(from);
        call.enqueue(new Callback<ArrayList<SongDetailBean>>() {
            @Override
            public void onResponse(Call<ArrayList<SongDetailBean>> call, Response<ArrayList<SongDetailBean>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Track> trackList = new ArrayList<>();
                    for (SongDetailBean newSongBean : response.body()) {
                        Track track = new Track();
                        track.setmArtworkURL(newSongBean.getImgUrl());
                        track.setSingerName(newSongBean.getSingerName());
                        track.setFileHash(newSongBean.getHash());
                        track.setmStreamURL(newSongBean.getPlayUrl());
                        track.setmTitle(newSongBean.getSongName());
                        trackList.add(track);
                    }
                    ActionNewSongs action = new ActionNewSongs();
                    action.isOK = true;
                    action.trackList = trackList;
                    action.type = type;
                    action.from = from;
                    EventBus.getDefault().post(action);
                } else {
                    onFailure(null, new Exception("is nothing"));
                }

            }

            @Override
            public void onFailure(Call<ArrayList<SongDetailBean>> call, Throwable t) {
                ActionNewSongs action = new ActionNewSongs();
                action.isOK = false;
                action.type = type;
                action.from = from;
                EventBus.getDefault().post(action);

            }

        });
    }

    //获取热歌
    public static void getBillSongs(final int type, final String from) {
        StreamService ss = HttpUtil.getApiService(Config.API_HOST, null);
        Call<ArrayList<SongDetailBean>> call = ss.getBillSongs(from);
        call.enqueue(new Callback<ArrayList<SongDetailBean>>() {
            @Override
            public void onResponse(Call<ArrayList<SongDetailBean>> call, Response<ArrayList<SongDetailBean>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    List<Track> trackList = new ArrayList<>();
                    for (SongDetailBean billSongBean : response.body()) {
                        Track track = new Track();
                        track.setmArtworkURL(billSongBean.getImgUrl());
                        track.setSingerName(billSongBean.getSingerName());
                        track.setFileHash(billSongBean.getHash());
                        track.setmStreamURL(billSongBean.getPlayUrl());
                        track.setmTitle(billSongBean.getSongName());
                        trackList.add(track);
                    }
                    ActionBillSongs action = new ActionBillSongs();
                    action.isOK = true;
                    SortUtil.shuffle(trackList);
                    action.trackList = trackList;
                    action.type = type;
                    action.from = from;
                    EventBus.getDefault().post(action);
                } else {
                    onFailure(null, new Exception("is nothing"));
                }

            }

            @Override
            public void onFailure(Call<ArrayList<SongDetailBean>> call, Throwable t) {
                ActionBillSongs action = new ActionBillSongs();
                action.isOK = false;
                action.type = type;
                action.from = from;
                EventBus.getDefault().post(action);

            }

        });
    }
}
