package com.example.gearshop.utility;

import android.content.Context;
import android.content.Intent;

public class ActivityStartManager {
    public static void startTargetActivity(Context context, Class<?> targetActivityClass) {
        Intent intent = new Intent(context, targetActivityClass)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
