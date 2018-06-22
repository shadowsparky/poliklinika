/*
 * Copyright (c) Samsonov Eugene, 2018.
 */

package ru.shadowsparky.autisticsdevelopers.poliklinika;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Build;

import java.util.ArrayList;
import java.util.Collections;

public class Shorcuts_Create {
    Context _context;

    Shorcuts_Create(Context _context){
        this._context = _context;
    }

    public void setShortcut(boolean isUserLogin){
        if (isUserLogin && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = _context.getSystemService(ShortcutManager.class);
            // NOTE: You MUST specify an Action on the intents. Otherwise it will crash
            Intent home = new Intent(_context, AddAppointmentMenu.class);
            home.setAction(Intent.ACTION_VIEW);
            Intent profile = new Intent(_context, AppointmentList.class);
            profile.setAction(Intent.ACTION_VIEW);

            Intent[] intents = new Intent[] {home, profile};
            ShortcutInfo shortcut = new ShortcutInfo.Builder(_context, "addAppointment")
                    .setIntents(intents)
                    .setShortLabel("Добавить запись")
                    .setLongLabel("Добавить запись на прием к врачу")
                    .setIcon(Icon.createWithResource(_context, R.drawable.ic_add_appointment_24dp))
                    .build();
            shortcutManager.setDynamicShortcuts(Collections.singletonList(shortcut));
        } else if (!isUserLogin && Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            ShortcutManager shortcutManager = _context.getSystemService(ShortcutManager.class);
            ArrayList<String> s = new ArrayList<>();
            s.add("addAppointment");
            shortcutManager.disableShortcuts(s);
        }
    }
}
