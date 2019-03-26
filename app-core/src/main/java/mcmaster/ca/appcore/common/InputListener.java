package mcmaster.ca.appcore.common;

public interface InputListener<T> {
    void onInputReceived(T value);
}