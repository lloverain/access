package com.company;

import com.company.entity.DB;
import com.company.task.DetectStart;
import com.company.tool.AlertConfig;
import com.company.tool.Utile;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.SchedulerException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;

/**
 * @author 杨佳颖
 */
public class Main extends Application {

    private static Log logger = LogFactory.getLog("user");

    //整个程序的大小
    private static Scene scene;

    private final Desktop desktop = Desktop.getDesktop();

    public final String MysqlDriverClass = "com.mysql.cj.jdbc.Driver";
    public final String OracleDriverClass = "oracle.jdbc.driver.OracleDriver";
    public final String SqlServerDriverClass = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private final String appName = "Access比对工具——科欣";

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(appName);
        primaryStage.setWidth(750);
        primaryStage.setHeight(500);
        Image image = new Image("https://www.sckxyy.com/images/app/avatar-01.png");
        //添加程序图标
        primaryStage.getIcons().add(image);
        scene = new Scene(new Group());
        primaryStage.setScene(scene);
        //检测用户提供的配置文件是否为空
        if (AlertConfig.checkDB()) {
            //如果为空就让用户提供数据库类型，账户，密码
            selectPopUpBox(primaryStage);
        }
        //加载配置文件
        InputStream config = new BufferedInputStream(new FileInputStream("./db.properties"));
        Properties properties = new Properties();
        try {
            properties.load(config);
        } catch (IOException e) {
            UpdateStatistics("db.properties配置文件加载失败");
            logger.error("db.properties配置文件加载失败:" + e.toString());
        }

        //选择access数据库
        String jdbcurl = properties.getProperty("jdbcUrl");
        if (jdbcurl == null || jdbcurl.equals("") || jdbcurl.equals("null")) {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("请选择access数据库");
            configureFileChooser(fileChooser);
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                AlertConfig.AlertDBJdbcUrl(file.getPath());
            } else {
                System.exit(0);
                Platform.runLater(primaryStage::close);
            }
        }

        try {
            boolean statu = Utile.getAccessStatu();
            if (!statu) {
                ifIsZero(primaryStage);
            }
        } catch (Exception e) {
            errorHandle(primaryStage);
        }
        //整个布局组件
        SplitPane splitPane = new SplitPane();
        splitPane.setId("hiddenSplitter");
        //在主窗口添加左侧窗口和右侧窗口
        splitPane.getItems().addAll(leftWindow(), AlertAccessButton());
        //设置分频器位置
        splitPane.setDividerPositions(8, 2);
        scene = new Scene(splitPane, 1600, 700);
        primaryStage.setScene(scene);
        //子窗口随父窗口进行关闭
        primaryStage.setOnCloseRequest(event -> {
            //        按钮部分可以使用预设的也可以像这样自己 new 一个
            Alert _alert = new Alert(Alert.AlertType.CONFIRMATION, "你真的要退出吗？", new ButtonType("最小化到系统托盘", ButtonBar.ButtonData.NO),
                    new ButtonType("退出系统", ButtonBar.ButtonData.YES));
            //设置窗口的标题
            _alert.setTitle(appName);
            _alert.setHeaderText("确定退出");
            //设置对话框的 icon 图标，参数是主窗口的 stage
            _alert.initOwner(primaryStage);
            Optional<ButtonType> _buttonType = _alert.showAndWait();
            //根据点击结果返回
            if (_buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)) {
                Platform.setImplicitExit(true);
                System.exit(0);
                Platform.runLater(primaryStage::close);
            } else {
                if (primaryStage.isIconified()) {
                    primaryStage.setIconified(false);
                }
                primaryStage.toFront();
            }
        });


        //保证窗口关闭后，Stage对象仍然存活
        Platform.setImplicitExit(false);
        //构建系统托盘图标
        BufferedImage bufferedImage = getRemoteBufferedImage("https://www.sckxyy.com/images/app/avatar-01.png");
        TrayIcon trayIcon = new TrayIcon(bufferedImage, appName);
        trayIcon.setImageAutoSize(true);
        //获取系统托盘
        SystemTray tray = SystemTray.getSystemTray();
        //添加鼠标点击事件
        trayIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //双击左键
                if (e.getButton() == MouseEvent.BUTTON1 || e.getClickCount() == 2) {
                    Platform.runLater(() -> {
                        if (primaryStage.isIconified()) {
                            primaryStage.setIconified(false);
                        }
                        if (!primaryStage.isShowing()) {
                            primaryStage.show();
                        }
                        primaryStage.toFront();
                    });
                }
                //鼠标右键,关闭应用
                else if (e.getButton() == MouseEvent.BUTTON3) {
                    Platform.runLater(() -> {
                        if (!primaryStage.isShowing()) {
                            primaryStage.show();
                        }
                        //        按钮部分可以使用预设的也可以像这样自己 new 一个
                        Alert _alert = new Alert(Alert.AlertType.CONFIRMATION, "你真的要退出吗？", new ButtonType("取消", ButtonBar.ButtonData.NO),
                                new ButtonType("确定", ButtonBar.ButtonData.YES));
                        //设置窗口的标题
                        _alert.setTitle(appName);
                        _alert.setHeaderText("确定退出");
                        //设置对话框的 icon 图标，参数是主窗口的 stage
                        _alert.initOwner(primaryStage);
                        Optional<ButtonType> _buttonType = _alert.showAndWait();
                        //根据点击结果返回
                        if (_buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)) {
                            Platform.setImplicitExit(true);
                            tray.remove(trayIcon);
                            System.exit(0);
                            Platform.runLater(primaryStage::close);
                        }
                    });

                }
            }
        });
        //添加托盘图标
        tray.add(trayIcon);
        //程序显示
        primaryStage.show();

        //修改access按钮
        Button alertAccess = (Button) scene.lookup("#access");
        alertAccess.setOnAction(event -> {
            check(primaryStage);
        });
        Button alertDB = (Button) scene.lookup("#db");
        alertDB.setOnAction(event -> {
            selectPopUpBox(primaryStage);
        });

        startTimedTask();
    }

    /**
     * 选择数据库并配置信息
     *
     * @param primaryStage
     */
    private void selectPopUpBox(Stage primaryStage) {
        Dialog<Pair<String, DB>> dialog = new Dialog<>();
        dialog.setTitle(appName);
        dialog.setHeaderText("请配置数据库信息");
        //设置按钮
        ButtonType loginButtonType = new ButtonType("保存", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        //程序布局
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField ipTextField = new TextField();
        ipTextField.setPromptText("IP");
        TextField PortTextField = new TextField();
        PortTextField.setPromptText("端口");
        TextField name = new TextField();
        name.setPromptText("数据库名");
        TextField username = new TextField();
        username.setPromptText("账户");
        PasswordField password = new PasswordField();
        password.setPromptText("密码");

        ChoiceBox choiceBox = new ChoiceBox(FXCollections.observableArrayList(
                "Mysql", "Oracle", "SqlServer")
        );
        choiceBox.getSelectionModel().select(0);
        grid.add(new Label("IP"), 0, 0);
        grid.add(ipTextField, 1, 0);

        grid.add(new Label("端口"), 2, 0);
        grid.add(PortTextField, 3, 0);

        grid.add(new Label("数据库"), 0, 1);
        grid.add(choiceBox, 1, 1);

        grid.add(new Label("数据库名"), 0, 2);
        grid.add(name, 1, 2);

        grid.add(new Label("Username:"), 0, 3);
        grid.add(username, 1, 3);

        grid.add(new Label("Password:"), 0, 4);
        grid.add(password, 1, 4);

        //默认按钮不可点击
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);


        name.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(!(!name.getText().isEmpty() && !ipTextField.getText().isEmpty() && !PortTextField.getText().isEmpty()));
        });
        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(!(!name.getText().isEmpty() && !ipTextField.getText().isEmpty() && !PortTextField.getText().isEmpty()));
        });
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(!(!name.getText().isEmpty() && !ipTextField.getText().isEmpty() && !PortTextField.getText().isEmpty()));
        });
        dialog.getDialogPane().setContent(grid);

        //默认光标处于ip
        Platform.runLater(() -> ipTextField.requestFocus());

        final String[] dbName = {choiceBox.getItems().get(0).toString()};
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                dbName[0] = choiceBox.getItems().get(newValue.intValue()).toString();
            }
        });

        dialog.setResultConverter(dialogButton -> {
            DB db = new DB();
            db.setUser(username.getText());
            db.setPassword(password.getText());
            db.setName(name.getText());
            db.setIp(ipTextField.getText());
            db.setPort(PortTextField.getText());
            if (dialogButton == loginButtonType) {
                return new Pair<>(username.getText(), db);
            } else {
                if (!Utile.whetherToConnect()) {
                    System.exit(0);
                    Platform.runLater(primaryStage::close);
                }
            }
            return null;
        });

        Optional<Pair<String, DB>> result = dialog.showAndWait();

        result.ifPresent(dbPair -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.titleProperty().set(appName);
            alert.headerTextProperty().set("请稍等,检测数据库状态中");
            alert.showAndWait();
            if (dbName[0].equals("Mysql")) {
                String UrlPath = "jdbc:mysql://" + dbPair.getValue().getIp() + ":" + dbPair.getValue().getPort() + "/" + dbPair.getValue().getName() + "?serverTimezone=UTC&&useUnicode=true&characterEncoding=UTF-8";
                AlertConfig.AlertDB2Config(MysqlDriverClass, UrlPath, dbPair.getValue().getUser(), dbPair.getValue().getPassword());
                if (!Utile.whetherToConnect()) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.titleProperty().set(appName);
                    alert1.headerTextProperty().set("数据库无法连接，请重新配置");
                    alert1.showAndWait();
                    selectPopUpBox(primaryStage);
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.titleProperty().set(appName);
                    alert1.headerTextProperty().set("数据库连接成功！请选择Access数据库");
                    alert1.showAndWait();
                }
            }
            if (dbName[0].equals("Oracle")) {
                String UrlPath = "jdbc:oracle:thin:@" + dbPair.getValue().getIp() + ":" + dbPair.getValue().getPort() + ":" + dbPair.getValue().getName();
                AlertConfig.AlertDB2Config(OracleDriverClass, UrlPath, dbPair.getValue().getUser(), dbPair.getValue().getPassword());
                if (!Utile.whetherToConnect()) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.titleProperty().set(appName);
                    alert1.headerTextProperty().set("数据库无法连接，请重新配置");
                    alert1.showAndWait();
                    selectPopUpBox(primaryStage);
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.titleProperty().set(appName);
                    alert1.headerTextProperty().set("数据库连接成功！请选择Access数据库");
                    alert1.showAndWait();
                }
            }

            if (dbName[0].equals("SqlServer")) {
                String UrlPath = "jdbc:sqlserver://" + dbPair.getValue().getIp() + ":" + dbPair.getValue().getPort() + ";DatabaseName=" + dbPair.getValue().getName();
                AlertConfig.AlertDB2Config(SqlServerDriverClass, UrlPath, dbPair.getValue().getUser(), dbPair.getValue().getPassword());
                if (!Utile.whetherToConnect()) {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.titleProperty().set(appName);
                    alert1.headerTextProperty().set("数据库无法连接，请重新配置");
                    alert1.showAndWait();
                    selectPopUpBox(primaryStage);
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert1.titleProperty().set(appName);
                    alert1.headerTextProperty().set("数据库连接成功！请选择Access数据库");
                    alert1.showAndWait();
                }
            }
        });
    }

    /**
     * 这是按钮单击事件的处理办法
     *
     * @param primaryStage
     */
    private void check(Stage primaryStage) {
        //弹出文件选择框
        final FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("请选择access数据库");
        configureFileChooser(fileChooser);
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            //修改配置文件
            AlertConfig.AlertDBJdbcUrl(file.getPath());
            try {
                boolean statu = Utile.getAccessStatu();
                if (!statu) {
                    ifIsZero(primaryStage);
                }
            } catch (Exception e) {
                //如果检查错误，则弹出警告窗口，再次调用该方法
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle(appName);
                alert.setHeaderText("读取数据库错误");
                alert.setContentText("数据库无法读，请检查数据库是否异常！请重新选择数据库！");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    check(primaryStage);
                }

            }
        }
    }

    /**
     * 无法读取数据库时处理
     *
     * @param primaryStage
     */
    private void errorHandle(Stage primaryStage) {
        //弹出错误窗口
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(appName);
        alert.setHeaderText("读取数据库错误");
        alert.setContentText("数据库无法读，请检查数据库是否异常！请重新选择数据库！");
        Optional<ButtonType> result = alert.showAndWait();
        //点击确定则重选数据库加载
        if (result.get() == ButtonType.OK) {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("请选择access数据库");
            configureFileChooser(fileChooser);
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                //选择文件后读取数量
                AlertConfig.AlertDBJdbcUrl(file.getPath());
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle(appName);
                alert1.setHeaderText("请稍等");
                alert1.setContentText("读取数据库中！");
                alert1.showAndWait();
                try {
                    boolean statu = Utile.getAccessStatu();
                    if (!statu) {
                        ifIsZero(primaryStage);
                    }
                } catch (Exception e) {
                    errorHandle(primaryStage);
                }

            } else {
                //不选择就程序退出
                System.exit(0);
                Platform.runLater(primaryStage::close);
            }
        }
    }

    /**
     * 如果查询出的结果为0处理办法
     *
     * @param primaryStage
     */
    private void ifIsZero(Stage primaryStage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(appName);
        alert.setHeaderText("数据库无法连接或无法匹配，请重新选择！");
        alert.setContentText("是否正确？若有误，请重新选择！");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            final FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("请选择access数据库");
            configureFileChooser(fileChooser);
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                AlertConfig.AlertDBJdbcUrl(file.getPath());
                Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                alert1.setTitle(appName);
                alert1.setHeaderText("请稍等");
                alert1.setContentText("读取数据库中！");
                alert1.showAndWait();
                try {
                    boolean statu = Utile.getAccessStatu();
                    if (!statu) {
                        ifIsZero(primaryStage);
                    }
                } catch (Exception e) {
                    errorHandle(primaryStage);
                }
            }
        } else {
            System.exit(0);
            Platform.runLater(primaryStage::close);
        }
    }

    /**
     * 左窗口
     *
     * @return
     */
    private VBox leftWindow() {
        TextArea textArea = new TextArea();
        textArea.setId("log");
        textArea.setMinHeight(200);
        textArea.setMaxHeight(400);
        textArea.setEditable(false);
        VBox vBox = new VBox();
        vBox.setMinWidth(120);
        vBox.setPadding(new Insets(5));
        vBox.getChildren().addAll(textArea);
        return vBox;
    }

    /**
     * 按钮组
     *
     * @return
     */
    private VBox AlertAccessButton() {
        Button button = new Button("更换源数据库");
        button.setId("access");
        Button button1 = new Button("更换目标数据库");
        button1.setId("db");
        VBox vBox = new VBox();
        vBox.setMinWidth(120);
        vBox.setPadding(new Insets(5));
        vBox.getChildren().addAll(button, button1);
        return vBox;
    }

    /**
     * 文件选择类型限制
     *
     * @param fileChooser
     */
    private static void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("ACCDB", "*.accdb"),
                new FileChooser.ExtensionFilter("MDB", "*.mdb")
        );
    }

    /**
     * 打开文件
     *
     * @param file
     */
    private void openFile(File file) {
        EventQueue.invokeLater(() -> {
            try {
                desktop.open(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }


    /**
     * 更新UI
     *
     * @param
     */
    public static void UpdateStatistics(String talk) {
        Platform.runLater(() -> {
            TextArea textArea = (TextArea) scene.lookup("#log");
            if (textArea != null) {
                textArea.appendText(talk + "\n");
            }
        });

    }

    /**
     * 定时任务
     */
    public void startTimedTask() {
        try {
            UpdateStatistics("定时任务已开启！");
            logger.info("定时任务已开启！");
            DetectStart.job();
        } catch (SchedulerException e) {
            UpdateStatistics("定时任务开启失败！");
            logger.info("定时任务开启失败！");
        }
    }

    /**
     * 获取远程网络图片信息
     *
     * @param imageURL
     * @return
     */
    public static BufferedImage getRemoteBufferedImage(String imageURL) {
        URL url = null;
        InputStream is = null;
        BufferedImage bufferedImage = null;
        try {
            url = new URL(imageURL);
            is = url.openStream();
            bufferedImage = ImageIO.read(is);
        } catch (Exception e) {
            Main.UpdateStatistics("logo加载失败");
            logger.info("logo加载失败");
            return null;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                return null;
            }
        }
        return bufferedImage;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
