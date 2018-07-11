package ai.kitt.snowboy;
import java.io.File;
import android.os.Environment;

public class Constants {
    public static final String ASSETS_RES_DIR = "snowboy";
    public static final String DEFAULT_WORK_SPACE = Environment.getExternalStorageDirectory().getAbsolutePath() + "/snowboy/";
    public static final String ACTIVE_UMDL = "alexa.umdl";
    public static final String ONE_UMDL = "one.umdl";
    public static final String TWO_UMDL = "two.umdl";
    public static final String THREE_UMDL = "three.umdl";
    public static final String FOUR_UMDL = "four.umdl";
    public static final String FIVE_UMDL = "five.umdl";
    public static final String TEN_UMDL = "ten.umdl";
    public static final String TWENTY_UMDL = "twenty.umdl";
    public static final String THIRTY_UMDL = "thirty.umdl";
    public static final String HAKLET_UMDL = "haklet.umdl";
    public static final String SAYEM_UMDL = "sayem.umdl";
    public static final String HAYEG_UMDL = "hayeg.umdl";
    public static final String KISHUR_UMDL = "kishur.umdl";
    public static final String ACTIVE_RES = "common.res";
    public static final String SAVE_AUDIO = Constants.DEFAULT_WORK_SPACE + File.separatorChar + "recording.pcm";
    public static final int SAMPLE_RATE = 16000;
}
