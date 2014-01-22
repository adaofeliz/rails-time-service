package com.adaofeliz.railstime.context;

import java.util.Locale;
import java.util.TimeZone;

/**
 * User: ADAO
 * Date: 5/2/13 - 6:55 AM
 */

@ContextSource
public class UserContext {

    private Locale userLocale;
    private TimeZone userTimezone;

    public Locale getUserLocale() {
        return userLocale;
    }

    public void setUserLocale(Locale userLocale) {
        this.userLocale = userLocale;
    }

    public TimeZone getUserTimezone() {
        return userTimezone;
    }

    public void setUserTimezone(TimeZone userTimezone) {
        this.userTimezone = userTimezone;
    }

    @Override
    public String toString() {
        return "userLocale: " + this.getUserLocale() + " userTimezone: " + this.getUserTimezone();
    }
}
