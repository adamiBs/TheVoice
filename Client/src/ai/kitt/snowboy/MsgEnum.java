package ai.kitt.snowboy;

public enum  MsgEnum {
    MSG_VAD_END,
    MSG_VAD_NOSPEECH,
    MSG_VAD_SPEECH,
    MSG_VOLUME_NOTIFY,
    MSG_WAV_DATAINFO,
    MSG_RECORD_START,
    MSG_RECORD_STOP,
    MSG_ACTIVE,
    MSG_ONE,
    MSG_TWO,
    MSG_THREE,
    MSG_FOUR,
    MSG_FIVE,
    MSG_TEN,
    MSG_TWENTY,
    MSG_THIRTY,
    MSG_HAKLET,
    MSG_HAYEG,
    MSG_KISHUR,
    MSG_SIYUM,
    MSG_ERROR,
    MSG_INFO;

    public static MsgEnum getMsgEnum(int i) {
        return MsgEnum.values()[i];
    }
}
