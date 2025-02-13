package com.ubiqube.etsi.mano.service.vim;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ubiqube.etsi.mano.dao.mano.vim.VimConnectionInformation;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.ClusterMachine;
import com.ubiqube.etsi.mano.dao.mano.vim.vnfi.CnfInformations;
import com.ubiqube.etsi.mano.service.sys.SysImage;
import com.ubiqube.etsi.mano.vim.dto.Flavor;

@ExtendWith(MockitoExtension.class)
class CnfInformationVerifierTest {
	@Mock
	private Vim vim;
	@Mock
	private Network network;
	@Mock
	private Storage storage;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testNoCnfInfo() {
		VimConnectionInformation vci = new VimConnectionInformation();
		CnfInformationVerifier cnfInformationVerifier = CnfInformationVerifier.of(vim, vci);
		cnfInformationVerifier.verifyCnf();
	}

	@Test
	void testMissingNetworkName() {
		VimConnectionInformation vci = new VimConnectionInformation();
		CnfInformations cnfInfo = new CnfInformations();
		vci.setCnfInfo(cnfInfo);
		CnfInformationVerifier cnfInformationVerifier = CnfInformationVerifier.of(vim, vci);
		assertThrows(VimException.class, () -> cnfInformationVerifier.verifyCnf());
	}

	@Test
	void testMissingNetworkIdNetNotFound() {
		VimConnectionInformation vci = new VimConnectionInformation();
		CnfInformations cnfInfo = new CnfInformations();
		cnfInfo.setExtNetwork("networkName");
		vci.setCnfInfo(cnfInfo);
		CnfInformationVerifier cnfInformationVerifier = CnfInformationVerifier.of(vim, vci);
		when(vim.network(vci)).thenReturn(network);
		assertThrows(VimException.class, () -> cnfInformationVerifier.verifyCnf());
	}

	@Test
	void test() {
		VimConnectionInformation vci = new VimConnectionInformation();
		CnfInformations cnfInfo = new CnfInformations();
		cnfInfo.setExtNetwork("networkName");
		cnfInfo.setExtNetworkId("networkId");
		ClusterMachine master = new ClusterMachine();
		master.setFlavor("flavor");
		cnfInfo.setMaster(master);
		ClusterMachine worker = new ClusterMachine();
		worker.setFlavorId("id");
		cnfInfo.setWorker(worker);
		vci.setCnfInfo(cnfInfo);
		CnfInformationVerifier cnfInformationVerifier = CnfInformationVerifier.of(vim, vci);
		when(vim.network(vci)).thenReturn(network);
		when(network.search(NetowrkSearchField.NAME, List.of("networkName"))).thenReturn(List.of(new NetworkObject("id", "name")));
		Flavor fl00 = new Flavor();
		fl00.setName("flavor");
		fl00.setId("id");
		when(vim.getFlavorList(vci)).thenReturn(List.of(fl00));
		when(vim.storage(vci)).thenReturn(storage);
		SysImage sysImage = new SysImage();
		when(storage.getImagesInformations(any())).thenReturn(sysImage);
		cnfInformationVerifier.verifyCnf();
	}

	@Test
	void testMinInstance() {
		VimConnectionInformation vci = new VimConnectionInformation();
		CnfInformations cnfInfo = new CnfInformations();
		cnfInfo.setExtNetwork("networkName");
		cnfInfo.setExtNetworkId("networkId");
		ClusterMachine master = new ClusterMachine();
		master.setFlavor("flavor");
		master.setMinNumberInstance(0);
		cnfInfo.setMaster(master);

		ClusterMachine worker = new ClusterMachine();
		worker.setFlavorId("id");
		cnfInfo.setWorker(worker);
		vci.setCnfInfo(cnfInfo);
		CnfInformationVerifier cnfInformationVerifier = CnfInformationVerifier.of(vim, vci);
		when(vim.network(vci)).thenReturn(network);
		when(network.search(NetowrkSearchField.NAME, List.of("networkName"))).thenReturn(List.of(new NetworkObject("id", "name")));
		Flavor fl00 = new Flavor();
		fl00.setName("flavor");
		fl00.setId("id");
		when(vim.getFlavorList(vci)).thenReturn(List.of(fl00));
		assertThrows(VimException.class, () -> cnfInformationVerifier.verifyCnf());
	}
}
