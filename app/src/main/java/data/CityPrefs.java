package data;

import android.app.Activity;
import android.content.SharedPreferences;

public class CityPrefs {
    static SharedPreferences prefs;
      public CityPrefs(Activity activity)
      {
          prefs = activity.getPreferences(Activity.MODE_PRIVATE);
      }
      public String getCity()
      {
          return prefs.getString("City", "Spokane,Us");
      }
      public static void setCity(String city)
      {
          prefs.edit().putString("City",city).commit();
      }
}
