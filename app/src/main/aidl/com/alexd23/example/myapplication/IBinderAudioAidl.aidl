// IBinderAudioAidl.aidl
package com.alexd23.example.myapplication;
// Declare any non-default types here with import statements

interface IBinderAudioAidl {
    void setCallBack(IAidlCallback callback);
    void startPlayAudio(in Audio audio);
    void pausePlayMusic();
    void continuePlayAudio();
    void nextPlayAudio();
    void previousPlayAudio();
    void rewindAudio(int position);
}


parcelable Audio;

interface IAidlCallback {
    oneway void setAudio(in Audio audio);
    oneway void updateSeekBar(int duration, int position);
}

