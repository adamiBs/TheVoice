package ai.kitt.snowboy.audio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import ai.kitt.snowboy.Constants;
import ai.kitt.snowboy.MsgEnum;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import ai.kitt.snowboy.SnowboyDetect;

public class RecordingThread {
    static { System.loadLibrary("snowboy-detect-android"); }

    private static final String TAG = RecordingThread.class.getSimpleName();

    private static final String ACTIVE_RES = Constants.ACTIVE_RES;
    private static final String ACTIVE_UMDL = Constants.ACTIVE_UMDL;
    
    private boolean shouldContinue;
    private AudioDataReceivedListener listener = null;
    private Handler handler = null;
    private Thread thread;
    
    private static String strEnvWorkSpace = Constants.DEFAULT_WORK_SPACE;
    private String activeModel = strEnvWorkSpace+ACTIVE_UMDL;    
    private String commonRes = strEnvWorkSpace+ACTIVE_RES;


    private String oneModel = strEnvWorkSpace + Constants.ONE_UMDL;
    private String twoModel = strEnvWorkSpace + Constants.TWO_UMDL;
    private String threeModel = strEnvWorkSpace + Constants.THREE_UMDL;
    private String fourModel = strEnvWorkSpace + Constants.FOUR_UMDL;
    private String fiveModel = strEnvWorkSpace + Constants.FIVE_UMDL;
    private String tenModel = strEnvWorkSpace + Constants.TEN_UMDL;
    private String twentyModel = strEnvWorkSpace + Constants.TWENTY_UMDL;
    private String thirtyModel = strEnvWorkSpace + Constants.THIRTY_UMDL;
    private String hayegModel = strEnvWorkSpace + Constants.HAYEG_UMDL;
    private String hakletModel = strEnvWorkSpace + Constants.HAKLET_UMDL;
    private String kishurModel = strEnvWorkSpace + Constants.KISHUR_UMDL;
    private String sayemModel = strEnvWorkSpace + Constants.SAYEM_UMDL;

    private SnowboyDetect oneDetector = new SnowboyDetect(commonRes, oneModel);
    private SnowboyDetect twoDetector = new SnowboyDetect(commonRes, twoModel);
    private SnowboyDetect threeDetector = new SnowboyDetect(commonRes, threeModel);
    private SnowboyDetect fourDetector = new SnowboyDetect(commonRes, fourModel);
    private SnowboyDetect fiveDetector = new SnowboyDetect(commonRes, fiveModel);
    private SnowboyDetect tenDetector = new SnowboyDetect(commonRes, tenModel);
    private SnowboyDetect twentyDetector = new SnowboyDetect(commonRes, twentyModel);
    private SnowboyDetect thirtyDetector = new SnowboyDetect(commonRes, thirtyModel);
    private SnowboyDetect hayegDetector = new SnowboyDetect(commonRes, hayegModel);
    private SnowboyDetect hakletDetector = new SnowboyDetect(commonRes, hakletModel);
    private SnowboyDetect kishurDetector = new SnowboyDetect(commonRes, kishurModel);
    private SnowboyDetect sayemDetector = new SnowboyDetect(commonRes, sayemModel);
    
    private SnowboyDetect detector = new SnowboyDetect(commonRes, activeModel);
    private MediaPlayer player = new MediaPlayer();

    public RecordingThread(Handler handler, AudioDataReceivedListener listener) {
        this.handler = handler;
        this.listener = listener;

        SetDetectorParams(detector, "0.6");

        SetDetectorParams(oneDetector, "0.4");
        SetDetectorParams(twoDetector, "0.5");
        SetDetectorParams(threeDetector, "0.5");
        SetDetectorParams(fourDetector, "0.5");
        SetDetectorParams(fiveDetector, "0.5");
        SetDetectorParams(tenDetector, "0.5");
        SetDetectorParams(twentyDetector, "0.5");
        SetDetectorParams(thirtyDetector, "0.5");
        SetDetectorParams(hayegDetector, "0.5");
        SetDetectorParams(hakletDetector, "0.5");
        SetDetectorParams(kishurDetector, "0.5");
        SetDetectorParams(sayemDetector, "0.5");

        try {
            player.setDataSource(strEnvWorkSpace+"ding.wav");
            player.prepare();
        } catch (IOException e) {
            Log.e(TAG, "Playing ding sound error", e);
        }
    }

    private static void SetDetectorParams(SnowboyDetect oneDetector, String sensitivity) {
        oneDetector.SetSensitivity(sensitivity);
        oneDetector.SetAudioGain(1);
        oneDetector.ApplyFrontend(true);
    }

    private void sendMessage(MsgEnum what, Object obj){
        if (null != handler) {
            Message msg = handler.obtainMessage(what.ordinal(), obj);
            handler.sendMessage(msg);
        }
    }

    public void startRecording() {
        if (thread != null)
            return;

        shouldContinue = true;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                record();
            }
        });
        thread.start();
    }

    public void stopRecording() {
        if (thread == null)
            return;

        shouldContinue = false;
        thread = null;
    }

    private void record() {
        Log.v(TAG, "Start");
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);

        // Buffer size in bytes: for 0.1 second of audio
        int bufferSize = (int)(Constants.SAMPLE_RATE * 0.1 * 2);
        if (bufferSize == AudioRecord.ERROR || bufferSize == AudioRecord.ERROR_BAD_VALUE) {
            bufferSize = Constants.SAMPLE_RATE * 2;
        }

        byte[] audioBuffer = new byte[bufferSize];
        AudioRecord record = new AudioRecord(
            MediaRecorder.AudioSource.DEFAULT,
            Constants.SAMPLE_RATE,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT,
            bufferSize);

        if (record.getState() != AudioRecord.STATE_INITIALIZED) {
            Log.e(TAG, "Audio Record can't initialize!");
            return;
        }
        record.startRecording();
        if (null != listener) {
            listener.start();
        }
        Log.v(TAG, "Start recording");

        long shortsRead = 0;
        detector.Reset();

        oneDetector.Reset();
        twoDetector.Reset();
        threeDetector.Reset();
        fourDetector.Reset();
        fiveDetector.Reset();
        tenDetector.Reset();
        twentyDetector.Reset();
        thirtyDetector.Reset();
        sayemDetector.Reset();
        hakletDetector.Reset();
        kishurDetector.Reset();
        hayegDetector.Reset();

        while (shouldContinue) {
            record.read(audioBuffer, 0, audioBuffer.length);

            if (null != listener) {
                listener.onAudioDataReceived(audioBuffer, audioBuffer.length);
            }
            
            // Converts to short array.
            short[] audioData = new short[audioBuffer.length / 2];
            ByteBuffer.wrap(audioBuffer).order(ByteOrder.LITTLE_ENDIAN).asShortBuffer().get(audioData);

            shortsRead += audioData.length;

            // Snowboy hotword detection.
            RunAllDetections(audioData);
        }

        record.stop();
        record.release();

        if (null != listener) {
            listener.stop();
        }
        Log.v(TAG, String.format("Recording stopped. Samples read: %d", shortsRead));
    }

    private void RunAllDetections(short[] audioData) {
        if (RunDetection(oneDetector, audioData, MsgEnum.MSG_ONE))
            return;
        if (RunDetection(twoDetector, audioData, MsgEnum.MSG_TWO))
            return;
        if (RunDetection(threeDetector, audioData, MsgEnum.MSG_THREE))
            return;
        if (RunDetection(fourDetector, audioData, MsgEnum.MSG_FOUR))
            return;
        if (RunDetection(fiveDetector, audioData, MsgEnum.MSG_FIVE))
            return;
        if (RunDetection(tenDetector, audioData, MsgEnum.MSG_TEN))
            return;
        if (RunDetection(twentyDetector, audioData, MsgEnum.MSG_TWENTY))
            return;
        if (RunDetection(thirtyDetector, audioData, MsgEnum.MSG_THIRTY))
            return;
        if (RunDetection(hakletDetector, audioData, MsgEnum.MSG_HAKLET))
            return;
        if (RunDetection(hayegDetector, audioData, MsgEnum.MSG_HAYEG))
            return;
        if (RunDetection(kishurDetector, audioData, MsgEnum.MSG_KISHUR))
            return;
        if (RunDetection(sayemDetector, audioData, MsgEnum.MSG_SIYUM))
            return;
    }

    private boolean RunDetection(SnowboyDetect detector, short[] audioData, MsgEnum detectionMessage) {
        int result = detector.RunDetection(audioData, audioData.length);

        if (result == -2) {
            // post a higher CPU usage:
            // sendMessage(MsgEnum.MSG_VAD_NOSPEECH, null);
        } else if (result == -1) {
            sendMessage(MsgEnum.MSG_ERROR, "Unknown Detection Error");
        } else if (result == 0) {
            // post a higher CPU usage:
            // sendMessage(MsgEnum.MSG_VAD_SPEECH, null);
        } else if (result > 0) {
            sendMessage(detectionMessage, null);
            Log.i("Snowboy: ", "Hotword " + Integer.toString(result) + " detected!");
            player.start();
            return true;
        }
        return false;
    }
}
