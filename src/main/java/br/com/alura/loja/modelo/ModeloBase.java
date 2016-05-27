package br.com.alura.loja.modelo;

import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

public class ModeloBase {
	public String toXml() {
		return new XStream().toXML(this);
	}
	
	public String toJSon() {
		return new Gson().toJson(this);
	}
}
