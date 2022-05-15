package com.company.arclab.service;

import com.company.arclab.entity.application.Application;
import com.haulmont.addon.bproc.entity.ProcessInstanceData;
import com.haulmont.addon.bproc.entity.TaskData;
import com.haulmont.addon.bproc.query.ProcessInstanceDataQuery;
import com.haulmont.addon.bproc.service.BprocHistoricService;
import com.haulmont.addon.bproc.service.BprocRuntimeService;
import com.haulmont.cuba.core.global.Messages;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Comparator;
import java.util.List;

@Service(BPMNService.NAME)
public class BPMNServiceBean implements BPMNService {

    private final String MSG_PACK = "kz.almanit.jcrm.entity.messages.properties";
    private String MSG_DESCRIPTION = "bpmPanel.description";

    @Inject
    private ApplicationService applicationService;
    @Inject
    private BprocRuntimeService bprocRuntimeService;
    @Inject
    private BprocHistoricService bprocHistoricService;
    @Inject
    private Messages messages;

    @Override
    public ProcessInstanceData getProcessInstanceByApp(Application application, boolean findSubprocess) {
        if (application != null) {
            // Если заявка отменена, отказана, завершена - процесс будет остановленным,
            // а значит поиск надо вести не только по активным!
            boolean onlyActive = applicationService.isActiveApp(application);

            if (application.getProcId() != null) {
                if (findSubprocess) {
                    //Поиск подпроцесса
                    ProcessInstanceDataQuery dataQuery = bprocRuntimeService.createProcessInstanceDataQuery()
                            .superProcessInstanceId(application.getProcId()).orderByStartTime().desc();
                    //            if(onlyActive){
                    dataQuery.active();
                    //            }
                    List<ProcessInstanceData> subprocessList = dataQuery.list();
                    if (subprocessList != null && !subprocessList.isEmpty()) {
                        //вернем в первую очередь этот активный подпроцесс, если такой есть
                        return subprocessList.get(0);
                    }
                }
                //если нет подпроцесса - искать основной родительский процесс
                List<ProcessInstanceData> processList;
                if (!onlyActive) { //bprocHistoricService - для поиска завершенных процессов
                    // Почему список? в заявке уже есть processInstanceId... В экземпляре процесса поле id уникален.
                    processList = bprocHistoricService.createHistoricProcessInstanceDataQuery()
                            .processInstanceId(application.getProcId()).orderByProcessInstanceStartTime().desc().list();
                } else {            //bprocRuntimeService - для поиска активных процессов!
                    processList = bprocRuntimeService.createProcessInstanceDataQuery()
                            .processInstanceId(application.getProcId()).orderByStartTime().desc().list();
                }
                processList.sort(Comparator.comparing(ProcessInstanceData::getStartTime));
                if (processList != null && !processList.isEmpty()) {
                    // нужен родительский
                    return processList.get(0);
                }
            }
        }
        return null;
    }

    public String getTaskDescription(TaskData task) {
        StringBuffer description = new StringBuffer();

        if (task.getDescription() != null) {
            description.append("<p><b>" + messages.getMessage(MSG_PACK, MSG_DESCRIPTION)
                    + ": </b>\t" + task.getDescription() + "</p>");
        }

        return description.toString();
    }
}