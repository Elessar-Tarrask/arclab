package com.company.arclab.web.screens.kalkan;

import com.company.arclab.entity.client.TClientDigitalSign;
import com.company.arclab.entity.kalkan.EdsRegistry;
import com.company.arclab.event.UpdateEcpListEvent;
import com.company.arclab.service.KalkanSignVerifyService;
import com.haulmont.cuba.core.app.FileStorageService;
import com.haulmont.cuba.core.entity.Entity;
import com.haulmont.cuba.core.entity.FileDescriptor;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Events;
import com.haulmont.cuba.core.global.FileStorageException;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.UiComponents;
import com.haulmont.cuba.gui.app.core.inputdialog.InputDialog;
import com.haulmont.cuba.gui.app.core.inputdialog.InputParameter;
import com.haulmont.cuba.gui.components.ContentMode;
import com.haulmont.cuba.gui.components.DialogAction;
import com.haulmont.cuba.gui.components.PasswordField;
import com.haulmont.cuba.gui.components.ScreenFacet;
import com.haulmont.cuba.gui.components.actions.BaseAction;
import com.haulmont.cuba.gui.components.inputdialog.InputDialogAction;
import com.haulmont.cuba.gui.icons.CubaIcon;
import com.haulmont.cuba.gui.model.DataContext;
import com.haulmont.cuba.gui.model.InstanceContainer;
import com.haulmont.cuba.gui.model.InstancePropertyContainer;
import com.haulmont.cuba.gui.screen.Screen;
import com.haulmont.cuba.gui.screen.ScreenFragment;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;
import com.haulmont.cuba.security.entity.User;
import com.haulmont.cuba.security.global.UserSession;
import com.haulmont.cuba.web.gui.components.JavaScriptComponent;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.haulmont.cuba.gui.components.inputdialog.InputDialogAction.action;

@UiController("arclab_KalkanCryptSignXmlFragment")
@UiDescriptor("kalkan-crypt-sign-xml-fragment.xml")
public class KalkanCryptSignXmlFragment extends ScreenFragment {

    // Constants
    private final static String baseErrorText = "<div style=\"\n" +
            "    padding: 10px 10px 20px 10px;\n" +
            "    background-color: #f8d7da;\n" +
            "    border-color: #f5c6cb;\n" +
            "    border-radius: 5px;\n" +
            "    margin-bottom: 15px;\n" +
            "\">Ошибка при подключении к NCALayer. Убедитесь что программа запущена и настроена. " +
            "Для установки NCALayer перейдите на адрес <a href='https://pki.gov.kz/ncalayer/' target='_blank'> " +
            "https://pki.gov.kz/ncalayer/</a>  или прочитайте " +
            "<a href=\"https://pki.gov.kz/docs/nl_ru/\">Руководство пользователя NCALayer</a></div>";

    public static final String EVENT_OUTCOME_ERROR = "error";
    public static final String EVENT_OUTCOME_CANCEL = "cancel";

    @Inject
    private JavaScriptComponent ncaLayerShowJs;
    @Inject
    private Notifications notifications;

    @Inject
    private KalkanSignVerifyService kalkanSignVerifyService;
    @Inject
    private FileStorageService fileStorageService;
    @Inject
    private Metadata metadata;
    @Inject
    private UserSession userSession;
    @Inject
    private DataManager dataManager;
    @Inject
    private Events events;

    private Boolean isExists;
    @Inject
    private Logger log;
    @Inject
    private Dialogs dialogs;
//    @Inject
//    private InstancePropertyContainer<TClientDigitalSign> digitalSignDc;

    private InputDialog inputDialog;
    private InputDialogAction actionSendPassword = null;
    @Inject
    private ScreenFacet<DigitalSignatoryInfo> digitalSignScreen;

    private Boolean isConnectionOpened = false;
    @Inject
    private DataContext dataContext;
    @Inject
    private InstanceContainer<EdsRegistry> edsRegistryDc;
    @Inject
    private UiComponents uiComponents;
    @Inject
    private InstanceContainer<TClientDigitalSign> digitalSignDc;


    @Subscribe
    public void onInit(InitEvent event) {
        TClientDigitalSign digitalSign = metadata.create(TClientDigitalSign.class);
        digitalSignDc.setItem(digitalSign);
        // get ecp key path
        ncaLayerShowJs.addFunction("getKeyPath", callbackEvent -> {
            try {
                String storagePath = callbackEvent.getArguments().getString(0);
                digitalSignDc.getItem().setStoragePath(storagePath);
                showPasswordChecker();
            } catch (Exception err) {
//                atTheEndDo(err, "Ошибка получения пути к ЭЦП :" + err.getLocalizedMessage(), EVENT_OUTCOME_ERROR);
                atTheEndDo(err, EVENT_OUTCOME_ERROR);
            }
        });


        ncaLayerShowJs.addFunction("loadKeysBack", callbackEvent -> {
            try {
                List<String> items = Arrays.asList(callbackEvent.getArguments().getString(0).split("\\|"));
                digitalSignDc.getItem().setSignatoryType(items.get(0));
                digitalSignDc.getItem().setSignatoryName(items.get(1));
                digitalSignDc.getItem().setSignatoryFirstKey(items.get(2));
                digitalSignDc.getItem().setSignatorySecondKey(items.get(3));
                ncaLayerCallFunction("getKeySubjectDN");
            } catch (Exception err) {
//                atTheEndDo(err,"Ошибка получения типов ключей : " + err.getLocalizedMessage(), EVENT_OUTCOME_ERROR);
                atTheEndDo(err, EVENT_OUTCOME_ERROR);
            }
        });

        ncaLayerShowJs.addFunction("getKeySubjectDNBack", callbackEvent -> {
            try {
                List<String> items = Arrays.asList(callbackEvent.getArguments().getString(0).split(","));
                digitalSignDc.getItem().setSignatoryCN(Arrays.asList(items.get(0).split("=")).get(1));
                digitalSignDc.getItem().setSignatorySurname(Arrays.asList(items.get(1).split("=")).get(1));
                digitalSignDc.getItem().setSignatorySerialNumber(Arrays.asList(items.get(2).split("=")).get(1));
                digitalSignDc.getItem().setSignatoryC(Arrays.asList(items.get(3).split("=")).get(1));
                digitalSignDc.getItem().setSignatoryG(Arrays.asList(items.get(4).split("=")).get(1));
                ncaLayerCallFunction("loadExpireBefore");
            } catch (Exception err) {
//                atTheEndDo(err,"Ошибка получения базовой информации из эцп: " + err.getLocalizedMessage(), EVENT_OUTCOME_ERROR);
                atTheEndDo(err, EVENT_OUTCOME_ERROR);
            }
        });

        ncaLayerShowJs.addFunction("loadExpireBeforeBack", callbackEvent -> {
            try {
                String dateFrom = callbackEvent.getArguments().getString(0);
                digitalSignDc.getItem().setSignatoryDateFrom(dateFrom);
                ncaLayerCallFunction("loadExpireAfter");
            } catch (Exception err) {
//                "Ошибка получения даты начала эцп : " + err.getLocalizedMessage()
                atTheEndDo(err, EVENT_OUTCOME_ERROR);
            }
        });

        ncaLayerShowJs.addFunction("loadExpireAfterBack", callbackEvent -> {
            try {
                String dateTill = callbackEvent.getArguments().getString(0);
                digitalSignDc.getItem().setSignatoryDateTill(dateTill);
                inputDialog.close(InputDialog.INPUT_DIALOG_OK_ACTION);
                dataContext.commit();
                digitalSignScreen.show();
            } catch (Exception err) {
                atTheEndDo(err, EVENT_OUTCOME_ERROR);
            }
        });

        // errors and notifications
        ncaLayerShowJs.addFunction("sendNotification", callbackEvent -> {
            int code = (int) callbackEvent.getArguments().getNumber(0);
            String connectionResult = callbackEvent.getArguments().getString(1);
            // функция отображения этапов связи с пользователем
            Boolean showNotificationRes = NotificationsConnectionProc(code, connectionResult);

            initSecondStep(showNotificationRes);
        });

        // xml sign unused
        ncaLayerShowJs.addFunction("signXmlBack", callbackEvent -> {
            showNotification("Документ подписан", Notifications.NotificationType.TRAY);
            //String value = callbackEvent.getArguments().getString(0);
            //edsRegistry.setSignDateTime(new Date());
            //signInformation.setSignedData(value);
            // TODO if needed add xml Param to edsRegistry
        });

        // cms sign
        ncaLayerShowJs.addFunction("signCmsBack", callbackEvent -> {
            showNotification("Документ подписан", Notifications.NotificationType.TRAY);
            String value = callbackEvent.getArguments().getString(0);
            EdsRegistry edsRegistry = edsRegistryDc.getItem();
            edsRegistry.setSignDateTime(new Date());
            edsRegistry.setSignedCmsWithFileInfo(value);
            edsRegistry = kalkanSignVerifyService.isGoodSignature(edsRegistry);     // проверяем подпись на валидность
            digitalSignDc.getItem().setSignatoryPassword(null);                     // обнуляем пароль
            dataManager.commit(edsRegistry);
            atTheEndDo(null, edsRegistryDc.getItem().getSignAction());
        });
    }

    @Subscribe(target = Target.PARENT_CONTROLLER)
    public void onBeforeShow(Screen.BeforeShowEvent event) {
        edsRegistryDc.setItem(dataManager.create(EdsRegistry.class));
        dataContext.evict(edsRegistryDc.getItem());
    }


    // функция вызова js на основе названия функции в js файле
    private void ncaLayerCallFunction(String jsFunctionName) {
        Map<String, String> myMap = setMapData();
        ncaLayerShowJs.setState(myMap);
        ncaLayerShowJs.callFunction(jsFunctionName);
    }

    private Map<String, String> setMapData() {
        Map<String, String> myMap = new HashMap<>();
        if (edsRegistryDc.getItemOrNull() != null) {
            myMap.put("checkSum", edsRegistryDc.getItem().getFileCheckSum());              // hash который подписываем
            myMap.put("storagePath", digitalSignDc.getItem().getStoragePath());            // путь к хранилищу ключа
            myMap.put("password", digitalSignDc.getItem().getSignatoryPassword());         // пароль transient параметр, для безопасности каждый раз обнуляется
            myMap.put("key", digitalSignDc.getItem().getSignatorySecondKey());             // ключ используемый дял подписи (кажется)
            myMap.put("flag", "true");                                                     // доп параметр отвечающий за включение publicKey в подпись (не используется)
        }
        return myMap;
    }

    private EdsRegistry checkIfSignAlreadyExists(FileDescriptor fileDescriptor, Entity entity, User user) {
        Optional<EdsRegistry> edsRegistryFromDb = dataManager.load(EdsRegistry.class)
                .query("select o from arclab_EdsRegistry o where " +
                        "o.fileToSign = :fileToSign and o.currentUser = :currentUser")
                .view("edsRegistry-view")
                .parameter("fileToSign", fileDescriptor)
                .parameter("currentUser", user)
                .optional();
        isExists = edsRegistryFromDb.isPresent();
        if (isExists) {
            edsRegistryDc.setItem(edsRegistryFromDb.get());
        }
        return edsRegistryDc.getItem();
    }


    public void setInfoToSign(FileDescriptor fileDescriptor, Entity entity, String action, String comment) {
        try {
            if (fileDescriptor == null) {
                atTheEndDo(new Exception("Файл ЭЦП не найден."), EVENT_OUTCOME_ERROR);
                throw new IllegalArgumentException("Файл не присутствует");
            }
            String checkSum = setFileDescriptor(fileDescriptor);
            if (checkSum == null)
                throw new IllegalArgumentException("Файл не присутствует");
//            if (entity == null)
//                throw new IllegalArgumentException("Не присутствует сущность родителя");

            User user = userSession.getUser();

            EdsRegistry edsRegistry = checkIfSignAlreadyExists(fileDescriptor, entity, user);
            // подгружаем информацию о текущем профиле
            getTOfficerDigitalSignInfo();
            edsRegistry.setFileToSign(fileDescriptor);
            edsRegistry.setSignComment(comment);
            //edsRegistry.setEntity(entity);
            edsRegistry.setSignAction(action);
            edsRegistry.setFileCheckSum(setFileDescriptor(fileDescriptor));
            edsRegistry.setCurrentUser(user);
            // вызываем connection с ncaLayer
            if (!isConnectionOpened)
                ncaLayerShowJs.callFunction("initNCALayerSocket");
            else
                initSecondStep(true);
            if (isExists)
                showNotification("Подпись уже имеется, новая подпись переподпишет старую подпись", Notifications.NotificationType.WARNING);
        } catch (IllegalArgumentException e) {
            showWarningNotification(e);
        }
    }

    private void getTOfficerDigitalSignInfo() {
//        TOfficer officer = officerService.getOfficerByUser(userSession.getCurrentOrSubstitutedUser());
//        officer = dataManager.reload(officer, "tOfficer-profileView");
//        tOfficerDc.setItem(dataContext.merge(officer));
//        if (tOfficerDc.getItem().getDigitalSign() == null) {
//            tOfficerDc.getItem().setDigitalSign(metadata.create(TClientDigitalSign.class));
//            tOfficerDc.getItem().getDigitalSign().setStoragePath("~");
//        }
//        tOfficerDc.getItem().getDigitalSign().setSignatoryAction(null);
    }

    public String setFileDescriptor(FileDescriptor fileDescriptor) {
        byte[] bytes = new byte[0];
        try {
            bytes = fileStorageService.loadFile(fileDescriptor);
        } catch (FileStorageException exception) {
            showNotification("Проблема с получением файла " + exception.getLocalizedMessage(), Notifications.NotificationType.WARNING);
        }
        if (bytes.length > 0) {
            return getMd5Hash(bytes);
        }
        return null;
    }

    private String getMd5Hash(byte[] bytes) {
        return DigestUtils
                .md5Hex(bytes).toUpperCase();
    }

    private void showNotification(String message, Notifications.NotificationType notificationType) {
        notifications.create(notificationType)
                .withCaption(message)
                .show();
    }

    private void showWarningNotification(Throwable e) {
        log.warn("Warning", e);
        showNotification(e.getMessage(), Notifications.NotificationType.WARNING);
    }

    private void showPasswordChecker() {
        actionSendPassword = action("confirm")
                .withCaption("Открыть")
                .withPrimary(true)
                .withIcon("")
                .withHandler(actionEvent -> {
                    InputDialog dialog = actionEvent.getInputDialog();
                    String password = dialog.getValue("password");
                    digitalSignDc.getItem().setSignatoryPassword(password);
                    ncaLayerCallFunction("loadKeys");
                    actionSendPassword.setEnabled(false);
                });

        inputDialog = dialogs.createInputDialog(this)
                .withCaption("Введите пароль от ЭЦП")
                .withParameter(InputParameter.stringParameter("password")
                        .withRequired(true)
                        .withField(() -> {
                            PasswordField field = uiComponents.create(PasswordField.class);
                            field.setCaption("Password");

                            field.setWidthFull();
                            return field;
                        }))
                .withActions(actionSendPassword,
                        action("showPassword")
                                .withCaption("Показать пароль")
                                .withIcon(CubaIcon.VIEW_ACTION)
                                .withHandler(event -> {
                                    InputDialog inputDialog = event.getInputDialog();
                                    String password = inputDialog.getValue("password");
                                    notifications.create(Notifications.NotificationType.WARNING)
                                            .withCaption(password)
                                            .show();
                                })
                )
                .withCloseListener(closeEvent -> {
                    if (closeEvent.getCloseAction().equals(InputDialog.WINDOW_CLOSE_ACTION)) {
                        showNotification("Отмена действия подписания, причина : не указан пароль", Notifications.NotificationType.TRAY);
                        atTheEndDo(null, "SignCancelAction");
                    }
                }).show();
    }

    private void showCustomHtmlDialogs() {

//        Action cancelAction = new DialogAction(DialogAction.Type.CANCEL);
//        cancelAction.addPropertyChangeListener(e ->
//                atTheEndDo(null, EVENT_OUTCOME_CANCEL));

        dialogs.createOptionDialog()
                .withCaption("Ошибка")
                .withMessage(KalkanCryptSignXmlFragment.baseErrorText)
                .withWidth("700px")
                .withType(Dialogs.MessageType.CONFIRMATION)
                .withContentMode(ContentMode.HTML)
                .withActions(
                        new BaseAction("customAction")
                                .withCaption("Попробовать снова")
                                .withIcon("font-icon:REFRESH")
                                .withHandler(e ->
                                        ncaLayerShowJs.callFunction("initNCALayerSocket")
                                ),
                        new BaseAction("customAction")
                                .withCaption("Пропустить этап подписания")
                                .withDescription("Добалено для процесса тестирования без ЭЦП")
                                .withIcon("font-icon:SEND_O")
                                .withHandler(e -> {
                                            showNotification("Пропуск подписания", Notifications.NotificationType.TRAY);
                                            atTheEndDo(null, edsRegistryDc.getItem().getSignAction());
                                        }
                                ),
//                        cancelAction
                        new DialogAction(DialogAction.Type.CANCEL)
                )
                .show();
    }

    private void sendEventBack(String signAction) {
        events.publish(new UpdateEcpListEvent(this, edsRegistryDc.getItem().getCurrentUser(),
                edsRegistryDc.getItem(), signAction, edsRegistryDc.getItem().getFileCheckSum()));
    }

    private Boolean NotificationsConnectionProc(int code, String connectionResult) {
        switch (code) {
            case 201:  // успешный случай открытия соединения с ncalayer
                isConnectionOpened = true;
                showNotification(connectionResult, Notifications.NotificationType.TRAY);
                return true;
            case 202:  // ошибка отмены действия подписания
                showNotification("Отмена действия подписания, причина : " + connectionResult, Notifications.NotificationType.TRAY);
                atTheEndDo(null, "SignCancelAction");
                break;
            case 203:  // Нет данных для подписи! нереальная ошибка которая возможно только при checkSum == null || checkSum == ""
                showNotification(connectionResult, Notifications.NotificationType.ERROR);
                break;
            case 205:  // вызывается в случае если первоначально не RSA ключ
                ncaLayerCallFunction("openChooseCertDialog");
                inputDialog.close(InputDialog.INPUT_DIALOG_OK_ACTION);
                showNotification(connectionResult, Notifications.NotificationType.TRAY);
                break;
            case 204:  // вызывается в случае если был указан неверный пароль при указании пароля эцп
                actionSendPassword.setEnabled(true);
            case 301:  // вызывается в случае если соединение было успешно закрыто
                isConnectionOpened = false;
                showNotification(connectionResult, Notifications.NotificationType.TRAY);
                break;
            case 302:  // вызывается в случае если соединение было закрыто насильно
            default:   // ну тут default
                isConnectionOpened = false;
                showNotification(connectionResult, Notifications.NotificationType.TRAY);
                showCustomHtmlDialogs();
        }
        return false;
    }

    private void atTheEndDo(Throwable err, String signAction) {
        if (actionSendPassword != null) actionSendPassword.setEnabled(true);
        ncaLayerCallFunction("webSocketOnClose");
        if (err != null) {
            showNotification(err.getMessage(), Notifications.NotificationType.ERROR);
            log.error("Error on ECP", err);
        }
        if (signAction != null)
            sendEventBack(signAction);
    }

    private void initSecondStep(Boolean showNotificationRes) {
        if (showNotificationRes && digitalSignDc.getItem().getStoragePath() != null && digitalSignDc.getItem().getStoragePath().length() > 1)
            showPasswordChecker();
        else if (showNotificationRes)
            ncaLayerCallFunction("openChooseCertDialog");
    }

    @Subscribe("digitalSignScreen")
    public void onDigitalSignScreenAfterClose(ScreenFacet.AfterCloseEvent event) {
        if (digitalSignDc.getItem().getSignatoryAction() == null) {
            NotificationsConnectionProc(202, "Закрыто пользователем");
        } else if (digitalSignDc.getItem().getSignatoryAction().equals("signActionCMS"))
            ncaLayerCallFunction("signCMS");
        else if ((digitalSignDc.getItem().getSignatoryAction().equals("chooseAnotherCertificate"))) {
            ncaLayerCallFunction("openChooseCertDialog");
        }
    }

    @Subscribe(target = Target.PARENT_CONTROLLER)
    public void onBeforeClose(Screen.BeforeCloseEvent event) {
        if (isConnectionOpened)
            ncaLayerCallFunction("webSocketOnClose");
    }

    @Subscribe("digitalSignScreen")
    public void onDigitalSignScreenAfterShow(ScreenFacet.AfterShowEvent event) {
        DigitalSignatoryInfo a = (DigitalSignatoryInfo) event.getScreen();
        a.setTClientDigitalSignDc(digitalSignDc.getItem());
    }
}