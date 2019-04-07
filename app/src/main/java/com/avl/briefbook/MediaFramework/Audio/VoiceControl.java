package com.avl.briefbook.MediaFramework.Audio;

import android.media.audiofx.AcousticEchoCanceler;
import android.media.audiofx.AudioEffect;
import android.media.audiofx.AutomaticGainControl;
import android.media.audiofx.BassBoost;
import android.media.audiofx.Equalizer;
import android.media.audiofx.NoiseSuppressor;
import android.media.audiofx.PresetReverb;
import android.media.audiofx.Visualizer;

public class VoiceControl {


    public static void main(String[] args)
    {
        AudioEffect          audioEffect;
        AcousticEchoCanceler acousticEchoCanceler; //取消回声控制
        AutomaticGainControl automaticGainControl; //自动增益控制
        NoiseSuppressor noiseSuppressor; //噪音压制器
        BassBoost       bassBoost;             //重低音控制器
        Equalizer       equalizer;             //均衡控制器
        PresetReverb    presetReverb;       // 预设音场控制器
        Visualizer      visualizer;     //示波器
    }
}
