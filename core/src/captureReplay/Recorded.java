package captureReplay;

public interface Recorded {

    public Object getState() throws NoSuchFieldException, IllegalAccessException;
    public void setRecording();


}
