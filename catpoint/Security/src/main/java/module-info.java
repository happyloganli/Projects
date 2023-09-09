module Security {
    requires miglayout;
    requires java.desktop;
    requires com.google.gson;
    requires com.google.common;
    requires java.prefs;
    requires Image;
    opens com.udacity.catpoint.security.data to com.google.gson;
}