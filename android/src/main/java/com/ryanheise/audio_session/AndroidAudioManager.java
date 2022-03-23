package com.ryanheise.audio_session;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioDeviceCallback;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.MicrophoneInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Pair;
import android.view.KeyEvent;

import androidx.media.AudioAttributesCompat;
import androidx.media.AudioFocusRequestCompat;
import androidx.media.AudioManagerCompat;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AndroidAudioManager implements MethodCallHandler {
    // TODO: synchronize access
    private static Map<String, Case> cases = new HashMap<String, Case>();

    private MethodChannel channel;
    private Context context;

    public AndroidAudioManager(Context applicationContext, BinaryMessenger messenger) {
        context = applicationContext;
        channel = new MethodChannel(messenger, "com.ryanheise.android_audio_manager");
        channel.setMethodCallHandler(this);
    }

    @Override
    public void onMethodCall(final MethodCall call, final Result result) {
        try {
            List<?> args = (List<?>) call.arguments;
            String sessionKey = String.valueOf(args.get(0));
            Case aCase = cases.get(sessionKey);
            if (aCase == null) {
                aCase = new Case(sessionKey, context);
                aCase.add(this);
                cases.put(sessionKey, aCase);
            }

            switch (call.method) {
                case "requestAudioFocus": {
                    result.success(aCase.requestAudioFocus(args));
                    break;
                }
                case "abandonAudioFocus": {
                    result.success(aCase.abandonAudioFocus());
                    break;
                }
                case "dispatchMediaKeyEvent": {
                    result.success(aCase.dispatchMediaKeyEvent((Map<?, ?>) args.get(1)));
                    break;
                }
                case "isVolumeFixed": {
                    result.success(aCase.isVolumeFixed());
                    break;
                }
                case "adjustStreamVolume": {
                    result.success(aCase.adjustStreamVolume((Integer) args.get(1), (Integer) args.get(2), (Integer) args.get(3)));
                    break;
                }
                case "adjustVolume": {
                    result.success(aCase.adjustVolume((Integer) args.get(1), (Integer) args.get(2)));
                    break;
                }
                case "adjustSuggestedStreamVolume": {
                    result.success(aCase.adjustSuggestedStreamVolume((Integer) args.get(1), (Integer) args.get(2), (Integer) args.get(3)));
                    break;
                }
                case "getRingerMode": {
                    result.success(aCase.getRingerMode());
                    break;
                }
                case "getStreamMaxVolume": {
                    result.success(aCase.getStreamMaxVolume((Integer) args.get(1)));
                    break;
                }
                case "getStreamMinVolume": {
                    result.success(aCase.getStreamMinVolume((Integer) args.get(1)));
                    break;
                }
                case "getStreamVolume": {
                    result.success(aCase.getStreamVolume((Integer) args.get(1)));
                    break;
                }
                case "getStreamVolumeDb": {
                    result.success(aCase.getStreamVolumeDb((Integer) args.get(1), (Integer) args.get(2), (Integer) args.get(3)));
                    break;
                }
                case "setRingerMode": {
                    result.success(aCase.setRingerMode((Integer) args.get(1)));
                    break;
                }
                case "setStreamVolume": {
                    result.success(aCase.setStreamVolume((Integer) args.get(1), (Integer) args.get(2), (Integer) args.get(3)));
                    break;
                }
                case "isStreamMute": {
                    result.success(aCase.isStreamMute((Integer) args.get(1)));
                    break;
                }
                case "setSpeakerphoneOn": {
                    result.success(aCase.setSpeakerphoneOn((Boolean) args.get(1)));
                    break;
                }
                case "isSpeakerphoneOn": {
                    result.success(aCase.isSpeakerphoneOn());
                    break;
                }
                case "setAllowedCapturePolicy": {
                    result.success(aCase.setAllowedCapturePolicy((Integer) args.get(1)));
                    break;
                }
                case "getAllowedCapturePolicy": {
                    result.success(aCase.getAllowedCapturePolicy());
                    break;
                }
                case "isBluetoothScoAvailableOffCall": {
                    result.success(aCase.isBluetoothScoAvailableOffCall());
                    break;
                }
                case "startBluetoothSco": {
                    result.success(aCase.startBluetoothSco());
                    break;
                }
                case "stopBluetoothSco": {
                    result.success(aCase.stopBluetoothSco());
                    break;
                }
                case "setBluetoothScoOn": {
                    result.success(aCase.setBluetoothScoOn((Boolean) args.get(1)));
                    break;
                }
                case "isBluetoothScoOn": {
                    result.success(aCase.isBluetoothScoOn());
                    break;
                }
                case "setMicrophoneMute": {
                    result.success(aCase.setMicrophoneMute((Boolean) args.get(1)));
                    break;
                }
                case "isMicrophoneMute": {
                    result.success(aCase.isMicrophoneMute());
                    break;
                }
                case "setMode": {
                    result.success(aCase.setMode((Integer) args.get(1)));
                    break;
                }
                case "getMode": {
                    result.success(aCase.getMode());
                    break;
                }
                case "isMusicActive": {
                    result.success(aCase.isMusicActive());
                    break;
                }
                case "generateAudioSessionId": {
                    result.success(aCase.generateAudioSessionId());
                    break;
                }
                case "setParameters": {
                    result.success(aCase.setParameters((String) args.get(1)));
                    break;
                }
                case "getParameters": {
                    result.success(aCase.getParameters((String) args.get(1)));
                    break;
                }
                case "playSoundEffect": {
                    result.success(aCase.playSoundEffect((Integer) args.get(1), (Double) args.get(2)));
                    break;
                }
                case "loadSoundEffects": {
                    result.success(aCase.loadSoundEffects());
                    break;
                }
                case "unloadSoundEffects": {
                    result.success(aCase.unloadSoundEffects());
                    break;
                }
                case "getProperty": {
                    result.success(aCase.getProperty((String) args.get(1)));
                    break;
                }
                case "getDevices": {
                    result.success(aCase.getDevices((Integer) args.get(1)));
                    break;
                }
                case "getMicrophones": {
                    result.success(aCase.getMicrophones());
                    break;
                }
                case "isHapticPlaybackSupported": {
                    result.success(aCase.isHapticPlaybackSupported());
                    break;
                }
                case "release":
                    aCase.remove(this);
                    if (aCase.isEmpty()) {
                        aCase.dispose();
                    }
                    cases.remove(sessionKey);
                    break;
                default: {
                    result.notImplemented();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.error("Error: " + e, null, null);
        }
    }

    public void dispose() {
        channel.setMethodCallHandler(null);
        for (Case aCase : cases.values()) {
            if (aCase != null) {
                aCase.remove(this);
                if (aCase.isEmpty()) {
                    aCase.dispose();
                }
            }
        }
        cases.clear();
        channel = null;
    }

    /**
     * To emulate iOS's AVAudioSession, we maintain a single app-wide audio
     * focus request and noisy receiver at any one time which all isolates
     * share access to.
     */
    private static class Case {
        private final Handler handler = new Handler(Looper.getMainLooper());
        private List<AndroidAudioManager> instances = new ArrayList<>();
        private AudioFocusRequestCompat audioFocusRequest;
        private BroadcastReceiver noisyReceiver;
        private Context applicationContext;
        private AudioManager audioManager;
        private Object audioDeviceCallback;
        private String sessionKey;

        private static List<?> encodeAudioDevices(AudioDeviceInfo[] devices) {
            ArrayList<Map<String, Object>> result = new ArrayList<>();
            for (AudioDeviceInfo device : devices) {
                result.add(encodeAudioDevice(device));
            }
            return result;
        }

        private static Map<String, Object> encodeAudioDevice(AudioDeviceInfo device) {
            return mapOf(
                    "id", device.getId(),
                    "productName", device.getProductName(),
                    "address", device.getAddress(),
                    "isSource", device.isSource(),
                    "isSink", device.isSink(),
                    "sampleRates", device.getSampleRates(),
                    "channelMasks", device.getChannelMasks(),
                    "channelIndexMasks", device.getChannelIndexMasks(),
                    "channelCounts", device.getChannelCounts(),
                    "encodings", device.getEncodings(),
                    "type", device.getType()
            );
        }

        public Case(String sessionKey, Context applicationContext) {
            this.sessionKey = sessionKey;
            this.applicationContext = applicationContext;
            audioManager = (AudioManager) applicationContext.getSystemService(Context.AUDIO_SERVICE);
            if (Build.VERSION.SDK_INT >= 23) {
                initAudioDeviceCallback();
            }
        }

        private void initAudioDeviceCallback() {
            audioDeviceCallback = new AudioDeviceCallback() {
                @Override
                public void onAudioDevicesAdded(AudioDeviceInfo[] addedDevices) {
                    invokeMethod("onAudioDevicesAdded", encodeAudioDevices(addedDevices));
                }

                @Override
                public void onAudioDevicesRemoved(AudioDeviceInfo[] removedDevices) {
                    invokeMethod("onAudioDevicesRemoved", encodeAudioDevices(removedDevices));
                }
            };
            audioManager.registerAudioDeviceCallback((AudioDeviceCallback) audioDeviceCallback, handler);
        }

        public void add(AndroidAudioManager manager) {
            instances.add(manager);
        }

        public void remove(AndroidAudioManager manager) {
            instances.remove(manager);
        }

        public boolean isEmpty() {
            return instances.size() == 0;
        }

        private boolean requestAudioFocus(List<?> args) {
            if (audioFocusRequest != null) {
                return true;
            }
            Map<?, ?> request = (Map<?, ?>) args.get(1);
            AudioFocusRequestCompat.Builder builder = new AudioFocusRequestCompat.Builder((Integer) request.get("gainType"));
            builder.setOnAudioFocusChangeListener(focusChange -> {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS) abandonAudioFocus();
                invokeMethod("onAudioFocusChanged", focusChange);
            });
            if (request.get("audioAttributes") != null) {
                builder.setAudioAttributes(decodeAudioAttributes((Map<?, ?>) request.get("audioAttributes")));
            }
            if (request.get("willPauseWhenDucked") != null) {
                builder.setWillPauseWhenDucked((Boolean) request.get("willPauseWhenDucked"));
            }
            audioFocusRequest = builder.build();
            int status = AudioManagerCompat.requestAudioFocus(audioManager, audioFocusRequest);
            boolean success = status == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
            if (success) {
                registerNoisyReceiver();
            }
            return success;
        }

        private boolean abandonAudioFocus() {
            if (applicationContext == null) return false;
            unregisterNoisyReceiver();
            if (audioFocusRequest == null) {
                return true;
            } else {
                int status = AudioManagerCompat.abandonAudioFocusRequest(audioManager, audioFocusRequest);
                audioFocusRequest = null;
                return status == AudioManager.AUDIOFOCUS_REQUEST_GRANTED;
            }
        }

        private Object dispatchMediaKeyEvent(Map<?, ?> rawKeyEvent) {
            requireApi(19);
            KeyEvent keyEvent = new KeyEvent(
                    getLong(rawKeyEvent.get("downTime")),
                    getLong(rawKeyEvent.get("eventTime")),
                    (Integer) rawKeyEvent.get("action"),
                    (Integer) rawKeyEvent.get("code"),
                    (Integer) rawKeyEvent.get("repeat"),
                    (Integer) rawKeyEvent.get("metaState"),
                    (Integer) rawKeyEvent.get("deviceId"),
                    (Integer) rawKeyEvent.get("scancode"),
                    (Integer) rawKeyEvent.get("flags"),
                    (Integer) rawKeyEvent.get("source"));
            audioManager.dispatchMediaKeyEvent(keyEvent);
            return null;
        }

        private Object isVolumeFixed() {
            requireApi(21);
            return audioManager.isVolumeFixed();
        }

        private Object adjustStreamVolume(int streamType, int direction, int flags) {
            audioManager.adjustStreamVolume(streamType, direction, flags);
            return null;
        }

        private Object adjustVolume(int direction, int flags) {
            audioManager.adjustVolume(direction, flags);
            return null;
        }

        private Object adjustSuggestedStreamVolume(int direction, int suggestedStreamType, int flags) {
            audioManager.adjustSuggestedStreamVolume(direction, suggestedStreamType, flags);
            return null;
        }

        private Object getRingerMode() {
            return audioManager.getRingerMode();
        }

        private Object getStreamMaxVolume(int streamType) {
            return audioManager.getStreamMaxVolume(streamType);
        }

        private Object getStreamMinVolume(int streamType) {
            requireApi(28);
            return audioManager.getStreamMinVolume(streamType);
        }

        private Object getStreamVolume(int streamType) {
            return audioManager.getStreamVolume(streamType);
        }

        private Object getStreamVolumeDb(int streamType, int index, int deviceType) {
            requireApi(28);
            return audioManager.getStreamVolumeDb(streamType, index, deviceType);
        }

        private Object setRingerMode(int ringerMode) {
            audioManager.setRingerMode(ringerMode);
            return null;
        }

        private Object setStreamVolume(int streamType, int index, int flags) {
            audioManager.setStreamVolume(streamType, index, flags);
            return null;
        }

        private Object isStreamMute(int streamType) {
            requireApi(23);
            return audioManager.isStreamMute(streamType);
        }

        private Object setSpeakerphoneOn(boolean enabled) {
            audioManager.setSpeakerphoneOn(enabled);
            return null;
        }

        private Object isSpeakerphoneOn() {
            return audioManager.isSpeakerphoneOn();
        }

        private Object setAllowedCapturePolicy(int capturePolicy) {
            requireApi(29);
            audioManager.setAllowedCapturePolicy(capturePolicy);
            return null;
        }

        private Object getAllowedCapturePolicy() {
            requireApi(29);
            return audioManager.getAllowedCapturePolicy();
        }

        private Object isBluetoothScoAvailableOffCall() {
            return audioManager.isBluetoothScoAvailableOffCall();
        }

        private Object startBluetoothSco() {
            audioManager.startBluetoothSco();
            return null;
        }

        private Object stopBluetoothSco() {
            audioManager.stopBluetoothSco();
            return null;
        }

        private Object setBluetoothScoOn(boolean enabled) {
            audioManager.setBluetoothScoOn(enabled);
            return null;
        }

        private Object isBluetoothScoOn() {
            return audioManager.isBluetoothScoOn();
        }

        private Object setMicrophoneMute(boolean enabled) {
            audioManager.setMicrophoneMute(enabled);
            return null;
        }

        private Object isMicrophoneMute() {
            return audioManager.isMicrophoneMute();
        }

        private Object setMode(int mode) {
            audioManager.setMode(mode);
            return null;
        }

        private Object getMode() {
            return audioManager.getMode();
        }

        private Object isMusicActive() {
            return audioManager.isMusicActive();
        }

        private Object generateAudioSessionId() {
            requireApi(21);
            return audioManager.generateAudioSessionId();
        }

        private Object setParameters(String parameters) {
            audioManager.setParameters(parameters);
            return null;
        }

        private Object getParameters(String keys) {
            return audioManager.getParameters(keys);
        }

        private Object playSoundEffect(int effectType, Double volume) {
            if (volume != null) {
                audioManager.playSoundEffect(effectType, (float) ((double) volume));
            } else {
                audioManager.playSoundEffect(effectType);
            }
            return null;
        }

        private Object loadSoundEffects() {
            audioManager.loadSoundEffects();
            return null;
        }

        private Object unloadSoundEffects() {
            audioManager.unloadSoundEffects();
            return null;
        }

        private Object getProperty(String arg) {
            requireApi(17);
            return audioManager.getProperty(arg);
        }

        private Object getDevices(int flags) {
            requireApi(23);
            ArrayList<Map<String, Object>> result = new ArrayList<>();
            AudioDeviceInfo[] devices = audioManager.getDevices(flags);
            for (int i = 0; i < devices.length; i++) {
                AudioDeviceInfo device = devices[i];
                String address = null;
                if (Build.VERSION.SDK_INT >= 28) {
                    address = device.getAddress();
                }
                result.add(mapOf(
                        "id", device.getId(),
                        "productName", device.getProductName(),
                        "address", address,
                        "isSource", device.isSource(),
                        "isSink", device.isSink(),
                        "sampleRates", intArrayToList(device.getSampleRates()),
                        "channelMasks", intArrayToList(device.getChannelMasks()),
                        "channelIndexMasks", intArrayToList(device.getChannelIndexMasks()),
                        "channelCounts", intArrayToList(device.getChannelCounts()),
                        "encodings", intArrayToList(device.getEncodings()),
                        "type", device.getType()
                ));
            }
            return result;
        }

        private Object getMicrophones() throws IOException {
            requireApi(28);
            ArrayList<Map<String, Object>> result = new ArrayList<>();
            List<MicrophoneInfo> microphones = audioManager.getMicrophones();
            for (MicrophoneInfo microphone : microphones) {
                List<List<Double>> frequencyResponse = new ArrayList<>();
                for (Pair<Float, Float> pair : microphone.getFrequencyResponse()) {
                    frequencyResponse.add(new ArrayList<Double>(Arrays.asList((double) ((float) pair.first), (double) ((float) pair.second))));
                }
                List<List<Integer>> channelMapping = new ArrayList<>();
                for (Pair<Integer, Integer> pair : microphone.getChannelMapping()) {
                    channelMapping.add(new ArrayList<Integer>(Arrays.asList(pair.first, pair.second)));
                }
                result.add(mapOf(
                        "description", microphone.getDescription(),
                        "id", microphone.getId(),
                        "type", microphone.getType(),
                        "address", microphone.getAddress(),
                        "location", microphone.getLocation(),
                        "group", microphone.getGroup(),
                        "indexInTheGroup", microphone.getIndexInTheGroup(),
                        "position", coordinate3fToList(microphone.getPosition()),
                        "orientation", coordinate3fToList(microphone.getOrientation()),
                        "frequencyResponse", frequencyResponse,
                        "channelMapping", channelMapping,
                        "sensitivity", microphone.getSensitivity(),
                        "maxSpl", microphone.getMaxSpl(),
                        "minSpl", microphone.getMinSpl(),
                        "directionality", microphone.getDirectionality()
                ));
            }
            return result;
        }

        private Object isHapticPlaybackSupported() {
            requireApi(29);
            return audioManager.isHapticPlaybackSupported();
        }

        private void registerNoisyReceiver() {
            if (noisyReceiver != null) return;
            noisyReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction())) {
                        invokeMethod("onBecomingNoisy");
                    }
                }
            };
            applicationContext.registerReceiver(noisyReceiver, new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY));
        }

        private void unregisterNoisyReceiver() {
            if (noisyReceiver == null || applicationContext == null) return;
            applicationContext.unregisterReceiver(noisyReceiver);
            noisyReceiver = null;
        }

        private AudioAttributesCompat decodeAudioAttributes(Map<?, ?> attributes) {
            AudioAttributesCompat.Builder builder = new AudioAttributesCompat.Builder();
            if (attributes.get("contentType") != null) {
                builder.setContentType((Integer) attributes.get("contentType"));
            }
            if (attributes.get("flags") != null) {
                builder.setFlags((Integer) attributes.get("flags"));
            }
            if (attributes.get("usage") != null) {
                builder.setUsage((Integer) attributes.get("usage"));
            }
            return builder.build();
        }

        private void invokeMethod(String method, Object... args) {
            for (AndroidAudioManager instance : instances) {
                ArrayList<Object> list = new ArrayList<Object>(Arrays.asList(args));
                list.add(0, Integer.parseInt(sessionKey));
                instance.channel.invokeMethod(method, list);
            }
        }

        public void dispose() {
            abandonAudioFocus();
            if (Build.VERSION.SDK_INT >= 23) {
                disposeAudioDeviceCallback();
            }
            applicationContext = null;
            audioManager = null;
        }

        private void disposeAudioDeviceCallback() {
            audioManager.unregisterAudioDeviceCallback((AudioDeviceCallback) audioDeviceCallback);
        }
    }

    static void requireApi(int level) {
        if (Build.VERSION.SDK_INT < level)
            throw new RuntimeException("Requires API level " + level);
    }

    static Map<String, Object> mapOf(Object... args) {
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < args.length; i += 2) {
            map.put((String) args[i], args[i + 1]);
        }
        return map;
    }

    static ArrayList<Integer> intArrayToList(int[] a) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            list.add(a[i]);
        }
        return list;
    }

    static ArrayList<Double> doubleArrayToList(double[] a) {
        ArrayList<Double> list = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            list.add(a[i]);
        }
        return list;
    }

    static ArrayList<Double> coordinate3fToList(MicrophoneInfo.Coordinate3F coordinate) {
        ArrayList<Double> list = new ArrayList<>();
        list.add((double) coordinate.x);
        list.add((double) coordinate.y);
        list.add((double) coordinate.z);
        return list;
    }

    static Long getLong(Object o) {
        return (o == null || o instanceof Long) ? (Long) o : Long.valueOf((Integer) o);
    }
}
