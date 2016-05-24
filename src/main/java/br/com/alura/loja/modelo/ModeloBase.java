package br.com.alura.loja.modelo;

import com.thoughtworks.xstream.XStream;

public class ModeloBase {
	public String toXml() {
		return new XStream().toXML(this);
	}
}
