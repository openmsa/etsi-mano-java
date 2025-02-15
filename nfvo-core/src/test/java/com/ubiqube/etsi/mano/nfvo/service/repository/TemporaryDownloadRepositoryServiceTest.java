/**
 * Copyright (C) 2019-2025 Ubiqube.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */
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
