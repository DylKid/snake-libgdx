package captureReplay;

public interface InputHandler {
    public boolean touchUp(int x, int y, int pointer, int button);
    public boolean keyDown();
    public boolean keyUp();
}
