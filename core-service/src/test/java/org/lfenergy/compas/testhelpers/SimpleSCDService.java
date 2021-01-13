// SPDX-FileCopyrightText: 2020 RTE FRANCE
//
// SPDX-License-Identifier: Apache-2.0

package org.lfenergy.compas.testhelpers;

import org.lfenergy.compas.commons.MarshallerWrapper;
import org.lfenergy.compas.exception.ScdException;
import org.lfenergy.compas.scl.SCL;
import org.lfenergy.compas.scl.THeader;
import org.lfenergy.compas.scl.THitem;
import org.lfenergy.compas.service.AbstractSCDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.UUID;

@Service
public class SimpleSCDService extends AbstractSCDService<SimpleSCD, DefaultSCDSQLRepository> {

    @Autowired
    private MarshallerWrapper marshallerWrapper;

    public SimpleSCD initiateSCD(String filename, String hVersion, String hRevision) {
        SimpleSCD simpleSCD = new SimpleSCD();
        simpleSCD.setFileName(filename);

        SCL scd = new SCL();
        scd.setRelease((short) 4);
        scd.setVersion("2007");
        scd.setRevision("B");
        THeader tHeader = new THeader();
        tHeader.setRevision(hRevision);
        tHeader.setVersion(hVersion);
        UUID uuid = getNextID(simpleSCD);
        tHeader.setId(uuid.toString());
        scd.setHeader(tHeader);
        byte[] rawXml = marshallerWrapper.marshall(scd).getBytes();

        simpleSCD.setId(uuid);
        simpleSCD.setRawXml(rawXml);

        return repository.save(simpleSCD);
    }

    public SimpleSCD addHistoryItem(UUID id, String who, String what, String why) throws ScdException {
        SimpleSCD element = repository.findById(id)
                .orElseThrow(() ->  new ScdException("not found"));
        return addHistoryItem(element,who,what, why);
    }

    @Override
    public SimpleSCD addHistoryItem(SimpleSCD scdObj, String who, String what, String why) throws ScdException {
        SCL scd = marshallerWrapper.unmarshall(scdObj.getRawXml());

        THitem tHitem = new THitem();
        tHitem.setRevision(scd.getHeader().getRevision());
        tHitem.setVersion(scd.getHeader().getVersion());
        tHitem.setWho(who);
        tHitem.setWhat(what);
        tHitem.setWhen((new Date()).toString());
        tHitem.setWhy(why);

        THeader tHeader = scd.getHeader();
        Assert.notNull(tHeader, "Stored SCD must have Header tag");
        Assert.notNull(tHeader.getId(), "Stored SCD Header must have a unique ID");

        THeader.History history = tHeader.getHistory();
        if(history == null) {
            history = new THeader.History();
            tHeader.setHistory(history);
        }
        history.getHitem().add(tHitem);
        byte[] rawXml = marshallerWrapper.marshall(scd).getBytes();
        scdObj.setRawXml(rawXml);
        return repository.save(scdObj);
    }

    @Override
    public UUID getNextID(SimpleSCD value) {
        UUID uuid = value.getId();
        if(uuid == null){
            uuid = UUID.randomUUID();
        }
        while(repository.findById(uuid).isPresent()){
            uuid = UUID.randomUUID();
        }
        return uuid;
    }


    @Override
    public void addIED(UUID id, SCL icd) {

    }

    @Override
    public void addIED(UUID id, byte[] icd) {

    }
}
