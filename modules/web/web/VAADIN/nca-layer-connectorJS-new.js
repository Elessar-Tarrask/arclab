nca_layer_js_component_clicker = function () {
    // родитель на фронте кубы
    let connector = this;
    // путь к ncalayer
    let NCA_LAYER_URL = 'wss://127.0.0.1:13579/';
    let missed_heartbeats_limit_min = 10;
    let missed_heartbeats_limit_max = 50;
    let heartbeat_msg = '--heartbeat--';

    let METHOD_LOAD_SLOT_LIST = 'loadSlotList';
    let METHOD_BROWSE_KEY_STORE = 'browseKeyStore';
    let METHOD_GET_KEYS = 'getKeys';
    let METHOD_GET_SUBJECT_DN = 'getSubjectDN';
    let METHOD_GET_NOT_BEFORE = 'getNotBefore';
    let METHOD_GET_NOT_AFTER = 'getNotAfter';
    let METHOD_SIGN_XML = 'signXml';
    let METHOD_SIGN_PLAIN_DATA = 'signPlainData';
    let METHOD_SIGN_CMS = 'createCMSSignature';

    let KAZTOKEN_STORE = 'AKKaztokenStore';
    let AKK_ZID_CARD_STORE = 'AKKZIDCardStore';

    let CALLING_TIMEOUT = 500;
    const PKCS12_STORAGE_TYPE = 'PKCS12';
    const KAZTOKEN_STORAGE_TYPE = 'AKKaztokenStore';
    const ID_CARD_STORAGE_TYPE = 'AKKZIDCardStore';

    let heartbeat_interval = null;
    let missed_heartbeats = 0;

    let missed_heartbeats_limit = missed_heartbeats_limit_min;
    let webSocket = null;
    let callback = null;

    // фукнции отправляемые в кубу
    const SignInformationFunctions = {
        sendNotification: function (error, text) {
            connector.sendNotification(error, text);
        }
        ,
        signXmlBack: function (result) {
            unblockScreen();
            if (result['code'] === "500") {
                alert(result['message']);
            } else if (result['code'] === "200") {
                let res = result['responseObject'];
                connector.signXmlBack(res, new Date());
            }
        },
        signCmsBack: function (result) {
            unblockScreen();
            if (result['code'] === "500") {
                alert(result['message']);
            } else if (result['code'] === "200") {
                let res = result['responseObject'];
                connector.signCmsBack(res, new Date());
            }
        }
    };

    const unblockScreen = function () {
        $.unblockUI();
    };

    const blockScreen = function () {
        $.blockUI({
            message: '<br/>Подождите, выполняется операция в NCALayer...',
            css: {
                border: 'none',
                padding: '15px',
                backgroundColor: '#000',
                '-webkit-border-radius': '10px',
                '-moz-border-radius': '10px',
                opacity: .5,
                color: '#fff'
            }
        });
        //$.unblockUI();
    };

    connector.initNCALayerSocket = function () {
        this.webSocket = new WebSocket(NCA_LAYER_URL);
        this.webSocket.onopen = webSocketOnOpen.bind(this);
        this.webSocket.onclose = webSocketOnClose.bind(this);
        this.webSocket.onmessage = webSocketOnMessage.bind(this);
    }

    function getNCALayerSocket() {
        if (this.webSocket === null || this.webSocket === undefined || this.webSocket.readyState === 3 || this.webSocket.readyState === 2) {
            connector.initNCALayerSocket();
        }
        return this.webSocket;
    }

    function setMissedHeartbeatsLimitToMax() {
        this.missed_heartbeats_limit = missed_heartbeats_limit_max;
    }

    function setMissedHeartbeatsLimitToMin() {
        this.missed_heartbeats_limit = missed_heartbeats_limit_min;
    }

    function webSocketOnMessage(event) {
        if (event.data === heartbeat_msg) {
            this.missed_heartbeats = 0;
            return;
        }
        let result = JSON.parse(event.data);
        if (result != null) {
            let rw = {
                result: result['result'],
                secondResult: result['secondResult'],
                errorCode: result['errorCode'],
                getResult: function () {
                    return this.result;
                },
                getSecondResult: function () {
                    return this.secondResult;
                },
                getErrorCode: function () {
                    return this.errorCode;
                }
            };
            this.callback(rw);
        }
        console.log(event);
        setMissedHeartbeatsLimitToMin();
    }

    function ngOnInit() {
        webSocketOnMessage.bind(this);
    }

    function signXML(storageType, storagePath, alias, password, xmlToSign, callback) {
        let args = [storageType, storagePath, alias, password, xmlToSign];
        setTimeout(this.send(METHOD_SIGN_XML, args, callback), CALLING_TIMEOUT);
    }

    function signPlainData(storageType, storagePath, alias, password, xmlToSign, callback) {
        let args = [storageType, storagePath, alias, password, xmlToSign];
        setTimeout(this.send(METHOD_SIGN_PLAIN_DATA, args, callback), CALLING_TIMEOUT);
    }

    function signCMS(storageType, storagePath, alias, password, xmlToSign, attachments, callback) {
        let args = [storageType, storagePath, alias, password, xmlToSign, attachments];
        setTimeout(this.send(METHOD_SIGN_CMS, args, callback), CALLING_TIMEOUT);
    }

    function openChooseCertDialog(storageAlias, storagePath, callback) {
        let args = [storageAlias, 'P12', storagePath];
        setTimeout(this.send(METHOD_BROWSE_KEY_STORE, args, callback), CALLING_TIMEOUT);
    }

    function loadKazTokenSlotList(callback) {
        setTimeout(this.send(METHOD_LOAD_SLOT_LIST, [KAZTOKEN_STORE], callback), CALLING_TIMEOUT);
    }

    function loadIDCardSlotList(callback) {
        setTimeout(this.send(METHOD_LOAD_SLOT_LIST, [AKK_ZID_CARD_STORE], callback), CALLING_TIMEOUT);
    }

    function loadKeys(storageType, storagePath, password, callback) {
        let args = [storageType, storagePath, password, 'SIGN'];
        setTimeout(this.send(METHOD_GET_KEYS, args, callback), CALLING_TIMEOUT);
    }

    function getKeySubjectDN(storageType, storagePath, alias, password, callback) {
        let args = [storageType, storagePath, alias, password];
        setTimeout(this.send(METHOD_GET_SUBJECT_DN, args, callback), CALLING_TIMEOUT);
    }

    function loadExpireBefore(storageType, storagePath, alias, password, callback) {
        let args = [storageType, storagePath, alias, password];
        setTimeout(this.send(METHOD_GET_NOT_BEFORE, args, callback), CALLING_TIMEOUT);
    }

    function loadExpireAfter(storageType, storagePath, alias, password, callback) {
        let args = [storageType, storagePath, alias, password];
        setTimeout(this.send(METHOD_GET_NOT_AFTER, args, callback), CALLING_TIMEOUT);
    }

    function send(method, args, callback) {
        let methodVariable = {
            'method': method,
            'args': args
        };
        console.log("send");
        console.log(callback);
        if (callback) {
            this.callback = callback;
        }
        setMissedHeartbeatsLimitToMax();
        getNCALayerSocket().send(JSON.stringify(methodVariable));
    }

    function openDialog() {

        //this.modalService.open(this.helpDialog, {backdrop: 'static'});
        //this.contentModal.close();

        alert(
            "error"
        );
    }

    function pingLayer() {
        try {
            getNCALayerSocket().send(heartbeat_msg);
        } catch (e) {
            clearInterval(this.heartbeat_interval);
            this.heartbeat_interval = null;
            SignInformationFunctions.sendNotification(302, "Соединение прервано");
            console.warn("Closing connection. Reason: " + e.message);
            getNCALayerSocket().close();
        }
    }

    function webSocketOnOpen(event) {
        if (this.heartbeat_interval === null) {
            this.missed_heartbeats = 0;
            this.heartbeat_interval = setInterval(pingLayer.bind(this), 2000);
        }
    }

    function webSocketOnClose(event) {
        if (event.wasClean) {
            SignInformationFunctions.sendNotification(301, "Соединение прервано");
        } else {
            SignInformationFunctions.sendNotification(302, "Соединение прервано");
        }
        SignInformationFunctions.sendNotification(event.code, ' Reason: ' + event.reason);
    }
}