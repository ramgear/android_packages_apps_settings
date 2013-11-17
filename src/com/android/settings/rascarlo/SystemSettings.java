/*
 * Copyright (C) 2012 The CyanogenMod project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.settings.rascarlo;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;
import android.provider.Settings;

import com.android.settings.R;
import com.android.settings.SettingsPreferenceFragment;
import com.android.settings.Utils;

public class SystemSettings extends SettingsPreferenceFragment implements
        Preference.OnPreferenceChangeListener {
    private static final String TAG = "SystemSettings";
    private static final String KEY_STATUS_BAR = "status_bar";
    private static final String KEY_LED_SETTINGS = "led_settings";

    private PreferenceScreen mStatusBar;
    private PreferenceScreen mLedSettings;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.system_settings);

        // don't show status bar preference screen on tablet
        // available settings right now are for phones only
        mStatusBar = (PreferenceScreen) findPreference(KEY_STATUS_BAR);
        if (mStatusBar != null) {
            if (Utils.isTablet(getActivity())) {
                getPreferenceScreen().removePreference(mStatusBar);
            }
        }

        // Led settings
        mLedSettings = (PreferenceScreen) findPreference(KEY_LED_SETTINGS);
        if (mLedSettings != null) {
            if (!getResources().getBoolean(
                    com.android.internal.R.bool.config_intrusiveNotificationLed)) {
                getPreferenceScreen().removePreference(mLedSettings);
            } else {
                updateLightPulseDescription();
            }
        }
    }

    private void updateLightPulseDescription() {
        if (Settings.System.getInt(getActivity().getContentResolver(),
                Settings.System.NOTIFICATION_LIGHT_PULSE, 0) == 1) {
            mLedSettings.setSummary(getString(R.string.enabled_string));
        } else {
            mLedSettings.setSummary(getString(R.string.disabled_string));
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        // TODO Auto-generated method stub
        return false;
    }
}
