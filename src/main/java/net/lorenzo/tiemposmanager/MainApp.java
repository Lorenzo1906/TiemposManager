package net.lorenzo.tiemposmanager;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader.ProgressNotification;
import javafx.application.Preloader.StateChangeNotification;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import net.lorenzo.tiemposmanager.gui.main.MainPresenter;
import net.lorenzo.tiemposmanager.gui.reporte.ReportePresenter;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp extends Application {

    public static void main(String[] args) {
        LauncherImpl.launchApplication(MainApp.class, AppPreloader.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        notifyPreloader(new ProgressNotification(10d));
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TiemposManagerAppFactory.class);
        notifyPreloader(new ProgressNotification(50d));
        MainPresenter mainPresenter = context.getBean(MainPresenter.class);
        mainPresenter.setTabs();
        ReportePresenter reportePresenter = context.getBean(ReportePresenter.class);
        reportePresenter.inicializeComboBox();
        notifyPreloader(new ProgressNotification(90d));

        Scene scene = new Scene(mainPresenter.getView(), 590, 419);
        scene.getStylesheets().add("/styles/fxmlapp.css");
        stage.setScene(scene);
        stage.setTitle("Tiempos Manager");
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);

        notifyPreloader(new StateChangeNotification(StateChangeNotification.Type.BEFORE_START));
        Platform.runLater(stage::show);

        stage.show();
    }
}
