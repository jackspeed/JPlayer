package tv.danmaku.ijk.media.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;

public class TextActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private SurfaceView mSurfaceView;
    private IjkMediaPlayer ijkMediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        mSurfaceView = findViewById(R.id.surface_view);
        ijkMediaPlayer = new IjkMediaPlayer();
//        // 强制使用tcp
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_transport", "tcp");

        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 60);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "max-fps", 0);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "fps", 30);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", IjkMediaPlayer.SDL_FCC_YV12);

        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "fflags", "nobuffer");
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "max-buffer-size", 1024);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "min-frames", 10);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 1);
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probsize", "4096");
        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "analyzeduration", "2000000");
        mSurfaceView.getHolder().addCallback(this);
        String mVideoPath = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
        mVideoPath="rtsp://218.204.223.237:554/live/1/66251FC11353191F/e7ooqwcfbqjoo80j.sdp";
        mVideoPath="rtsp://218.204.223.237:554/live/1/0547424F573B085C/gsfp90ef4k0a6iap.sdp";
        mVideoPath="rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov";
        try {
            ijkMediaPlayer.setDataSource(mVideoPath);
            ijkMediaPlayer.prepareAsync();
            ijkMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        一些视频返回码
//        int MEDIA_INFO_VIDEO_RENDERING_START = 3;//视频准备渲染
//        int MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK = 200;//数据错误没有有效的回收
//        int MEDIA_INFO_BUFFERING_START = 701;//开始缓冲
//        int MEDIA_INFO_BUFFERING_END = 702;//缓冲结束
//        int MEDIA_INFO_VIDEO_ROTATION_CHANGED = 10001;//视频选择信息
//        int MEDIA_ERROR_SERVER_DIED = 100;//视频中断，一般是视频源异常或者不支持的视频类型。
//        int MEDIA_ERROR_IJK_PLAYER = -10000,//一般是视频源有问题或者数据格式不支持，比如音频不是AAC之类的
//        MediaPlayer Error (-10000,0), 视频播放过程中出错了，可能是视频的原因，播放器内部错误
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        ijkMediaPlayer.setDisplay(holder);
        Log.i("*******", holder.toString());
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.i("*******", holder.toString() + "\n" + "format:\n" + format + "width:\n" + width + "height:\n" + height);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("*******", holder.toString());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ijkMediaPlayer.stop();
        ijkMediaPlayer.release();
    }
}
