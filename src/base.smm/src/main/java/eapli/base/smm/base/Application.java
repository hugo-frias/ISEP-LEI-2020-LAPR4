package eapli.base.smm.base;

public class Application {
    
    public static final String APP_TITLE = "Sistema de Monitorização de Máquinas (SMM)";

    private static final AppSettings SETTINGS = new AppSettings();

    public static AppSettings settings() {
        return SETTINGS;
    }

    private Application() {
        // private visibility to ensure singleton & utility
    }
}
