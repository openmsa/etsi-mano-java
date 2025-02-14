package com.ubiqube.etsi.mano.nfvo.service.repository;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ubiqube.etsi.mano.dao.mano.TemporaryDownload;
import com.ubiqube.etsi.mano.nfvo.jpa.TemporaryDownloadJpa;

class TemporaryDownloadRepositoryServiceTest {

    @Mock
    private TemporaryDownloadJpa temporaryJpa;

    @InjectMocks
    private TemporaryDownloadRepositoryService temporaryDownloadRepositoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        TemporaryDownload td = new TemporaryDownload();
        when(temporaryJpa.save(td)).thenReturn(td);

        TemporaryDownload result = temporaryDownloadRepositoryService.save(td);

        assertEquals(td, result);
        verify(temporaryJpa).save(td);
    }

    @Test
    void testFindByIdAndExpirationDateAfter() {
        String id = "testId";
        Date date = new Date();
        TemporaryDownload td = new TemporaryDownload();
        Optional<TemporaryDownload> optionalTd = Optional.of(td);
        when(temporaryJpa.findByIdAndExpirationDateAfter(id, date)).thenReturn(optionalTd);

        Optional<TemporaryDownload> result = temporaryDownloadRepositoryService.findByIdAndExpirationDateAfter(id, date);

        assertTrue(result.isPresent());
        assertEquals(td, result.get());
        verify(temporaryJpa).findByIdAndExpirationDateAfter(id, date);
    }
}
