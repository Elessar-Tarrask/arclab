nca_layer_js_component_clicker = function () {
    let connector = this;
    connector.getState().data = {storagePath: "", password: "", checkSum: "", key: ""};
    let storageName = "PKCS12";
    let NCA_LAYER_URL = 'wss://127.0.0.1:13579/';
    let missed_heartbeats_limit_max = 50;
    let missed_heartbeats_limit_min = 30;
    let missed_heartbeats_limit = missed_heartbeats_limit_min;
    let METHOD_GET_KEYS = 'getKeys';
    let METHOD_GET_SUBJECT_DN = 'getSubjectDN';
    let METHOD_GET_NOT_BEFORE = 'getNotBefore';
    let METHOD_GET_NOT_AFTER = 'getNotAfter';
    let METHOD_SIGN_CMS = 'createCMSSignature';
    connector.callback = null;
    let webSocket = null;
    let CALLING_TIMEOUT = 100;
    let METHOD_BROWSE_KEY_STORE = 'browseKeyStore';
    let heartbeat_msg = '--heartbeat--';
    connector.heartbeat_interval = null;
    let missed_heartbeats = 0;

    const SignInformationFunctions = {
        getKeyPath: function (result) {
            if (result.getResult())
                connector.getKeyPath(result.getResult());
            else
                SignInformationFunctions.sendNotification(202, "Отмена действия");
        },
        loadKeysBack: function (result) {
            if (result.getResult() && result.getResult() !== -1)
                connector.loadKeysBack(result.getResult());
            else if (result.getErrorCode() === "LOAD_KEYSTORE_ERROR")
                SignInformationFunctions.sendNotification(205, "Ошибка получения эцп по сохраненому пути");
            else if (result.getErrorCode() === "EMPTY_KEY_LIST")
                SignInformationFunctions.sendNotification(205, "Выберите эцп ключ с типом RSA");
            else
                SignInformationFunctions.sendNotification(204, "Неправильный пароль");
        },
        getKeySubjectDNBack: function (result) {
            if (result.getResult())
                connector.getKeySubjectDNBack(result.getResult());
            else
                SignInformationFunctions.sendNotification(202, "Ошибка получения базовой информации из эцп");
        },
        loadExpireBeforeBack: function (result) {
            if (result.getResult())
                connector.loadExpireBeforeBack(result.getResult());
            else
                SignInformationFunctions.sendNotification(202, "Ошибка получения даты начала эцп");
        },
        loadExpireAfterBack: function (result) {
            if (result.getResult())
                connector.loadExpireAfterBack(result.getResult());
            else
                SignInformationFunctions.sendNotification(202, "Ошибка получения даты окончания эцп");
        },
        sendNotification: function (int, text) {
            connector.sendNotification(int, text);
        },
        signXmlBack: function (result) {
            if (result['code'] === "500") {
                SignInformationFunctions.sendNotification(202, result['message']);
            } else if (result['code'] === "200") {
                let res = result['responseObject'];
                connector.signXmlBack(res, new Date());
            }
        },
        signCmsBack: function (result) {
            if (result.getResult())
                connector.signCmsBack(result.getResult());
            else
                SignInformationFunctions.sendNotification(202, "Ошибка при подписи данных, причина: " + result.getErrorCode());

        }
    };

    function setMissedHeartbeatsLimitToMax() {
        missed_heartbeats_limit = missed_heartbeats_limit_max;
    }

    function setMissedHeartbeatsLimitToMin() {
        missed_heartbeats_limit = missed_heartbeats_limit_min;
    }

    function customSend(method, args, callback) {
        let methodVariable = {
            'method': method,
            'args': args
        };
        if (callback) {
            connector.callback = callback;
        }
        setMissedHeartbeatsLimitToMax();
        getNCALayerSocket().send(JSON.stringify(methodVariable));
    }

    connector.initNCALayerSocket = function () {
        webSocket = new WebSocket(NCA_LAYER_URL);
        webSocket.onopen = webSocketOnOpen.bind(this);
        webSocket.onclose = webSocketOnClose.bind(this);
        webSocket.onmessage = webSocketOnMessage.bind(this);
    }

    function getNCALayerSocket() {
        if (webSocket === null || webSocket === undefined || webSocket.readyState === 3 || webSocket.readyState === 2) {
            connector.initNCALayerSocket();
        }
        return webSocket;
    }

    const openDialog = function () {
        if (confirm("Ошибка при подключении к NCALayer. Запустите NCALayer и нажмите ОК") === true) {
            location.reload();
        }
    };

    function pingLayer() {
        try {
            missed_heartbeats++;
            console.error(missed_heartbeats, missed_heartbeats_limit);
            if (missed_heartbeats > missed_heartbeats_limit)
                throw new Error("exceeded timeLimit");
            getNCALayerSocket().send(heartbeat_msg);
        } catch (e) {
            clearInterval(connector.heartbeat_interval);
            connector.heartbeat_interval = null;
            SignInformationFunctions.sendNotification(301, "Превышено время ожидания. Попробуйте снова");
            getNCALayerSocket().close();
        }
    }

    function webSocketOnOpen(event) {
        // if (connector.heartbeat_interval === null) {
        //     missed_heartbeats = 0;
        //     console.error("open", missed_heartbeats);
        //     clearInterval(connector.heartbeat_interval);
        //     connector.heartbeat_interval = setInterval(pingLayer.bind(this), 2000);
        // }
        SignInformationFunctions.sendNotification(201, "Соединение открыто");
    }

    function webSocketOnClose(event) {
        if (event.wasClean) {
            SignInformationFunctions.sendNotification(301, "Соединение закрыто успешно");
        } else {
            SignInformationFunctions.sendNotification(302, "Соединение закрыто насильно.");
        }
    }

    function webSocketOnMessage(event) {
        if (event.data === heartbeat_msg) {
            missed_heartbeats = 0;
            console.error("onMessage", missed_heartbeats);
            return;
        }

        let result = JSON.parse(event.data);

        if (result != null) {
            let rw = {
                code: result['code'],
                message: result['message'],
                responseObject: result['responseObject'],
                result: result['result'],
                secondResult: result['secondResult'],
                errorCode: result['errorCode'],
                getResult: function () {
                    return this.result;
                },
                getMessage: function () {
                    return this.message;
                },
                getResponseObject: function () {
                    return this.responseObject;
                },
                getCode: function () {
                    return this.code;
                },
                getSecondResult: function () {
                    return this.secondResult;
                },
                getErrorCode: function () {
                    return this.errorCode;
                }
            };
            if (connector.callback != null) {
                SignInformationFunctions[connector.callback](rw);
                connector.callback = null;
            }
        }
        setMissedHeartbeatsLimitToMin();
    }

    const getKeyInfo = function (storageName, callBack) {
        let getKeyInfo = {
            "module": "kz.gov.pki.knca.commonUtils",
            "method": "getKeyInfo",
            "args": [storageName]
        };
        connector.callback = callBack;
        getNCALayerSocket().send(JSON.stringify(getKeyInfo));
    };

    const createCAdESFromBase64 = function (storageName, keyType, base64ToSign, flag, callBack) {
        let createCAdESFromBase64 = {
            "module": "kz.gov.pki.knca.commonUtils",
            "method": "createCAdESFromBase64",
            "args": [storageName, keyType, base64ToSign, flag]
        };
        connector.callback = callBack;
        getNCALayerSocket().send(JSON.stringify(createCAdESFromBase64));
    };

    const signXml = function (storageName, keyType, xmlToSign, callBack) {
        let signXml = {
            "module": "kz.gov.pki.knca.commonUtils",
            "method": "signXml",
            "args": [storageName, keyType, xmlToSign, "", ""]
        };
        connector.callback = callBack;
        getNCALayerSocket().send(JSON.stringify(signXml));
    };

    const signXmls = function (storageName, keyType, xmlsToSign, callBack) {
        let signXmls = {
            "module": "kz.gov.pki.knca.commonUtils",
            "method": "signXmls",
            "args": [storageName, keyType, xmlsToSign, "", ""]
        };
        connector.callback = callBack;
        getNCALayerSocket().send(JSON.stringify(signXmls));
    };

    const getKeyInfoCall = function () {
        getKeyInfo(storageName, "getKeyInfoBack");
    };

    const getKeyInfoBack = function (result) {
        if (result['code'] === "500") {
            SignInformationFunctions.sendNotification(202, "Отмена действия");
        } else if (result['code'] === "200") {
            console.log(result);
        }
    };

    connector.getKeyInfoCallButton = function () {
        getKeyInfoCall();
    };

    connector.signXmlCall = function () {
        let signInformation = this.getState().data;
        let xml = signInformation.signXml ? signInformation.signXml : "";
        signXml(storageName, "SIGNATURE", xml, "signXmlBack");
    }

    connector.signCmsCall = function () {
        let signInformation = this.getState().data;
        let checkSum = signInformation.checkSum ? signInformation.checkSum : "";
        if (checkSum !== null && checkSum !== "") {
            createCAdESFromBase64(storageName, "SIGNATURE", checkSum, true, "signCmsBack");
        } else {
            SignInformationFunctions.sendNotification(203, "Нет данных для подписи!");
        }
    }

    connector.signCMS = function () {
        let args = [storageName, this.getState().data.storagePath, this.getState().data.key, this.getState().data.password, this.getState().data.checkSum, true];
        setTimeout(customSend, CALLING_TIMEOUT, METHOD_SIGN_CMS, args, "signCmsBack");
    }

    connector.openChooseCertDialog = function () {
        let data = this.getState().data;
        let storagePath = data.storagePath ? data.storagePath : "~";
        let args = [storageName, 'P12', storagePath];
        setTimeout(customSend, CALLING_TIMEOUT, METHOD_BROWSE_KEY_STORE, args, "getKeyPath");
    }

    connector.loadKeys = function () {
        let args = [storageName, this.getState().data.storagePath, this.getState().data.password, 'SIGN'];
        setTimeout(customSend, CALLING_TIMEOUT, METHOD_GET_KEYS, args, "loadKeysBack");
    }
    connector.getKeySubjectDN = function () {
        let args = [storageName, this.getState().data.storagePath, this.getState().data.key, this.getState().data.password];
        setTimeout(customSend, CALLING_TIMEOUT, METHOD_GET_SUBJECT_DN, args, "getKeySubjectDNBack");
    }
    connector.loadExpireBefore = function () {
        let args = [storageName, this.getState().data.storagePath, this.getState().data.key, this.getState().data.password];
        setTimeout(customSend, CALLING_TIMEOUT, METHOD_GET_NOT_BEFORE, args, "loadExpireBeforeBack");
    }
    connector.loadExpireAfter = function () {
        let args = [storageName, this.getState().data.storagePath, this.getState().data.key, this.getState().data.password];
        setTimeout(customSend, CALLING_TIMEOUT, METHOD_GET_NOT_AFTER, args, "loadExpireAfterBack");
    }
    connector.webSocketOnClose = function () {
        try {
            getNCALayerSocket().close();
        } catch (error) {
            alert("Ошибка: обратитесь к разработчику системы - " + error)
        }
    }
}