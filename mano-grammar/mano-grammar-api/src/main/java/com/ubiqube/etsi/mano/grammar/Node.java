/**
 *     Copyright (C) 2019-2023 Ubiqube.
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
/**
 * This copy of Woodstox XML processor is licensed under the
 * Apache (Software) License, version 2.0 ("the License").
 * See the License for details about distribution rights, and the
 * specific rights regarding derivate works.
 *
 * You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/
 *
 * A copy is also included in the downloadable source code package
 * containing Woodstox, in file "ASL2.0", under the same directory
 * as this file.
 */
package com.ubiqube.etsi.mano.grammar;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Nullable;

/**
 *
 * @author Olivier Vignaud <ovi@ubiqube.com>
 *
 */
public class Node<U> {

	public enum Operand {
		EQ("eq"),
		NEQ("neq"),
		GT("gt"),
		LT("lt"),
		GTE("gte"),
		LTE("lte"),
		CONT("cont"),
		NCONT("ncont"),
		IN("in"),
		NIN("nin");

		public final String op;

		Operand(final String _op) {
			op = _op;
		}
	}

	@Nullable
	private String name;
	private Operand op;
	private List<U> value = new ArrayList<>();

	public Node() {
		// Nothing.
	}

	public Node(final @Nullable String name, @Nullable final Operand op, final List<U> value) {
		this.name = name;
		this.op = op;
		this.value = new ArrayList<>(value);
	}

	public static <U> Node<U> of(final String name, final Operand op, final List<U> value) {
		return new Node<>(name, op, value);
	}

	public static <U> Node<U> of(final String name, final Operand op, final U value) {
		return new Node<>(name, op, List.of(value));
	}

	public @Nullable String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public @Nullable Operand getOp() {
		return op;
	}

	public void setOp(final Operand op) {
		this.op = op;
	}

	public @Nullable U getValue() {
		if (value.size() > 1) {
			throw new GrammarException("Calling a multivalue.");
		}
		if (value.isEmpty()) {
			return null;
		}
		return value.get(0);
	}

	public List<U> getValues() {
		if (null == value) {
			return List.of();
		}
		return value;
	}

	public void setValue(final List<U> value) {
		this.value = value;
	}

	public void addValue(final U inValue) {
		value.add(inValue);
	}

	@Override
	public String toString() {
		return "Node [name=" + name + ", op=" + op + ", value=" + value + "]";
	}

}
