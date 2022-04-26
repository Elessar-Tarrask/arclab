package com.company.arclab.service;

import com.company.arclab.entity.kalkan.EdsRegistry;
import com.haulmont.cuba.core.global.Metadata;
import kz.gov.pki.kalkan.jce.provider.KalkanProvider;
import kz.gov.pki.kalkan.jce.provider.cms.CMSException;
import kz.gov.pki.kalkan.jce.provider.cms.CMSProcessableByteArray;
import kz.gov.pki.kalkan.jce.provider.cms.CMSSignedData;
import kz.gov.pki.kalkan.jce.provider.cms.SignerInformation;
import kz.gov.pki.kalkan.jce.provider.cms.SignerInformationStore;
import kz.gov.pki.kalkan.util.encoders.Base64;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.signature.XMLSignature;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import java.security.SignatureException;
import java.security.cert.CertStore;
import java.security.cert.CertStoreException;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service(KalkanSignVerifyService.NAME)
public class KalkanSignVerifyServiceBean implements KalkanSignVerifyService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(KalkanSignVerifyServiceBean.class);
    @Inject
    private Metadata metadata;

    TypeOfRespondent typeOfRespondent;
    private String verifyErrorMsg = "";
    static String providerName = "No_Name";
    static String kalkanErrorMessage = "";

    static AtomicBoolean canWorkWithKalkan = new AtomicBoolean(false);

    static void loadProvider() {
        try {
            Provider kalkanProvider = new KalkanProvider();
            //Добавление провайдера в java.security.Security
            boolean exists = false;
            Provider[] providers = Security.getProviders();
            for (Provider p : providers) {
                if (p.getName().equals(kalkanProvider.getName())) {
                    exists = true;
                }
            }
            if (!exists) {
                Security.addProvider(kalkanProvider);
            } else {
                // да нужно заменять провайдер каждый раз когда загружаеться класс, иначе провайдер будет не доступен;
                Security.removeProvider(kalkanProvider.getName());
                Security.addProvider(kalkanProvider);
            }
            canWorkWithKalkan.set(true);
            providerName = kalkanProvider.getName();

            // Почему Error, а не Exception -
            // чтобы поймать например ошибки когда провайдер скомпилированный под яву 1.7 запускаетьс на  яве 1.6
        } catch (Error ex) {
            canWorkWithKalkan.set(false);
        }
    }

    enum TypeOfRespondent {
        FIRM(1), PERSON(2);
        private final int code;

        TypeOfRespondent(int aCode) {
            this.code = aCode;
        }

        public int getCode() {
            return code;
        }

        public static TypeOfRespondent findByCode(int seekCode) {
            for (TypeOfRespondent seekType : TypeOfRespondent.values()) {
                if (seekType.getCode() == seekCode) {
                    return seekType;
                }
            }
            return null;
        }
    }

    @Override
    public List<EdsRegistry> verifyXml(String xmlString) {
        List<EdsRegistry> result = new ArrayList<>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            dbf.setNamespaceAware(true);
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document doc = documentBuilder.parse(new ByteArrayInputStream(xmlString.getBytes("UTF-8")));

            Element sigElement = null;
            Element rootEl = (Element) doc.getFirstChild();

            NodeList list = rootEl.getElementsByTagName("ds:Signature");
            int length = list.getLength();
            for (int i = 0; i < length; i++) {
                Node sigNode = list.item(length - (i + 1));
                sigElement = (Element) sigNode;
                if (sigElement == null) {
                    log.error("Bad signature: Element 'ds:Reference' is not found in XML document");
                }
                XMLSignature signature = new XMLSignature(sigElement, "");
                KeyInfo ki = signature.getKeyInfo();
                X509Certificate cert = ki.getX509Certificate();
                if (cert != null) {
                    //result.add(signature.checkSignatureValue(cert));
                    rootEl.removeChild(sigElement);
                }
            }
        } catch (Exception e) {
            log.error("Error", e);
        }
        return result;
    }

    private EdsRegistry createEdsRegistry() {
        metadata.create(EdsRegistry.class);
        return null;
    }

    @Override
    public EdsRegistry isGoodSignature(EdsRegistry edsRegistry) {
        loadProvider();

        if (!canWorkWithKalkan.get()) {
            verifyErrorMsg = "Провайдер 'KalKan' не был загружен. Причина:" + kalkanErrorMessage;
            return null;
        }
        Boolean result = verifyCMSSignature(edsRegistry);
        edsRegistry.setSignResult(result);
        edsRegistry.setSignErrorMessage(verifyErrorMsg);
        return edsRegistry;
    }


    private Boolean verifyCMSSignature(EdsRegistry edsRegistry) {
        verifyErrorMsg = "Ошибка не определена";
        try {
            CMSSignedData cms = createCMSSignedData(edsRegistry.getSignedCmsWithFileInfo(), edsRegistry.getFileCheckSum());

            SignerInformationStore signers = cms.getSignerInfos();
            CertStore clientCerts = cms.getCertificatesAndCRLs("Collection", providerName);
            if (!reCheckClientSignature(signers, clientCerts, edsRegistry)) {
                return false;
            }
//            if (isBadBinOrIin(signers, clientCerts)) {
//                return false;
//            }
//            if (isBadKeyUsage(signers, clientCerts)) {
//                return false;
//            }
//            try {
//                if (checkNucOneCertificateType(signers, clientCerts)) {
//                    return true;
//                } else if (checkNucTwoCertificateType(signers, clientCerts)) {
//                    return true;
//                } else {
//                    verifyErrorMsg = "Сертификат(ы) подписавший отчет не был выдан НУЦ РК.";
//                    return false;
//                }
//            } catch (Exception ex) {
//                verifyErrorMsg = ex.getMessage();
//                return false;
//            }
            verifyErrorMsg = "";
            return true;
        } catch (Exception e) {
            if ((e.getCause() instanceof SignatureException)) {
                verifyErrorMsg = "SIGNATURE_VALIDATION_ERROR : " + e.getMessage();
            } else {
                verifyErrorMsg = "COMMON_ERROR : " + e.getMessage();
            }
            log.error(verifyErrorMsg);
            return false;
        }
    }

    private CMSSignedData createCMSSignedData(String signatureToVerify, String signedData) throws CMSException, IOException {
        CMSSignedData cms = new CMSSignedData(Base64.decode(signatureToVerify));
        boolean isAttachedContent = cms.getSignedContent() != null;
        if (isAttachedContent) {
            cms = new CMSSignedData(cms.getEncoded());
        } else {
            CMSProcessableByteArray data = new CMSProcessableByteArray(signedData.getBytes(StandardCharsets.UTF_8));
            cms = new CMSSignedData(data, cms.getEncoded());
        }
        return cms;
    }

    private boolean reCheckClientSignature(SignerInformationStore signers, CertStore clientCerts, EdsRegistry edsRegistry) throws
            CertStoreException, NoSuchAlgorithmException, NoSuchProviderException, CMSException {
        Iterator it = signers.getSigners().iterator();

        boolean overAllResult = true;
        while (it.hasNext()) {
            SignerInformation signer = (SignerInformation) it.next();
            X509CertSelector signerConstraints = signer.getSID();
            Collection<? extends Certificate> certCollection = clientCerts.getCertificates(signerConstraints);
            Iterator<? extends Certificate> certIt = certCollection.iterator();
            int indexOfSigner = 0;
            while (certIt.hasNext()) {
                indexOfSigner++;
                X509Certificate cert = (X509Certificate) certIt.next();
                try {
                    cert.checkValidity();
                    String[] arr = SplitCertSubjectInfo(cert.getSubjectDN().getName());
                    edsRegistry.setSignerIinBin(getFullName(arr, "SERIALNUMBER"));
                    edsRegistry.setSignerFio(getFullName(arr, "CN"));
                    overAllResult = (overAllResult) && (signer.verify(cert, providerName));
                } catch (CertificateExpiredException ex) {
                    verifyErrorMsg = "Срок действия Сертификата которым подписали отчет прошел!";
                    return false;
                } catch (CertificateNotYetValidException ex) {
                    verifyErrorMsg = "Сертификат которым подписали отчет уже не действителен!";
                    return false;
                }
            }
            if (indexOfSigner == 0) {
                verifyErrorMsg = "Есть подпись данных, но не найден сертификат чтобы перепроверить эту подпись!";
            }

            if (!overAllResult) {
                verifyErrorMsg = "Перепроверка подписи данных и сертификата дала ошибку!";
            }
        }
        return overAllResult;
    }

    private String[] SplitCertSubjectInfo(String subjectDn) {
        if (!subjectDn.isEmpty())
            // example :: CN=БАЙШЫҒАР БАҒЛАН,SURNAME=БАЙШЫҒАР,SERIALNUMBER=IIN950921300549,C=KZ,G=ШЕРАЛЫҰЛЫ
            return subjectDn.split(",");
        return null;
    }

    private String getFullName(String[] arr, String elem) {
        String fullName = "";
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].split("=")[0].equals(elem))
                fullName = arr[i].split("=")[1];
        }

        return fullName;
    }

//    private boolean checkNucOneCertificateType(SignerInformationStore signers, CertStore clientCerts) throws CertStoreException {
//
//        Iterator it = signers.getSigners().iterator();
//        boolean result = false;
//        while (it.hasNext()) {
//            SignerInformation signer = (SignerInformation) it.next();
//            X509CertSelector signerConstraints = signer.getSID();
//            Collection<? extends Certificate> certCollection = clientCerts.getCertificates(signerConstraints);
//            Iterator<? extends Certificate> certIt = certCollection.iterator();
//            if (certCollection.size() == 0) {
//                throw new RuntimeException("В Отчете не найдены сертификаты которыми подписан Отчет.");
//            }
//            while (certIt.hasNext()) {
//                X509Certificate userCert = (X509Certificate) certIt.next();
//                X509Certificate certForCheck = null;
//                boolean isMyVersion = false;
//                try {
//                    if (TypeOfRespondent.FIRM.equals(typeOfRespondent)) {
//                        X509Certificate certNuc1Gost = (X509Certificate) createCerificate_nuc1_gost();
//                        userCert.verify(certNuc1Gost.getPublicKey(), providerName);
//                        certForCheck = certNuc1Gost;
//                    } else {
//                        X509Certificate certNuc1Rsa = (X509Certificate) createCerificate_nuc1_rsa();
//                        userCert.verify(certNuc1Rsa.getPublicKey(), providerName);
//                        certForCheck = certNuc1Rsa;
//                    }
//
//                    isMyVersion = true;
//                } catch (Exception ex) {
//                    // не подписан сертификатом старого типа НУЦ 1
//                    result = false;
//                }
//                if (isMyVersion) { // если данные были подписанный сертфикатом от системы НУЦ_1 то
//                    try {
//                        certForCheck.checkValidity(); // проверяем валидность сертификата
//                    } catch (CertificateExpiredException ex) {
//                        throw new RuntimeException("Отчет подписан сертификатом НУЦ 1.0, но корневым сертификатом НУЦ 1.0 уже нельзя пользоваться");
//                    } catch (CertificateNotYetValidException ex) {
//                        throw new RuntimeException("Отчет подписан сертификатом НУЦ 1.0, но корневой сертификат НУЦ 1.0 уже не действителен.");
//                    }
//                    try {
//                        if (isNotRevokedCertNucOne(userCert)) {  // проверяем отозваность сертификата
//                            return true;
//                        } else {
//                            throw new RuntimeException("Cертификат подписавший отчет был отозван.");
//                        }
//                    } catch (Exception ex) {
//                        throw new RuntimeException(ex.getMessage());
//                    }
//                }
//            }
//        }
//        return result;
//    }
//
//    private boolean checkNucTwoCertificateType(SignerInformationStore signers, CertStore clientCerts) throws CertStoreException {
//        Iterator it = signers.getSigners().iterator();
//        boolean result = false;
//        while (it.hasNext()) {
//            SignerInformation signer = (SignerInformation) it.next();
//            X509CertSelector signerConstraints = signer.getSID();
//            Collection certCollection = clientCerts.getCertificates(signerConstraints);
//            Iterator certIt = certCollection.iterator();
//            //System.out.println(  );
//            if (certCollection.size() == 0) {
//                throw new RuntimeException("В Отчете не найдены сертификаты которыми подписан Отчет.");
//            }
//            while (certIt.hasNext()) {
//                X509Certificate userCert = (X509Certificate) certIt.next();
//                boolean isMyVersion = false;
//                X509Certificate certForCheck = null;
//                try {
//                    if (TypeOfRespondent.FIRM.equals(typeOfRespondent)) {
//                        X509Certificate certNuc2Gost = (X509Certificate) createCerificate_nuc2_gost();
//                        X509Certificate certKucGost = (X509Certificate) createCerificate_kuc_gost();
//                        userCert.verify(certNuc2Gost.getPublicKey(), providerName);
//                        certNuc2Gost.verify(certKucGost.getPublicKey(), providerName);
//                        certForCheck = certNuc2Gost;
//                    } else {
//                        X509Certificate certNuc2Rsa = (X509Certificate) createCerificate_nuc2_rsa();
//                        X509Certificate certKucRsa = (X509Certificate) createCerificate_kuc_rsa();
//                        userCert.verify(certNuc2Rsa.getPublicKey(), providerName);
//                        certNuc2Rsa.verify(certKucRsa.getPublicKey(), providerName);
//                        certForCheck = certNuc2Rsa;
//                    }
//
//                    isMyVersion = true;
//                } catch (Exception ex) {
//                    result = false;
//
//                }
//                if (isMyVersion) { // если данные были подписанный сертфикатом от системы НУЦ_1 то
//                    try {
//                        certForCheck.checkValidity();
//                    } catch (CertificateExpiredException ex) {
//                        throw new RuntimeException("Отчет подписан сертификатом НУЦ 2.0, но корневым сертификатом НУЦ 2.0 уже нельзя пользоваться");
//                    } catch (CertificateNotYetValidException ex) {
//                        throw new RuntimeException("Отчет подписан сертификатом НУЦ 2.0, но корневой сертификат НУЦ 2.0 уже не действителен.");
//                    }
//
//                    try {
//                        if (isNotRevokedCertNucTwo(userCert)) {
//                            result = true;
//                            return true;
//                        } else {
//                            throw new RuntimeException("Cертификат подписавший отчет был отозван.");
//                        }
//                    } catch (Exception ex) {
//                        throw new RuntimeException(ex.getMessage());
//                    }
//                }
//
//            }
//        }
//        return result;
//    }
}