package com.idw.project.cataloguemovie.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.SwitchPreference;
import android.provider.Settings;
import androidx.annotation.Nullable;

import com.idw.project.cataloguemovie.R;
import com.idw.project.cataloguemovie.reminder.DailyAlarmReminder;
import com.idw.project.cataloguemovie.reminder.ReleaseTVShowTodayReminder;
import com.idw.project.cataloguemovie.reminder.ReleaseTodayReminder;
import com.idw.project.cataloguemovie.rest.ApiClient;
import com.idw.project.cataloguemovie.rest.ApiInterface;

public class PreferenceFragment extends android.preference.PreferenceFragment implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {

    private DailyAlarmReminder dailyAlarmReminder = new DailyAlarmReminder();
    private ReleaseTodayReminder releaseTodayReminder = new ReleaseTodayReminder();
    private ReleaseTVShowTodayReminder releaseTVShowTodayReminder = new ReleaseTVShowTodayReminder();

    Preference languagePreference;
    SwitchPreference dailyReminderPreference, releaseTodayReminderPreference;

    String language, daily_reminder, release_today_reminder;

    ApiInterface apiService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        apiService = ApiClient.getClient().create(ApiInterface.class);

        language = getResources().getString(R.string.key_language);
        daily_reminder = getResources().getString(R.string.key_daily_reminder);
        release_today_reminder = getResources().getString(R.string.key_release_reminder);

        languagePreference = findPreference(language);
        dailyReminderPreference = (SwitchPreference) findPreference(daily_reminder);
        releaseTodayReminderPreference = (SwitchPreference) findPreference(release_today_reminder);


        dailyReminderPreference.setOnPreferenceChangeListener(this);
        releaseTodayReminderPreference.setOnPreferenceChangeListener(this);

        languagePreference.setOnPreferenceClickListener(this);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        String key = preference.getKey();
        boolean isSet = (boolean) newValue;

        if (key.equals(daily_reminder)) {
            if (isSet) {
                dailyAlarmReminder.setRepeatingAlarm(getActivity());
            } else {
                dailyAlarmReminder.cancelAlarm(getActivity());
            }
        }else {
            if (isSet) {
                releaseTodayReminder.setRepeatingAlarm(getActivity());
                releaseTVShowTodayReminder.setRepeatingAlarm(getActivity());
            } else {
                releaseTodayReminder.cancelAlarm(getActivity());
                releaseTVShowTodayReminder.cancelAlarm(getActivity());
            }
        }

        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        String key = preference.getKey();

        if (key.equals(language)) {
            Intent languageIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(languageIntent);
        }
        return true;
    }

}
