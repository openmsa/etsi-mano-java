/**
 *     Copyright (C) 2019-2020 Ubiqube.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.ubiqube.etsi.mano.tf;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ubiqube.etsi.mano.orchestrator.entities.SystemConnections;

import net.juniper.contrail.api.ApiConnector;
import net.juniper.contrail.api.ApiConnectorFactory;
import net.juniper.contrail.api.ApiObjectBase;
import net.juniper.contrail.api.Status;
import net.juniper.contrail.api.types.InstanceIp;
import net.juniper.contrail.api.types.IpamSubnetType;
import net.juniper.contrail.api.types.IpamSubnets;
import net.juniper.contrail.api.types.NetworkIpam;
import net.juniper.contrail.api.types.PortTuple;
import net.juniper.contrail.api.types.Project;
import net.juniper.contrail.api.types.ServiceInstance;
import net.juniper.contrail.api.types.ServiceTemplate;
import net.juniper.contrail.api.types.ServiceTemplateInterfaceType;
import net.juniper.contrail.api.types.ServiceTemplateType;
import net.juniper.contrail.api.types.SubnetType;
import net.juniper.contrail.api.types.VirtualMachineInterface;
import net.juniper.contrail.api.types.VirtualNetwork;
import net.juniper.contrail.api.types.VirtualNetworkType;
import net.juniper.contrail.api.types.VnSubnetsType;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
@SuppressWarnings("static-method")
class ContrailTest {

	private static final Logger LOG = LoggerFactory.getLogger(ContrailTest.class);
	private final SystemConnections vimConnectionInformation;

	public ContrailTest() {
		vimConnectionInformation = new SystemConnections();
		vimConnectionInformation.setInterfaceInfo(new HashMap<>());
		// vimConnectionInformation.getInterfaceInfo().put("endpoint",
		// "http://192.168.1.36:8082");
		vimConnectionInformation.getInterfaceInfo().put("endpoint", "https://10.242.228.221:8082");
		vimConnectionInformation.getInterfaceInfo().put("auth-endpoint", "https://10.242.228.250:5000/v3");
		vimConnectionInformation.setAccessInfo(new HashMap<>());
		vimConnectionInformation.getAccessInfo().put("username", "admin");
		vimConnectionInformation.getAccessInfo().put("password", "uoleiChai8no6yai");
		vimConnectionInformation.getAccessInfo().put("projectId", "44009ebe33d34d5f81b22a9354661abf");
		vimConnectionInformation.getAccessInfo().put("projectDomain", "admin_domain");
	}

	@Test
	void dummy() {
		assertTrue(true);
	}

	private final static ApiConnector getConnection() {
		return ApiConnectorFactory.build("192.168.1.36", 8082).credentials("", "");
	}

	private static Status executeCreate(final ApiObjectBase obj) throws IOException {
		final ApiConnector conn = getConnection();
		final Status st = conn.create(obj);
		LOG.info("{} ", st.getClass());
		st.ifFailure(new LogErrorHandler());
		return st;
	}

	void testConnection() throws IOException {
		final ApiConnector conn = getConnection();
		final List<? extends ApiObjectBase> res = conn.list(PortTuple.class, List.of());
		res.forEach(x -> {
			final PortTuple xx = (PortTuple) x;
			LOG.info("{} {}", xx.getName(), xx.getUuid());
		});
		LOG.info("{}", res);
	}

	void createServiceTemplateTest() throws IOException {
		final ApiConnector conn = getConnection();
		final ServiceTemplate obj = new ServiceTemplate();
		obj.setDisplayName("ovi test");
		obj.setName("ovi-name");
		final ServiceTemplateType props = new ServiceTemplateType();
		props.setServiceVirtualizationType("virtual-machine"); // Any of [‘virtual-machine’, ‘network-namespace’, ‘vrouter-instance’,
																// ‘physical-device’]
		props.addInterfaceType(new ServiceTemplateInterfaceType("left"));
		props.addInterfaceType(new ServiceTemplateInterfaceType("right"));
		props.setServiceMode("in-network"); // Any of [‘transparent’, ‘in-network’, ‘in-network-nat’]
		props.setServiceType("firewall"); // Any of [‘firewall’, ‘analyzer’, ‘source-nat’, ‘loadbalancer’]
		props.setVersion(2);
		obj.setProperties(props);
		final Status st = conn.create(obj);
		LOG.info("{} ", st.getClass());
		st.ifFailure(new LogErrorHandler());
	}

	void createServiceInstanceTest() {
		final ApiConnector conn = getConnection();
		final ServiceInstance root = new ServiceInstance();
		final ServiceTemplate st = new ServiceTemplate();
		root.setServiceTemplate(st);
	}

	void createPortTupleTest() {
		final ApiConnector conn = getConnection();
		final PortTuple root = new PortTuple();
		root.setDisplayName("ovi-tuple");
		root.setName("ovi-tuple-name");
		final ServiceInstance parent = null;
		root.setParent(parent);
	}

	void createIpamTest() throws IOException {
		final ApiConnector conn = getConnection();
		final NetworkIpam root = new NetworkIpam();
		root.setDisplayName("ovi-ipam");
		root.setName("ovi-ipam-name");
		// root.setIpamSubnetMethod("user-defined-subnet"); // Any of
		// [‘user-defined-subnet’, ‘flat-subnet’, ‘auto-subnet’]
		final List<IpamSubnetType> subnets = new ArrayList<>();
		final SubnetType subnet = new SubnetType("5.6.7.8", 24);
		final IpamSubnetType ipt = new IpamSubnetType(subnet);
		subnets.add(ipt);
		final IpamSubnets ipam_subnets = new IpamSubnets(subnets);
		// root.setIpamSubnets(ipam_subnets);
		executeCreate(root);
		LOG.info("Done crating ipam.");

		// conn.update(root);
	}

	void deleteIpam() throws IOException {
		final ApiConnector conn = getConnection();
		final NetworkIpam root = new NetworkIpam();
		root.setUuid("af605e1f-f1a4-4d90-a39c-924a72f3d3c5");
		conn.delete(root);
	}

	void createVirtualNetworkTest() throws IOException {
		final ApiConnector conn = getConnection();
		final VirtualNetwork root = new VirtualNetwork();
		root.setDisplayName("ovi display name");
		root.setName("ovi-vl");
		// final NetworkIpam ipam = (NetworkIpam) conn.findById(NetworkIpam.class,
		// "8e2e057c-204f-499c-9782-0367e0bc0aa1");
		final NetworkIpam ipam = new NetworkIpam();
		ipam.setName("ovi-ipam-name");
		final List<IpamSubnetType> subnets = new ArrayList<>();
		final SubnetType subnet = new SubnetType("5.6.7.8", 24);
		final IpamSubnetType ipt = new IpamSubnetType(subnet);
		subnets.add(ipt);
		final IpamSubnets ipam_subnets = new IpamSubnets(subnets);
		final VirtualNetworkType vnp = new VirtualNetworkType();
		final VnSubnetsType data = new VnSubnetsType();
		data.addIpamSubnets(ipt);
		root.setNetworkIpam(ipam, data);
		executeCreate(root);
	}

	void createVirtualMachineInterfaceTest() throws IOException {
		final ApiConnector conn = getConnection();
		final VirtualMachineInterface root = new VirtualMachineInterface();
		root.setName("vmi-ovi");
		root.setDisplayName("VirtMacInt-OVI");
		final PortTuple obj = null;
		root.setPortTuple(obj);
		executeCreate(root);
	}

	void createServiceInstance() {
		final ContrailApi api = new ContrailApi();
		// final String tmpl = api.createServiceTemplate(vimConnectionInformation,
		// "ovi-template");
		final String left = "6e5fc975-9265-43f7-8923-89f2cce03563";
		final String right = "7ae1f037-b6f5-431a-bb7b-48d8644b9033";
		// final String si = api.createServiceInstance(vimConnectionInformation,
		// "ovi-si", tmpl, left, right);
		// final String pt = api.createPortTuple(vimConnectionInformation, "ovi-pt",
		// si);
		final String vmi = "4880c971-0359-43f4-a2a1-d63ad72dbab4";
		final String ptStr = "c45f7463-67b9-4436-a1f3-c80d211605d2";
		api.updatePort(vimConnectionInformation, vmi, ptStr, "left");
	}

	void getTuplePort() {
		final ContrailFacade cf = new ContrailFacade();
		final VirtualMachineInterface obj = cf.findById(vimConnectionInformation, VirtualMachineInterface.class, "4880c971-0359-43f4-a2a1-d63ad72dbab4");
		LOG.debug("{}", obj);
	}

	void testInstanceIp() {
		final ContrailFacade cf = new ContrailFacade();
		final InstanceIp ii = new InstanceIp();
		ii.setUuid("3f2b990f-bb57-4e60-a6a6-7d856f9cf37d");
		cf.delete(vimConnectionInformation, ii);
	}

	void testVmiUPdate() {
		final ContrailFacade cf = new ContrailFacade();
		final VirtualMachineInterface vmi = cf.findById(vimConnectionInformation, VirtualMachineInterface.class, "cae3cb37-4006-4a4d-aaa4-0f3d59a9b7bb");
		vmi.clearVirtualMachineInterface();
		cf.update(vimConnectionInformation, vmi);
	}

	void testVmiDelete() {
		final ContrailFacade cf = new ContrailFacade();
		final VirtualMachineInterface vmi = cf.findById(vimConnectionInformation, VirtualMachineInterface.class, "cae3cb37-4006-4a4d-aaa4-0f3d59a9b7bb");
		vmi.clearVirtualMachineInterface();
		cf.delete(vimConnectionInformation, vmi);
	}

	void testUpdatePortTuple() {
		final ContrailFacade cf = new ContrailFacade();
		final PortTuple pt = cf.findById(vimConnectionInformation, PortTuple.class, "edf523f2-8237-4fa3-9bdd-0ad43a8d6d08");
		pt.clearVirtualNetwork();
		cf.update(vimConnectionInformation, pt);
	}

	void deletePortTuple() {
		final ContrailFacade cf = new ContrailFacade();
		final PortTuple pt = new PortTuple();
		final Project prj = new Project();
		prj.setName("admin");
		// pt.setParent(prj);
		pt.setUuid("edf523f2-8237-4fa3-9bdd-0ad43a8d6d08");
		cf.delete(vimConnectionInformation, pt);
	}

	void deletePt2() {
		final ContrailFacade cf = new ContrailFacade();
		final PortTuple pt = cf.findById(vimConnectionInformation, PortTuple.class, "edf523f2-8237-4fa3-9bdd-0ad43a8d6d08");
		pt.getVirtualMachineInterfaceBackRefs().forEach(x -> deleteVmi(x.getUuid()));
		cf.delete(vimConnectionInformation, pt);
	}

	private void deleteVmi(final String uuid) {
		final ContrailFacade cf = new ContrailFacade();
		final VirtualMachineInterface vmi = cf.findById(vimConnectionInformation, VirtualMachineInterface.class, uuid);
		vmi.getInstanceIpBackRefs().stream().filter(x -> !x.getReferredName().get(0).contains("-left-")).forEach(x -> {
			final InstanceIp ii = new InstanceIp();
			ii.setUuid(x.getUuid());
			cf.delete(vimConnectionInformation, ii);
		});
		cf.delete(vimConnectionInformation, vmi);
	}

	void simpleConnectionTest() {
		System.getProperties().put("proxySet", "true");
		System.getProperties().put("socksProxyHost", "127.0.0.1");
		System.getProperties().put("socksProxyPort", "3128");
		final ContrailApi ca = new ContrailApi();
		// Old Token TM LAB
		// gAAAAABja79seW3S42kHGIJX3TWxXP-jvDy5AXcWMQEwJNks0hx08F4mejRQEajNBWDLjGi0YVKf_I2dwqEgP_0KvTn4-sxEyIK7Oio6U7qiIVoM9HYGfHNJJkQpZGsGqGyPN60thtqrc5wKz1pz-uWacuFZBSYoaA
		ca.deleteServiceTemplate(vimConnectionInformation, UUID.randomUUID().toString());
		assertTrue(true);
	}

	@Test
	void testName() throws Exception {
		assertTrue(true);
	}
}
